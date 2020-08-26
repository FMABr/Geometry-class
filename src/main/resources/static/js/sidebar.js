var hamburger = document.getElementById("btSidebar");
var sidebar = document.querySelector(".sidebar");
var main = document.querySelector("main");

hamburger.addEventListener('click', () => {
    hamburger.classList.toggle("active");
    sidebar.classList.toggle("closed");
    main.classList.toggle("closed");
}, false);