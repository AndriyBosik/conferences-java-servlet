const TOPIC_DATA_SPEAKER_ID_ATTRIBUTE = "data-speaker-id";
const TOPIC_DATA_TITLE_ATTRIBUTE = "data-title";
const TOPIC_DATA_TOPIC_ID_ATTRIBUTE = "data-topic-id";
const TOPIC_DATA_REPORT_TOPIC_SPEAKER_ID_ATTRIBUTE = "data-report-topic-speaker-id";

document.addEventListener("DOMContentLoaded", function() {
    const TOPIC_FORM = document.getElementById("new-topic-form");
    const TOPIC_PRELOADER = document.getElementById("topic-preloader");

    let topicTriggers = document.querySelectorAll(".topicTrigger");
    for (let topicTrigger of topicTriggers) {
        topicTrigger.addEventListener("click", function() {
            TOPIC_PRELOADER.classList.remove(HIDDEN_CLASS);

            const tr = topicTrigger.closest("tr");

            TOPIC_FORM.action = UPDATE_TOPIC_ACTION;
            let topicData = {
                id: tr.getAttribute(TOPIC_DATA_TOPIC_ID_ATTRIBUTE),
                report_topic_speaker_id: null,
                title: tr.querySelector(`[${TOPIC_DATA_TITLE_ATTRIBUTE}]`).innerHTML,
                speaker_id: null
            };

            let speakerIdElement = tr.querySelector(`[${TOPIC_DATA_SPEAKER_ID_ATTRIBUTE}]`);
            let reportTopicSpeakerId = tr.getAttribute(TOPIC_DATA_REPORT_TOPIC_SPEAKER_ID_ATTRIBUTE);
            if (speakerIdElement != null) {
                Object.assign(topicData, {
                    speaker_id: speakerIdElement.getAttribute(TOPIC_DATA_SPEAKER_ID_ATTRIBUTE)
                });
            }
            if (reportTopicSpeakerId != null) {
                topicData.report_topic_speaker_id = reportTopicSpeakerId;
            }

            assignValuesToForm(TOPIC_FORM, topicData);
            TOPIC_PRELOADER.classList.add(HIDDEN_CLASS);
        });
    }

    let createTopicFormTriggers = document.querySelectorAll(".createTopicFormTrigger");
    for (let trigger of createTopicFormTriggers) {
        trigger.addEventListener("click", function() {
            TOPIC_PRELOADER.classList.remove(HIDDEN_CLASS);

            TOPIC_FORM.action = CREATE_TOPIC_ACTION;
            assignValuesToForm(TOPIC_FORM, INITIAL_TOPIC_STATE);

            TOPIC_PRELOADER.classList.add(HIDDEN_CLASS);
        });
    }
});

function assignValuesToForm(topicForm, values) {
    updateTextFieldValue(topicForm.querySelector("[name='id']"), values.id);
    updateTextFieldValue(topicForm.querySelector("[name='title']"), values.title);
    updateTextFieldValue(topicForm.querySelector("[name='report_topic_speaker_id']"), values.report_topic_speaker_id);
    const speakerSelect = topicForm.querySelector("[name='speaker_id']");
    if (values.speaker_id != null) {
        updateSelectValue(speakerSelect, values.speaker_id);
    } else {
        updateSelectValue(speakerSelect, "");
    }
}