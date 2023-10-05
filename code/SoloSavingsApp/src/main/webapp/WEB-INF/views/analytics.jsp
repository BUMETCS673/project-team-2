<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page isELIgnored="false" %>

<%--<%--%>
<%--    Gson gsonObj = new Gson();--%>
<%--    Map<Object,Object> map = null;--%>
<%--    List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();--%>

<%--    map = new HashMap<Object,Object>(); map.put("label", "2008–09"); map.put("y", 209.7); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2009–10"); map.put("y", 198.3); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2010–11"); map.put("y", 206.1); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2011–12"); map.put("y", 224.8); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2012–13"); map.put("y", 238.2); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2013–14"); map.put("y", 271.6); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2014–15"); map.put("y", 290.2); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2015–16"); map.put("y", 319); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2016–17"); map.put("y", 332.9); list.add(map);--%>

<%--    String dataPoints1 = gsonObj.toJson(list);--%>

<%--    list = new ArrayList<Map<Object,Object>>();--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2008–09"); map.put("y", 136.4); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2009–10"); map.put("y", 135); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2010–11"); map.put("y", 143.3); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2011–12"); map.put("y", 162.6); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2012–13"); map.put("y", 173.1); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2013–14"); map.put("y", 206.6); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2014–15"); map.put("y", 225.2); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2015–16"); map.put("y", 254); list.add(map);--%>
<%--    map = new HashMap<Object,Object>(); map.put("label", "2016–17"); map.put("y", 267.9); list.add(map);--%>

<%--    String dataPoints2 = gsonObj.toJson(list);--%>
<%--    String thisYearIncomes = null;--%>
<%--    String thisYearExpenses;--%>
<%--%>--%>

<!DOCTYPE html>
<html>
<head>
  <title>SoloSavings Analytics</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f4;
    }

    header {
      background-color: #333;
      color: white;
      padding: 10px;
      text-align: center;
    }

    tfoot {
        font-weight: bold;
    }

    footer {
        background-color: #333;
        color: white;
        text-align: center;
        padding: 10px;
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    nav ul {
      list-style-type: none;
      margin: 0;
      padding: 0;
    }

    nav ul li {
      display: inline;
      margin-right: 20px;
      color: white;
    }

    main {
      max-width: 800px;
      margin: 0 auto;
      padding: 2rem;
      background-color: #fff;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    h1, h2, h3, p {
      text-align: center;
    }
  </style>


</head>

<body>
<header>
  <nav>
    <ul>
      <li><a href="/solosavings" style="color: white;">Home</a></li>
      <li><a href="/solosavings/login" style="color: white;">Login</a></li>
      <li><a href="/solosavings/register" style="color: white;">Register</a></li>
    </ul>
  </nav>
</header>

<main>
    <div id="chartContainer" style="height: 370px; width: 100%;"></div>
    <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
</main>
</body>
<footer>
    &copy; 2023 SoloSavings
</footer>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.canvasjs.com/assets/script/canvasjs.min.js"></script>
<script>
    $(function() {
        let incomesData;
        let expensesData;

        async function fetchData() {
            incomesData = await getIncomesData();
            expensesData = await getExpensesData();
        }

        function getIncomesData() {
            return $.ajax({
                type: 'GET',
                url: '/transaction/analytics/monthly/CREDIT/2023/1', // change user_id later
                contentType: 'application/json',
                success: function(response) {
                    incomesData = gsonObj.toJson(response);
                    console.log(response)
                    console.log(incomesData)
                },
                error: function(error) {
                    console.error('Something went wrong!', error);
                }
            });
        }

        function getExpensesData() {
            return $.ajax({
                type: 'GET',
                url: '/transaction/analytics/monthly/DEBIT/2023/1', // change user_id later
                contentType: 'application/json',
                success: function(response) {
                    expensesData = gsonObj.toJson(response);
                },
                error: function(error) {
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
                title: {
                    text: "Monthly income and expense for this year"
                },
                axisY: {
                    title: "US $",
                    includeZero: true
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
                    yValueFormatString: "#0.0# million tonnes",
                    showInLegend: true,
                    dataPoints: incomes
                }, {
                    type: "column",
                    name: "expense",
                    yValueFormatString: "#0.## million tonnes",
                    showInLegend: true,
                    dataPoints: expenses
                }]
            });
            chart.render();
        }

        function toggleDataSeries(e) {
            if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                e.dataSeries.visible = false;
            } else {
                e.dataSeries.visible = true;
            }
            chart.render();
        }

        init();
    });


        //
</script>
</html>