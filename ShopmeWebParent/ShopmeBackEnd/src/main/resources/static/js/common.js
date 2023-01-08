$(document).ready(function() {
    $("#logoutLink").on("click", function(e) {
        e.preventDefault();
        document.logoutForm.submit();
    });
});


$(document).ready(function() {
    $(".link-delete").on("click", function(e) {
        e.preventDefault();
        const link = $(this);
        // alert($(this).attr("href"));
        const userId = link.attr("userId");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this user ID" + userId + "?");
        $("#confirmModal").modal();
    });
});


function clearFilter() {
    window.location = "[[@{/users/}]]";
}