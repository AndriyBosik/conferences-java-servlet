<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="tags" prefix="taglib" %>

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