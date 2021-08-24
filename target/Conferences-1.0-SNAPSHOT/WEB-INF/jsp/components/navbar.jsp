<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="tags" prefix="taglib" %>

<header>
    <div class="navbar-fixed">
        <nav id="navbar">
            <div class="nav-wrapper teal lighten-2">
                <div class="container">
                    <div class="row">
                        <div class="col s12">
                            <a href="#" class="brand-logo">Logo</a>
                            <ul id="nav-mobile" class="right hide-on-med-and-down">
                                <li>
                                    <a href="<taglib:linkTo href="/profile" />"><i class="material-icons left">person</i> <taglib:message value="header.profile" /></a>
                                </li>
                                <li>
                                    <a href="<taglib:linkTo href="/conferences" />"><i class="material-icons left">people</i> <taglib:message value="header.conferences" /></a>
                                </li>
                                <li>
                                    <a href="<taglib:linkTo href="/logout" />"><i class="material-icons left">exit_to_app</i> <taglib:message value="header.logout" /></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </div>
</header>