<%@ taglib uri="tags" prefix="taglib" %>

<div id="meeting-form" class="modal">
    <form action="" method="post" enctype="multipart/form-data">
        <div class="modal-content row">
            <h5 class="col s12"><taglib:message value="create_meeting" /></h5>

            <div class="input-field col s12 m6">
                <input id="meeting-title" name="title" type="text">
                <label for="meeting-title"><taglib:message value="title" /></label>
            </div>

            <div class="input-field col s12 m6">
                <input id="meeting-address" name="address" type="text">
                <label for="meeting-address"><taglib:message value="address" /></label>
            </div>

            <div class="col s12 m6 s-hflex-center">
                <input type="text" name="date" placeholder="<taglib:message value="select_date" />" class="date-picker" />
                <div class="col">
                    <input type="number" name="hours" min="0" max="23" value="12" class="center-align" />
                </div>
                <span class="s-vflex-center weight-normal time-divider">:</span>
                <div class="col">
                    <input type="number" name="minutes" min="0" max="59" value="00" class="center-align" />
                </div>
            </div>

            <div class="col s12 m6">
                <div class="file-field input-field" style="margin: 0px;">
                    <div class="btn">
                        <span><taglib:message value="photo" /></span>
                        <input type="file" name="image_path" accept="image/*" />
                    </div>
                    <div class="file-path-wrapper">
                        <input type="text" class="file-path validate" />
                    </div>
                </div>
            </div>

            <div class="input-field col s12">
                <textarea id="meeting-description" class="materialize-textarea" name="description"></textarea>
                <label for="meeting-description"><taglib:message value="description" /></label>
            </div>

        </div>
        <div class="modal-footer">
            <div class="col px20">
                <button type="submit" class="btn waves-effect waves-light">
                    <taglib:message value="confirm" />
                    <i class="material-icons right">check</i>
                </button>
            </div>
        </div>
    </form>
</div>