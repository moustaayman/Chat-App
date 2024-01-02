function updateChat() {
    $.ajax({
        url: 'ChatServlet',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#chatRoom').empty();
            const messagesHTML = data.map(function (message) {
                return '<li>' + message.content + '</li>';
            }).join("");
            $('#chatRoom').append(messagesHTML);
            $('#chatRoom').scrollTop($('#chatRoom')[0].scrollHeight);
        },
        error: function () {
            console.log('Error occurred while refreshing the chat room.');
        }
    });
}

updateChat();
setInterval(updateChat, 300);
function disconnectUser() {
    window.location.href = 'UserLogoutServlet';
}
$(document).on('click', '#disconnectButton', function() {
    disconnectUser();
});