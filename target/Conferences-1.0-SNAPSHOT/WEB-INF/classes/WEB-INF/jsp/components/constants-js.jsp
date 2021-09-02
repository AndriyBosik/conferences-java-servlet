<%@ taglib uri="tags" prefix="taglib" %>

<script type="text/javascript">
    const UPDATE_TOPIC_ACTION = "<taglib:linkTo href="/topics/update" />";
    const CREATE_TOPIC_ACTION = "<taglib:linkTo href="/topics/create" />";
    const SET_TOPIC_SPEAKER_FROM_PROPOSALS_ACTION = "<taglib:linkTo href="/topics/set-speaker-from-proposals" />";
    const GET_TOPIC_PROPOSALS_ACTION = "<taglib:linkTo href="/topics-api/get-topic-proposals/" />";
    const GET_TOPIC_AVAILABLE_SPEAKERS_TO_PROPOSE = "<taglib:linkTo href="/topics-api/get-available-speakers-for-proposal/" />";
    const PROPOSE_TOPIC_FOR_USER = "<taglib:linkTo href="/topics-api/propose-topic-for-user" />";
    const SAVE_USER_PRESENCE = "<taglib:linkTo href="/meetings-api/save-user-presence" />";

    const PROPOSE_MESSAGE = "<taglib:message value="propose" />";
    const IS_PRESENT_MESSAGE = "<taglib:message value="is_present" />";
    const IS_NOT_PRESENT_MESSAGE = "<taglib:message value="is_not_present" />";

    const INITIAL_TOPIC_STATE = {
        id: "",
        title: "",
        speaker_id: "",
        report_topic_speaker_id: ""
    };

    const HIDDEN_CLASS = "hidden";
</script>