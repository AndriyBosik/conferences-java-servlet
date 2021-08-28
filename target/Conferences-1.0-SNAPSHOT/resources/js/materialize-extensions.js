function updateSelectValue(select, value) {
    select.value = value;
    M.FormSelect.init(select);
}

function updateTextFieldValue(textField, value) {
    textField.value = value;
    M.updateTextFields();
}