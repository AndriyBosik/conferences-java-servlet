document.addEventListener("DOMContentLoaded", function() {
    let elements = document.querySelectorAll(".tooltipped");
    M.Tooltip.init(elements);

    let dataHrefs = document.querySelectorAll("[data-href]");
    for (let dataHref of dataHrefs) {
        let url = dataHref.getAttribute("data-href");
        dataHref.addEventListener("click", function() {
            window.location = url;
        });
    }
});