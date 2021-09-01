<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <div class="s-hflex">
                <div class="equal-flex">
                    <h4 class="grey-text text-darken-2 mb0"><taglib:message value="header.profile" /></h4>
                </div>
                <div class="s-vflex-end">
                    <tf:forRoles roles="${['speaker']}">
                        <a href="<taglib:linkTo href="/home/speaker-meetings" />" class="btn waves-effect waves-light modal-trigger">
                            <taglib:message value="my_meetings" />
                            <i class="material-icons right">visibility</i>
                        </a>
                    </tf:forRoles>
                    <tf:forRoles roles="${['moderator']}">
                        <a href="<taglib:linkTo href="/topics/speaker-proposed" />" class="btn waves-effect waves-light modal-trigger">
                            <div class="s-hflex">
                                <span><taglib:message value="proposed_topics" /></span>
                                <c:if test="${proposedTopicsCount > 0}">
                                    <div class="s-vflex-center">
                                        <span class="new badge orange" data-badge-caption="" style="padding-left: 10px; padding-right: 10px; min-width: auto;">${proposedTopicsCount}</span>
                                    </div>
                                </c:if>
                            </div>
                        </a>
                    </tf:forRoles>
                </div>
            </div>
            <hr />
        </div>
        <form action="" method="post" class="col s12">
            <div class="full-width input-field">
                <input id="login" type="text" name="login" value="${user.login}" />
                <label for="login"><taglib:message value="login" /></label>
            </div>
            <div class="full-width input-field">
                <input id="surname" type="text" name="surname" value="${user.surname}" />
                <label for="surname"><taglib:message value="surname" /></label>
            </div>
            <div class="full-width input-field">
                <input id="name" type="text" name="name" value="${user.name}" />
                <label for="name"><taglib:message value="name" /></label>
            </div>
            <div class="full-width input-field">
                <input id="email" type="email" name="email" value="${user.email}" />
                <label for="email"><taglib:message value="email" /></label>
            </div>
            <div class="full-width input-field">
                <input id="old-password" type="password" name="old-password" />
                <label for="old-password"><taglib:message value="old_password" /></label>
            </div>
            <div class="full-width input-field">
                <input id="new-password" type="password" name="new-password" />
                <label for="new-password"><taglib:message value="new_password" /></label>
            </div>
            <div class="full-width input-field">
                <input id="confirm-password" type="password" name="confirm-password" />
                <label for="confirm-password"><taglib:message value="confirm_password" /></label>
            </div>
            <div class="full-width s-hflex-end">
                <button class="btn waves-effect waves-light" type="submit" name="action">
                    <taglib:message value="confirm_changes" />
                    <i class="material-icons right">check</i>
                </button>
            </div>
        </form>
    </div>
</div>