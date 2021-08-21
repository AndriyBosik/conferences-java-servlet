<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Profile</title>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="/resources/css/site.css" rel="stylesheet" />
        <link href="/resources/css/common.css" rel="stylesheet" />
        <link href="/resources/css/indentations.css" rel="stylesheet" />
        <link href="/resources/css/materialize.min.css" rel="stylesheet" />
    </head>
    <body>
        <div class="navbar-fixed">
            <nav id="navbar">
                <div class="nav-wrapper teal lighten-2">
                    <div class="container">
                        <div class="row">
                            <div class="col s12">
                                <a href="#" class="brand-logo">Logo</a>
                                <ul id="nav-mobile" class="right hide-on-med-and-down">
                                    <li>
                                        <a href="/profile"><i class="material-icons left">person</i> Profile</a>
                                    </li>
                                    <li>
                                        <a href="/conferences"><i class="material-icons left">people</i> Conferences</a>
                                    </li>
                                    <li>
                                        <a href="/logout"><i class="material-icons left">exit_to_app</i> Logout</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        </div>

        <main>
            <div class="row">
                <div class="col full-width s-hflex">
                    <div class="equal-flex s-hflex-start m-hflex-end">
                        <div class="z-depth-1 user-avatar default radius-circle"></div>
                    </div>
                    <div class="col equal-flex s-hflex-start">
                        <div class="full-width s-vflex-center">
                            <div class="col file-field no-file-path input-field">
                                <div class="btn">
                                    <span>Upload<i class="material-icons right">file_upload</i></span>
                                    <input type="file">
                                </div>
                                <div class="file-path-wrapper">
                                    <input type="text" class="file-path validate">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <footer class="page-footer teal lighten-2">
            <div class="container">
                <div class="row">
                    <div class="col l6 s12">
                        <h5 class="white-text">Footer Content</h5>
                        <p class="grey-text text-lighten-4">
                            You can use rows and columns here to organize your footer content.
                        </p>
                    </div>
                    <div class="col l4 offset-12 s12">
                        <h5 class="white-text">Links</h5>
                        <ul>
                            <li>
                                <a href="#" class="grey-text text-lighten-3">Link 1</a>
                            </li>
                            <li>
                                <a href="#" class="grey-text text-lighten-3">Link 2</a>
                            </li>
                            <li>
                                <a href="#" class="grey-text text-lighten-3">Link 3</a>
                            </li>
                            <li>
                                <a href="#" class="grey-text text-lighten-3">Link 4</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="footer-copyright">
                <div class="container">
                    <jsp:useBean id="now" class="java.util.Date" />
                    <fmt:formatDate var="year" value="${now}" pattern="yyyy" /> Copyright Text
                    <a href="#" class="grey-text text-lighten-4 right">More Links</a>
                </div>
            </div>
        </footer>

        <script type="text/javascript" src="/resources/js/materialize.min.js"></script>
    </body>
</html>
