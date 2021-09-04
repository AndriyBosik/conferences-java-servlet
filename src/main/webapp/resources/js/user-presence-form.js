const presenceValues = document.querySelectorAll("#presence-editor .presenceValue");
const presenceSendingPreloader = document.querySelector("#presence-editor .presenceSendingPreloader");

for (let presenceValue of presenceValues) {
    const present = presenceValue.getAttribute("data-presence") === "true";
    const statData = {
        meetingId: MEETING_ID,
        userId: presenceValue.getAttribute("data-user-id")*1,
        present: !present
    };
    let button;
    if (present) {
        button = getIsNotPresentButton(statData);
    } else {
        button = getIsPresentButton(statData);
    }
    presenceValue.replaceWith(button);
}

function getIsPresentButton(statData) {
    const button = convertStringToDomElement(
        `<div class="clickable">
            <i class="material-icons weight-strong fs30 tooltipped" data-position="right" data-tooltip="${IS_PRESENT_MESSAGE}">add</i>
        </div>`
    );

    button.addEventListener("click", async () => {
        const preloaderClone = presenceSendingPreloader.cloneNode(true);
        preloaderClone.classList.remove(HIDDEN_CLASS);
        button.replaceWith(preloaderClone);

        await sendRequest(statData);

        statData.present = false;
        preloaderClone.replaceWith(getIsNotPresentButton(statData));
    });

    return button;
}

function getIsNotPresentButton(statData) {
    const button = convertStringToDomElement(
        `<div class="clickable">
            <i class="material-icons weight-strong fs30 red-text tooltipped" data-position="right" data-tooltip="${IS_NOT_PRESENT_MESSAGE}">remove</i>
        </div>`
    );

    button.addEventListener("click", async () => {
        const preloaderClone = presenceSendingPreloader.cloneNode(true);
        preloaderClone.classList.remove(HIDDEN_CLASS);
        button.replaceWith(preloaderClone);

        const response = await sendRequest(statData);
        if (response.message !== "") {
            M.toast({
                html: response.message
            });
        }

        statData.present = true;
        preloaderClone.replaceWith(getIsPresentButton(statData));
    });

    return button;
}

async function sendRequest(statData) {
    console.log("here:", statData);
    const response = await fetch(SAVE_USER_PRESENCE, {
        method: "POST",
        body: JSON.stringify(statData),
        headers: {
            "Content-Type": "application/json"
        }
    });
    return response.json();
}