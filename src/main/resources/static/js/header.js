// Stop notification menu to close when clicked inside
$("#notification-dropdown-menu").click(function (e) {
    e.stopPropagation();
});

// Toggles icon on notification dropdown click
function toggleEnvelope() {
    $('#notification-dropdown-icon').find('i').toggleClass('fa-envelope fa-envelope-open');
}