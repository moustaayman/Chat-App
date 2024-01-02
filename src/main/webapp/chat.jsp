<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <!-- Font awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css">

    <link rel="stylesheet" type="text/css" href="styles_chat.css">
</head>
<body>
<div class="container-fluid h-100">
    <div class="row justify-content-center h-100">
        <div class="col-md-4 col-xl-3 chat">
            <div class="card mb-sm-3 mb-md-0 contacts_card">
                <div class="card-header">

                </div>
                <div class="card-body contacts_body" id="userList">
                    <!-- ///////////////////////////////-->
                </div>
                <div class="card-footer"></div>
            </div>
        </div>
        <div class="col-md-8 col-xl-6 chat">
            <div class="card">
                <div class="card-body msg_card_body" id="chatMessages">
                    <!-- ///////////////////////////////-->
                </div>
                <div class="card-footer">
                    <div class="input-group">
                        <textarea name="newMessage" class="form-control type_msg" placeholder="Type your message..." id="newMessage"></textarea>
                        <div class="input-group-append">
                            <span class="input-group-text send_btn" id="sendMessage"><i class="fas fa-location-arrow"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <button class="btn transparent" id="disconnect-btn">Disconnect</button>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="module">
    $(document).on('click', '#sendMessage', function() {
        sendMessage();
    });
    function updateUserStatus() {
        const now = new Date();
        $('.user_info').each(function() {
            const lastConnectionTime = new Date($(this).data('last-connection'));
            const userId = $(this).data('user-id');
            const statusElement = $(this).find('p');

            // Déterminez le statut en fonction du temps écoulé depuis la dernière connexion
            const timeDiff = now - lastConnectionTime;
            const minutesSinceLastConnection = Math.floor(timeDiff / (1000 * 60));

            if (minutesSinceLastConnection < 5) {
                statusElement.text('Online');
            } else if (minutesSinceLastConnection < 10) {
                statusElement.text('Away');
            } else {
                statusElement.text('Offline');
            }
        });
    }
    function loadUserList() {
        $.ajax({
            url: 'UserServlet',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#userList').empty();
                const users = data.map(function (user) {
                    let colorClass;
                    if(user.status === "ONLINE") colorClass = "status-green-icon";
                    if(user.status === "OFFLINE") colorClass = "status-yellow-icon";
                    if(user.status === "BANNED") colorClass = "status-red-icon";
                    return (
                        '<ul class="contacts">' +
                        '<li class="active" data-user-id="' + user.USER_ID + '">' +
                        '<div class="bd-highlight" style="position: relative; display: flex">' +
                        '<div class="img_cont">' +
                        '<img src="./images/user.jpeg" class="rounded-circle user_img"/>' +
                        '<span class="online_icon '+colorClass+'"></span>' +
                        '</div>' +
                        '<div class="user_info" id="userInfo">' +
                        '<span id="userName">' + user.username + '</span>' +
                        '<p id="userStatus">'+ user.status +'</p>' +
                        '</div>' +
                        '<span id="action_menu_btn"><i class="fas fa-ban banUserBtn"></i></span>'+
                        '<div class="action_menu">'+
                        '</div>'+
                        '</div>' +
                        '</li>' +
                        '</ul>'
                    );
                }).join("");
                $('#userList').append(users);
                $('.banUserBtn').click(function() {
                    const userId = $(this).closest('li').data('user-id');
                    $.ajax({
                        url: 'UserServlet',
                        type: 'POST',
                        data: { action: 'ban', userId:userId},
                        success: function (data) {
                            console.log('User banned successfully, user to ban :'+userId);
                        },
                        error: function () {
                            console.log('Error occurred while banning the user.');
                        }
                    });
                });
            },
            error: function () {
                console.log('Error occurred while fetching user list.');
            }
        });
    }

    function loadChatMessages() {
        const userId = getQueryParam('userId');
        $.ajax({
            url: 'ChatServlet',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                const chatMessages = $('#chatMessages');
                const isScrolledToBottom = chatMessages.scrollTop() + chatMessages.outerHeight() >= chatMessages[0].scrollHeight;
                $('#chatMessages').empty();
                const messages = data.map(function (message) {
                    const justifyClass = message.user.USER_ID == userId ? 'justify-content-end' : 'justify-content-start';
                    return (
                        '<div class="d-flex ' + justifyClass + ' mb-4">' +
                        '<div class="img_cont_msg">' +
                        '<img ' +
                        'src="./images/user.jpeg" ' +
                        'class="rounded-circle user_img_msg" />' +
                        '</div>' +
                        '<div class="msg_cotainer">' +
                        '<div class="msg_user">'+
                        message.user.username+
                        '</div>'+
                        '<div class="msg_content_container" '+
                        '<div class="msg_content">'+
                        message.content+
                        '</div>'+
                        '<div class="msg_time_container">' +
                        '<span class="msg_time">' + message.timestamp + '</span>' +
                        '</div>'+
                        '</div>'+
                        '</div>' +
                        '</div>'
                    );
                }).join("");
                $('#chatMessages').append(messages);
                if(isScrolledToBottom) chatMessages.scrollTop(chatMessages[0].scrollHeight);
            },
            error: function () {
                console.log('Error occurred while fetching chat messages.');
            }
        });
    }
    function getQueryParam(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }
    function sendMessage() {
        const newMessage = $('#newMessage').val();
        $.ajax({
            url: 'SendMessageServlet',
            type: 'POST',
            data: { newMessage: newMessage },
            success: function () {
                document.getElementById('newMessage').value = '';
                loadChatMessages();
            },
            error: function () {
                console.log('Error occurred while sending the message.');
            }
        });
    }

    setInterval(() => {
        loadChatMessages();
    }, 100);
    setInterval(()=>{
        loadUserList();
    },500)
    $(document).ready(function() {
        $('#disconnect-btn').click(function() {
            window.location.href = 'UserLogoutServlet';
        });
    });

</script>
</body>
</html>
