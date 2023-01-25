var extraImagesCount = 0;
dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function() {

    $("#shortDescription").richText();
    $("#fullDescription").richText();

    getCategories();

    dropdownBrands.change(function() {
        dropdownCategories.empty();
        getCategories();
    });
});

function getCategories() {
    brandId = dropdownBrands.val();
    url = brandModuleURL + "/" + brandId + "/categories";

    $.get(url, function(responseJson) {
        $.each(responseJson, function (index, category) {
            $("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
        });
    });
}

function checkUnique(form) {
    productId = $("#id").val();
    productName = $("#name").val();

    csrfValue = $("input[name='_csrf']").val();

    params = {id: productId, name: productName, _csrf: csrfValue};

    $.post(checkUniqueUrl, params, function (response) {
        if (response == "OK") {
            form.submit();
        } else if (response == "DuplicateName") {
            showWarningModal("There is another brand having same name " + productName);
        } else {
            showErrorModal("Unknown response from server");
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });
    return false;
}