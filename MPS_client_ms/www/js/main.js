$(document).ready(function() {
    var host = '127.0.0.1',
        port = 8001,
        path = '/';

    var status = document.getElementById('wsstatus'),
        icon = document.getElementById('wsicon');

    var ws = new WebSocket('ws://' + host + ':' + port + path);

    ws.onopen = function() {
        status.innerHTML = 'Connected';
        wsicon.style.color = 'green';
        wsicon.className = 'glyphicon glyphicon-ok';
        console.log('WebSocket connected');
    };

    ws.onmessage = function(message) {
        var obj = JSON.parse(message.data);
        if (obj.func && obj.data) {
            var func = window[obj.func];
            if (typeof func == 'function') {
                func.call(obj.data);
            }
        }
    };

    ws.onerror = function(error) {
        status.innerHTML = 'Error';
        wsicon.style.color = 'red';
        wsicon.className = 'glyphicon glyphicon-remove';
        console.log('WebSocket error');
        console.log(error);
    };

    ws.onclose = function() {
        status.innerHTML = 'Closed';
        wsicon.style.color = 'grey';
        wsicon.className = 'glyphicon glyphicon-repeat';
        console.log('WebSocket disconnected');
    };

});