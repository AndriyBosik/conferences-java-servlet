const speakerProposalsComponent = document.getElementById("speakerProposals");
const DATA_TOPIC_ID_ATTRIBUTE = "data-topic-id";

document.addEventListener("DOMContentLoaded", () => {
    const preloader = document.querySelector("#topic-proposals-form .preloader");

    let proposalsSearchTriggers = document.querySelectorAll(".proposalsSearchTrigger");
    for (let proposalsSearchTrigger of proposalsSearchTriggers) {
        let topicId = proposalsSearchTrigger.getAttribute(`${DATA_TOPIC_ID_ATTRIBUTE}`);
        proposalsSearchTrigger.addEventListener("click", async () => {
            preloader.classList.remove(HIDDEN_CLASS);

            let proposals = await fetchTopicProposals(topicId);
            for (let proposal of proposals) {
                speakerProposalsComponent.append(getTopicProposalComponent(proposal));
            }

            preloader.classList.add(HIDDEN_CLASS);

            updateErrorImages();
            updateDataSubmitComponents();
        });
    }
});

function getTopicProposalComponent(topicProposal) {
    let layout = `<form action="${SET_TOPIC_SPEAKER_FROM_PROPOSALS_ACTION}" method="post" class="col s6 m3 my5">
                <input type="hidden" name="speaker_id" value="${topicProposal.speaker.id}" />
                <input type="hidden" name="topic_id" value="${topicProposal.reportTopicId}">
                <input type="hidden" name="meeting_id" value="${MEETING_ID}">
                <div class="proposal full-width clickable" data-submit-action>
                    <div class="z-depth-1">
                        <div class="s-hflex">
                            <div class="proposal-avatar">
                                <img src="/resources/images/avatars/${topicProposal.speaker.login}.png" alt="" class="full-width full-height" data-error="avatarDefault" />
                            </div>
                            <span class="username s-vflex-center mx10 weight-strong">${topicProposal.speaker.name} ${topicProposal.speaker.surname}</span>
                        </div>
                    </div>
                </div>
            </form>`;

    return convertStringToDomElement(layout);
}

async function fetchTopicProposals(topicId) {
    speakerProposalsComponent.innerHTML = "";
    let response = await fetch(GET_TOPIC_PROPOSALS_ACTION + topicId);
    return response.json();
}