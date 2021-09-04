<%@ taglib prefix="taglib" uri="tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>500 Error</title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="/resources/css/common.css" rel="stylesheet" />
        <link href="/resources/css/indentations.css" rel="stylesheet" />
        <link href="/resources/css/materialize.min.css" rel="stylesheet" />
        <link href="/resources/css/materialize-extensions.css" rel="stylesheet" />
        <link href="/resources/css/error-pages.css" rel="stylesheet" />
    </head>
    <body>
        <div class="error-code">500</div>
        <div class="error-message">Internal server error</div>
        <div>
            <a href="<taglib:linkTo href="/" />" class="btn waves-effect waves-light"><i class="material-icons left">chevron_left</i>Go Home</a>
        </div>
    </body>
</html>
