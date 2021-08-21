<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="custom" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta content="content-type" content="text/html; charset=utf-8">

        <title>List test</title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
                        <div class="px10 radius-4 z-depth-1">
                            <form action="" method="post" class="auto-height">
                                <div class="full-width input-field col s12">
                                    <i class="material-icons prefix">account_circle</i>
                                    <input id="login" type="text" name="login" />
                                    <label for="login"><custom:message value="login" /></label>
                                </div>
                                <div class="full-width input-field col s12">
                                    <i class="material-icons prefix">lock</i>
                                    <input id="password" type="password" name="password" />
                                    <label for="password"><custom:message value="password" /></label>
                                </div>
                                <c:if test="${not empty errorMessage}">
                                    <div class="full-width col s12">
                                        <p class="center-align pink-text text-lighten-1 weight-normal login-error-message">
                                            <c:out value="${errorMessage}" />
                                        </p>
                                    </div>
                                </c:if>
                                <div class="col s6 pr5">
                                    <button class="full-width btn waves-effect waves-light red" type="button">
                                        <custom:message value="sign_up" /> <i class="material-icons right">supervisor_account</i>
                                    </button>
                                </div>
                                <div class="col s6 pl5">
                                    <button class="full-width btn waves-effect waves-light" type="submit">
                                        <custom:message value="login" /> <i class="material-icons right">send</i>
                                    </button>
                                </div>
                                <div class="col s12 center-align my10">
                                    <a href="/restore-password"><custom:message value="forgot_password" /></a>
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