$(document).ready(function() {
    $("#logoutLink").on("click", function(e) {
        e.preventDefault();
        document.logoutForm.submit();
    });

    customizeDropDownMenu();
});


$(document).ready(function() {
    $(".link-delete-user").on("click", function(e) {
        e.preventDefault();
        const link = $(this);
        // alert($(this).attr("href"));
        const userId = link.attr("userId");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this user ID" + userId + "?");
        $("#confirmModal").modal();
    });
});

$(document).ready(function() {
    $(".link-delete-category").on("click", function(e) {
        e.preventDefault();
        const link = $(this);
        // alert($(this).attr("href"));
        const categoryId = link.attr("categoryId");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this category ID" + categoryId + "?");
        $("#confirmModal").modal();
    });
});


function clearFilter() {
    window.location = "[[@{/users/}]]";
}

function customizeDropDownMenu() {
    $(".navbar .dropdown").hover(
        function() {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function() {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    )


    $(".dropdown > a").click(function () {
        location.href = this.href;
    })
}