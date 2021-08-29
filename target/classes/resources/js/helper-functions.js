function convertStringToDomElement(layout) {
    let div = document.createElement("div");
    div.innerHTML = layout;

    return div.firstChild;
}