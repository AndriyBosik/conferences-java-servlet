<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta content="content-type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title><taglib:message value="page.title.login" /></title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="/resources/css/site.css" rel="stylesheet" />
        <link href="/resources/css/login-page.css" rel="stylesheet" />
        <link href="/resources/css/common.css" rel="stylesheet" />
        <link href="/resources/css/indentations.css" rel="stylesheet" />
        <link href="/resources/css/materialize.min.css" rel="stylesheet" />
    </head>

    <body>
        <div class="window-height window-width yellow lighten-5 s-vflex-center">
            <div class="container">
                <div class="row">
                    <div class="col s12 m4 push-m4">

                        <tf:languagesList className="my10 s-hflex-end" />

                        <div class="px10 radius-4 z-depth-1">
                            <form action="" method="post" class="auto-height">
                                <div class="full-width input-field col s12">
                                    <i class="material-icons prefix">account_circle</i>
                                    <input id="login" type="text" name="login" />
                                    <label for="login"><taglib:message value="login" /></label>
                                </div>
                                <div class="full-width input-field col s12">
                                    <i class="material-icons prefix">lock</i>
                                    <input id="password" type="password" name="password" />
                                    <label for="password"><taglib:message value="password" /></label>
                                </div>
                                <c:if test="${not empty errorMessage}">
                                    <div class="full-width col s12">
                                        <p class="center-align pink-text text-lighten-1 weight-normal login-errorKey-message">
                                            <c:out value="${errorMessage}" />
                                        </p>
                                    </div>
                                </c:if>
                                <div class="col s12">
                                    <button class="full-width btn waves-effect waves-light" type="submit">
                                        <taglib:message value="login" />
                                    </button>
                                </div>
                                <div class="col s12 my15 weight-normal center-align">
                                    <span class="translucent-3p"><taglib:message value="dont_have_account" /> </span>
                                    <a href="<taglib:linkTo href="/users/sign-up" />"><taglib:message value="sign_up" /></a>
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