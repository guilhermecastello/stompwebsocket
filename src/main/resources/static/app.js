var stompClient = null;
var notificationCount = 0;

$(document).ready(function() {
    console.log("Index page is ready.");
    connect();

    $("#send").click(function(){
        sendMessage();
    });
    $("#send-private").click(function(){
        sendPrivateMessage();
    });
    $("#notifications").click(function(){
        resetNotificationDisplay();
    });

});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/app-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).text);
        });
        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body).text);
        });
        stompClient.subscribe('/topic/global-notification', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
        stompClient.subscribe('/user/topic/private-notification', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    console.log("Sending message." + $("#message").val());
    stompClient.send("/ws/message", {}, JSON.stringify({'text': $("#message").val()}));
}

function sendPrivateMessage() {
    console.log("Sending private message." + $("#message").val());
    stompClient.send("/ws/private-message", {}, JSON.stringify({'text': $("#message").val()}));
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function updateNotificationDisplay() {
    if (notificationCount == 0) {
        $('#notifications').hide();
    } else {
        $('#notifications').show();
        $('#notifications').text(notificationCount);
    }
}

function resetNotificationDisplay() {
    notificationCount = 0;
    updateNotificationDisplay();
}
