<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<header>
    <div class="navbar-fixed">
        <nav id="navbar">
            <div class="nav-wrapper teal lighten-2">
                <div class="container">
                    <div class="row">
                        <div class="col s12">
                            <ul id="nav-mobile" class="left hide-on-med-and-down">
                                <li>
                                    <a href="<taglib:linkTo href="/home/profile" />" class="tooltipped" data-position="bottom" data-tooltip="<taglib:message value="header.profile" />">
                                        <i class="material-icons">person</i>
                                    </a>
                                </li>
                                <li>
                                    <a href="<taglib:linkTo href="/meetings/all" />" class="tooltipped" data-position="bottom" data-tooltip="<taglib:message value="header.meetings" />">
                                        <i class="material-icons">people</i>
                                    </a>
                                </li>
                                <li>
                                    <a href="<taglib:linkTo href="/home/logout" />" class="tooltipped" data-position="bottom" data-tooltip="<taglib:message value="header.logout" />">
                                        <i class="material-icons">exit_to_app</i>
                                    </a>
                                </li>
                            </ul>

                            <a href="<taglib:linkTo href="/home/profile" />" class="brand-logo center full-height">
                                <div class="full-height p10">
                                    <img src="/resources/images/logo.png" alt="Logo" class="full-height">
                                </div>
                            </a>

                            <tf:languagesList
                                className="right"
                                basicLinkClass="white-text"
                                activeLinkClass="light-blue-text text-darken-4 weight-strong" />

                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </div>
</header>