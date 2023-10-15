function showMenu() {
    let jwtToken = document.cookie.replace(/(?:(?:^|.*;\s*)jwtToken\s*\=\s*([^;]*).*$)|^.*$/, "$1");
    if (jwtToken) {
        let elem1 = document.getElementById("logged_in_user");
        elem1.style.display = "block";
        let elem2 = document.getElementById("logged_out_user");
        elem2.style.display = "none";
    } else {
        let elem1 = document.getElementById("logged_out_user");
        elem1.style.display = "block";
        let elem2 = document.getElementById("logged_in_user");
        elem2.style.display = "none";
    }
}
function logout() {
    let EXPIRE_COOKIE = "jwtToken=; expires= Thur, 01 Jan 1970 00:00:00 UTC; path=/solosavings;";
    console.log("Logging out");
    document.cookie = EXPIRE_COOKIE;
    window.location.replace("/");
}

showMenu();


