<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="taglib" uri="tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta content="content-type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title><taglib:message value="sign_up" /></title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="/resources/css/sign-up-page.css" rel="stylesheet" />
        <link href="/resources/css/common.css" rel="stylesheet" />
        <link href="/resources/css/indentations.css" rel="stylesheet" />
        <link href="/resources/css/materialize.min.css" rel="stylesheet" />
    </head>
    <body>
        <div class="yellow lighten-5 s-vflex-center">
            <div class="container">
                <div class="row">
                    <div class="col s12 m8 push-m2 my20">

                        <tf:languagesList className="my10 s-hflex-end" />

                        <div class="px10 radius-4 z-depth-1 auto-height">
                            <div class="sign-up-form-header s-hflex px10">
                                <h4 class="equal-flex"><taglib:message value="sign_up" /></h4>
                            </div>

                            <tf:errors errors="${registrationErrors}" />
                            
                            <form action="" method="post">
                                <div class="full-width s-hflex px5 my10">
                                    <label class="equal-flex">
                                        <input type="radio" name="role" value="user" checked>
                                        <span><taglib:message value="user" /></span>
                                    </label>
                                    <label class="equal-flex">
                                        <input type="radio" name="role" value="speaker">
                                        <span><taglib:message value="speaker" /></span>
                                    </label>
                                </div>
                                <div class="full-width input-field col s12 m6">
                                    <input id="login" type="text" name="login" value="<c:out value="${registrationFields.login}" />" />
                                    <label for="login"><taglib:message value="login" /></label>
                                    <span class="helper-text"><taglib:message value="required_field" /></span>
                                </div>
                                <div class="full-width input-field col s12 m6">
                                    <input id="email" type="email" name="email" value="<c:out value="${registrationFields.email}" />" />
                                    <label for="email"><taglib:message value="email" /></label>
                                    <span class="helper-text"><taglib:message value="required_field" /></span>
                                </div>
                                <div class="full-width input-field col s12 m6">
                                    <input id="name" type="text" name="name" value="<c:out value="${registrationFields.name}" />" />
                                    <label for="name"><taglib:message value="name" /></label>
                                    <span class="helper-text"><taglib:message value="required_field" /></span>
                                </div>
                                <div class="full-width input-field col s12 m6">
                                    <input id="surname" type="text" name="surname" value="<c:out value="${registrationFields.surname}" />" />
                                    <label for="surname"><taglib:message value="surname" /></label>
                                    <span class="helper-text"><taglib:message value="required_field" /></span>
                                </div>
                                <div class="full-width input-field col s12 m6">
                                    <input id="password" type="password" name="password" />
                                    <label for="password"><taglib:message value="password" /></label>
                                    <span class="helper-text"><taglib:message value="required_field" /></span>
                                </div>
                                <div class="full-width input-field col s12 m6">
                                    <input id="confirm-password" type="password" name="confirm_password" />
                                    <label for="confirm-password"><taglib:message value="confirm_password" /></label>
                                    <span class="helper-text"><taglib:message value="required_field" /></span>
                                </div>
                                <div class="full-width s-hflex-end">
                                    <a href="<taglib:linkTo href="/" />" class="btn waves-effect waves-light light-blue darken-4 mx10">
                                        <taglib:message value="login" /> <i class="material-icons right">send</i>
                                    </a>
                                    <button type="submit" class="btn waves-effect waves-light">
                                        <taglib:message value="sign_up" /> <i class="material-icons right">supervisor_account</i>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="/resources/js/materialize.min.js"></script>
    </body>
</html>
