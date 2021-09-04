const proposalTriggers = document.querySelectorAll(".proposalTrigger");
const preloader = document.querySelector("#propose-to-speakers-form #proposal-preloader");
const availableSpeakersCollection = document.getElementById("availableSpeakersCollection");
const proposalSendingPreloader = document.querySelector(".proposalSendingPreloader");

for (let proposalTrigger of proposalTriggers) {
    const tr = proposalTrigger.closest("tr");
    const topicId = tr.getAttribute("data-topic-id");
    proposalTrigger.addEventListener("click", async () => {
        preloader.classList.remove(HIDDEN_CLASS);
        availableSpeakersCollection.innerHTML = "";

        const availableSpeakers = await getAvailableSpeakersByTopicId(topicId);

        for (let speaker of availableSpeakers) {
            const item = getSpeakerProposal(speaker);
            availableSpeakersCollection.append(item);

            const button = item.querySelector(".proposalSender");
            button.addEventListener("click", async () => {
                const speakerId = button.getAttribute("data-speaker-id");

                const preloaderClone = proposalSendingPreloader.cloneNode(true);
                preloaderClone.classList.remove(HIDDEN_CLASS);
                button.replaceWith(preloaderClone);

                const response = await sendProposal(speakerId, topicId);
                if (response.status === "success") {
                    preloaderClone.replaceWith(getSuccessResultComponent());
                } else {
                    M.toast({
                        html: response.message
                    });
                    preloaderClone.replaceWith(button);
                }
            });
        }

        preloader.classList.add(HIDDEN_CLASS);
    });
}

async function getAvailableSpeakersByTopicId(topicId) {
    const response = await fetch(GET_TOPIC_AVAILABLE_SPEAKERS_TO_PROPOSE + topicId);
    return response.json();
}

async function sendProposal(speakerId, topicId) {
    const response = await fetch(PROPOSE_TOPIC_FOR_USER, {
        method: "POST",
        body: JSON.stringify({
            speakerId: speakerId,
            reportTopicId: topicId
        }),
        headers: {
            "Content-Type": "application/json"
        }
    });
    return response.json();
}

function getSpeakerProposal(speaker) {
    const layout =
        `<li class="collection-item s-hflex">
            <div class="z-depth-1 user-avatar stretch-background">
                <img src="/resources/images/avatars/${speaker.imagePath}" alt="" class="circle full-width full-height" data-error="avatarDefault" />
            </div>
            <span class="title weight-normal s-vflex-center mx10 equal-flex">
                ${speaker.name} ${speaker.surname}
            </span>
            <span class="secondary-content s-vflex-center col s3">
                <div class="s-hflex-end">
                    <button type="type" class="btn waves-effect waves-light proposalSender" data-speaker-id="${speaker.id}">
                        ${PROPOSE_MESSAGE}
                        <i class="material-icons right">check</i>
                    </button>
                </div>
            </span>
        </li>`;

    return convertStringToDomElement(layout);
}

function getSuccessResultComponent() {
    return convertStringToDomElement(
        `<div>
                    <i class="material-icons teal-text small">check</i>
                </div>`
    );
}