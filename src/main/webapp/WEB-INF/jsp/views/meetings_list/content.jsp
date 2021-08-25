<%@ taglib uri="tags" prefix="taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <div class="s-hflex">
                <div class="equal-flex">
                    <h4 class="grey-text text-darken-2 mb0"><taglib:message value="header.meetings" /></h4>
                </div>
                <div class="s-vflex-end">
                    <button class="btn waves-effect waves-light" type="submit" name="action">
                        <taglib:message value="add" />
                        <i class="material-icons right">add</i>
                    </button>
                </div>
            </div>
            <hr />
        </div>
        <div class="meetings-list-area row">
            <c:forEach items="${meetings}" var="meeting">
                <div class="col s12 m4">
                    <div class="card hoverable meeting-card">
                        <div class="card-image stretch-background" style="background-image: url('/resources/images/${meeting.imagePath}')">
                            <a href="<taglib:linkTo href="/meetings/edit/${meeting.id}" />" class="tooltipped waves-light blue-text text-darken-3 top-right-element text-hoverable" data-position="right" data-tooltip="<taglib:message value="edit" />">
                                <i class="material-icons small">edit</i>
                            </a>

                            <a href="<taglib:linkTo href="/meetings/show/${meeting.id}" />" class="tooltipped btn-floating halfway-fab waves-effect waves-light blue darken-3" data-position="right" data-tooltip="<taglib:message value="view" />">
                                <i class="material-icons">arrow_forward</i>
                            </a>
                        </div>
                        <div class="card-content">
                            <span class="card-title truncate" style="font-weight: 400;">${meeting.title}</span>
                            <p class="truncate-4 translucent-3p">
                                ${meeting.description}
                            </p>
                            <hr class="date-divider" />
                            <div class="s-hflex-end">
                                <span class="translucent">
                                    <fmt:formatDate value="${meeting.date}" pattern="dd-MM-yyyy HH:mm" />
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="pages-area">
            <ul class="pagination center-align">
                <li class="disabled">
                    <a href="#"><i class="material-icons">chevron_left</i></a>
                </li>

                <li class="active teal"><a href="#">1</a></li>
                <li class="waves-effect"><a href="#">2</a></li>
                <li class="waves-effect"><a href="#">3</a></li>
                <li class="waves-effect"><a href="#">4</a></li>
                <li class="waves-effect"><a href="#">5</a></li>
                <li class="waves-effect"><a href="#">6</a></li>
                <li class="waves-effect"><a href="#">7</a></li>

                <li class="waves-effect">
                    <a href="#!"><i class="material-icons">chevron_right</i></a>
                </li>
            </ul>
        </div>
    </div>
</div>