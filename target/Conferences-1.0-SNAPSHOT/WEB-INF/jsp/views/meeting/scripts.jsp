<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<tf:forRoles roles="${['moderator']}">
    <script type="text/javascript">
        const MEETING_ID = "${empty meeting ? '' : meeting.id}";
        const test = "${empty asdf ? "empty" : "not empty"}";
    </script>
    <script type="text/javascript" src="/resources/js/topic-updater.js"></script>
    <script type="text/javascript" src="/resources/js/topic-proposals.js"></script>
</tf:forRoles>