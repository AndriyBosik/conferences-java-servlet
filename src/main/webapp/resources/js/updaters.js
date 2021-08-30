function updateErrorImages() {
    const errorImages = document.querySelectorAll("img[data-error]");
    for (let errorImage of errorImages) {
        if (errorImage.naturalWidth === 0) {
            // Image is broken
            const defaultKey = errorImage.getAttribute("data-error");
            errorImage.src = DEFAULT_IMAGES[defaultKey];
        }
    }
}

function updateDataSubmitComponents() {
    let dataSubmitActions = document.querySelectorAll("[data-submit-action]");
    for (let dataSubmitAction of dataSubmitActions) {
        dataSubmitAction.addEventListener("click", () => {
            let form = dataSubmitAction.closest("form");
            if (form == null) {
                return;
            }
            form.submit();
        });
    }
}

function updateSelects() {
    let selects = document.querySelector("select");
    for (let select of selects) {
        select.value = select.getAttribute("value");
    }
    M.FormSelect.init(selects);
}