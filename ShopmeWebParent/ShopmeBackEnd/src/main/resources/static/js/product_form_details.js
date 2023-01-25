$(document).ready(function() {

    $("a[name='linkRemoveDetail']").each(function(index) {
        $(this).click(function() {
            removeDetailSectionByIndex(index);
        });
    });
});

function addNextDetailSection() {

    allDivDetails = $("[id^='divDetail']");
    divDetailsCount = allDivDetails.length;

    htmlDetailSection = `
    <div class="form-inline" id="divDetail${divDetailsCount}">
        <label class="m-3">Name: </label>
        <input type="text" class="form-control w-25" name="detailNames" maxlength="255" />
        <label class="m-3">Value: </label>
        <input type="text" class="form-control w-25" name="detailValues" maxlength="255" />
    </div>
    `;

    $("#divProductDetails").append(htmlDetailSection);

    previouseDivDetailSection = allDivDetails.last();
    previouseDivDetailId = previouseDivDetailSection.attr("id");

    htmlLinkRemove = `
    <a class="btn fas fa-times-circle fa-2x icon-dark"
    href="javascript:removeDetailSectionById('${previouseDivDetailId}')"
    title="Remove this detail"></a>
    `;
    previouseDivDetailSection.append(htmlLinkRemove);

    $("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
    $("#" + id).remove();
}

function removeDetailSectionByIndex(index) {
    $("#divDetail" + index).remove();
}