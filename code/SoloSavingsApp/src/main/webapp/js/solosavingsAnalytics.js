function analytics() {
    let jwt = "";

    function setAuthHeader() {
        const jwtToken = document.cookie.replace(/(?:(?:^|.*;\s*)jwtToken\s*\=\s*([^;]*).*$)|^.*$/, "$1");
        if (jwtToken) {
            jwt = jwtToken;
            console.log("Found JWT cookie: " + jwt);

        } else {
            console.log("Failed to find JWT cookie");
            alert("You must login before accessing the dashboard");
            window.location.replace("/solosavings/login");
        }
    }

    setAuthHeader();
    $.ajaxSetup({
        headers: {
            'Authorization': "Bearer " + jwt
        }
    })
    $(document).ready(function () {
        let incomesData;
        let expensesData;

        async function fetchData() {
            incomesData = await getIncomesData();
            expensesData = await getExpensesData();
        }

        function getIncomesData() {
            return $.ajax({
                type: 'GET',
                url: '/api/transaction/analytics/monthly/CREDIT/2023', // change user_id later
                contentType: 'application/json',
                success: function (response) {
                    const gsonObj = new Gson();
                    incomesData = gsonObj.toJson(response);
                },
                error: function (error) {
                    console.error('Something went wrong!', error);
                }
            });
        }

        function getExpensesData() {
            return $.ajax({
                type: 'GET',
                url: '/api/transaction/analytics/monthly/DEBIT/2023', // change user_id later
                contentType: 'application/json',
                success: function (response) {
                    const gsonObj = new Gson();
                    expensesData = gsonObj.toJson(response);
                },
                error: function (error) {
                    console.error('Something went wrong!', error);
                }
            });
        }

        async function init() {
            await fetchData();
            createChart(incomesData, expensesData);
        }

        function createChart(incomes, expenses) {
            const chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                theme: "light1",
                axisY: {
                    title: "US $",
                    includeZero: true
                },
                axisX: {
                    interval: 1,
                    intervalType: "month",
                    stripLines: {
                        interval: 4
                    }
                },
                toolTip: {
                    shared: true
                },
                legend: {
                    cursor: "pointer",
                    itemclick: toggleDataSeries
                },
                data: [{
                    type: "column",
                    name: "income",
                    yValueFormatString: "$#0.00#",
                    showInLegend: true,
                    dataPoints: incomes
                }, {
                    type: "column",
                    name: "expense",
                    yValueFormatString: "$#0.00#",
                    showInLegend: true,
                    dataPoints: expenses
                }]
            });
            chart.render();
        }

        function toggleDataSeries(e) {
            e.dataSeries.visible = !(typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible);
            chart.render();
        }

        init();
    });
}
analytics();