const editMeetingModalTriggers = document.querySelectorAll("[data-target='edit-meeting-modal']");
const editMeetingModal = document.getElementById("edit-meeting-modal");
const editMeetingForm = editMeetingModal.querySelector("form");
const editMeetingFormPreloader = document.getElementById("meeting-preloader");

for (let trigger of editMeetingModalTriggers) {
    trigger.addEventListener("click", () => {
        editMeetingFormPreloader.classList.remove(HIDDEN_CLASS);

        const container = trigger.closest(".meetingContainer");

        const meeting = {
            id: container.getAttribute("data-id")*1,
            address: container.querySelector("[data-address]").innerHTML.trim(),
            date: container.querySelector("[data-date]").innerHTML.trim()
        };
        editMeetingForm.querySelector("[name='id']").value = meeting.id;
        updateTextFieldValue(editMeetingForm.querySelector("[name='address']"), meeting.address);

        const dateTime = moment(meeting.date, "DD-MM-YYYY hh:mm");
        editMeetingForm.querySelector("[name='hours']").value = dateTime.hour()
        editMeetingForm.querySelector("[name='minutes']").value = dateTime.minute()

        const editMeetingFormDatePicker = M.Datepicker.getInstance(editMeetingForm.querySelector(".date-picker"));
        editMeetingFormDatePicker.setDate(new Date(
            dateTime.year(),
            dateTime.month(),
            dateTime.date()
        ));
        editMeetingFormDatePicker._finishSelection();

        editMeetingFormPreloader.classList.add(HIDDEN_CLASS);
    });
}