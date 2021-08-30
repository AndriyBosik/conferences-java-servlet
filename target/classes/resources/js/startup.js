document.addEventListener("DOMContentLoaded", function() {
    updateErrorImages();
    updateDataSubmitComponents();

    let tooltipped = document.querySelectorAll(".tooltipped");
    M.Tooltip.init(tooltipped);

    let modals = document.querySelectorAll(".modal");
    M.Modal.init(modals);

    let selects = document.querySelectorAll("select");
    for (let select of selects) {
        initSelectedOption(select);
    }
    M.FormSelect.init(selects);

    let datePickers = document.querySelectorAll(".date-picker");
    let weekdaysShort = WEEKDAYS.map(weekday => weekday.substr(0, 3));
    M.Datepicker.init(datePickers, {
        format: "dd-mm-yyyy",
        firstDay: 1,
        i18n: {
            months: MONTHS,
            monthsShort: MONTHS.map(month => month.substr(0, 3)),
            weekdays: WEEKDAYS,
            weekdaysShort: weekdaysShort,
            weekdaysAbbrev: weekdaysShort,
            cancel: CANCEL,
            clear: CLEAR
        }
    });

    let dataHrefs = document.querySelectorAll("[data-href]");
    for (let dataHref of dataHrefs) {
        let url = dataHref.getAttribute("data-href");
        dataHref.addEventListener("click", function() {
            window.location = url;
        });
    }
});

function initSelectedOption(select) {
    let option = select.querySelector(`option[value='${select.getAttribute("initial-value")}']`);
    if (option == null) {
        option = select.querySelector("option");
    }
    if (option != null) {
        option.setAttribute("selected", "selected");
    }
}