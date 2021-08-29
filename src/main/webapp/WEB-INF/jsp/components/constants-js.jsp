<%@ taglib uri="tags" prefix="taglib" %>

<script type="text/javascript">
    const UPDATE_TOPIC_ACTION = "<taglib:linkTo href="/topics/update" />";
    const CREATE_TOPIC_ACTION = "<taglib:linkTo href="/topics/create" />";
    const SET_TOPIC_SPEAKER_FROM_PROPOSALS_ACTION = "<taglib:linkTo href="/topics/set-speaker-from-proposals" />";
    const GET_TOPIC_PROPOSALS_ACTION = "<taglib:linkTo href="/topics-api/get-topic-proposals/" />";

    const INITIAL_TOPIC_STATE = {
        id: "",
        title: "",
        speaker_id: "",
        report_topic_speaker_id: ""
    };

    const HIDDEN_CLASS = "hidden";
</script>