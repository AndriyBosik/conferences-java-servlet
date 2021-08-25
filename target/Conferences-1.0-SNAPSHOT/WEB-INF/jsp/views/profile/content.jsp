<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <h4 class="grey-text text-darken-2 mb0"><taglib:message value="header.profile" /></h4>
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