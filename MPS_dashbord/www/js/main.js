var WS = null;

var SOAP = {
    getInstances: function(data) {
        for (var i = 0; i < data.length; i++) {
            addInstance.call(data[i], data[i]);
        }
    }
};



function setStatus(host, port, color) {
    WS.send(JSON.stringify({
        func: 'setStatus',
        data: {
            arg0: host,
            arg1: port,
            arg2: color.toUpperCase()
        }
    }));
}

function addInstance(data) {

    var self = this,
        unique = this.host.replace(/\./g, '') + this.port,
        instance = $('#mps-instance-' + unique);

    if (instance.length == 0) {
        var template = $('#mps-instance-template').html(),
            instance = $(template);

        instance.attr('id', 'mps-instance-' + unique);

        $('.table tbody').append(instance);
    }

    instance.find('.yellow').off("click").click(function() {
        if (self.status != 'RED')
            setStatus(self.host, self.port, 'YELLOW');
    });

    instance.find('.green').off("click").click(function() {
        if (self.status != 'RED')
            setStatus(self.host, self.port, 'GREEN');
    });

    updateInstance.call(this, instance);
}

function updateInstance(instance) {

    // YYYY-MM-DD
    var signale = this.signal ? moment(parseInt(this.signal)).format('HH:mm:ss') : '',
        uptime = this.uptime ? moment(parseInt(this.uptime)).format('HH:mm:ss') : '',
        idletime = this.idletime ? moment(parseInt(this.idletime)).format('HH:mm:ss') : '',
        downtime = this.downtime ? moment(parseInt(this.downtime)).format('HH:mm:ss') : '';


    instance.find('.mps-host').html(this.host);
    instance.find('.mps-port').html(this.port);
    instance.find('.mps-queries').html(this.query);
    instance.find('.mps-load').html(this.load != "" ? this.load + "%" : "");
    instance.find('.mps-signal').html(signale);
    instance.find('.mps-uptime').html(uptime);
    instance.find('.mps-idletime').html(idletime);
    instance.find('.mps-downtime').html(downtime);

    var status = instance.find('.mps-status');
    switch (this.status) {
        case 'RED':
            status.find('.red').removeClass('grey');
            status.find('.yellow, .green').addClass('grey');
            break;
        case 'YELLOW':
            status.find('.yellow').removeClass('grey');
            status.find('.red, .green').addClass('grey');
            break;
        case 'GREEN':
            status.find('.green').removeClass('grey');
            status.find('.red, .yellow').addClass('grey');
            break;
    }
}

function refresh() {
    var rate = 1000;
    if (WS.readyState == WS.OPEN) {
        WS.send(JSON.stringify({
            func: "getInstances",
            data: {}
        }));
    }
    window.setTimeout(refresh, rate);
};


$(document).ready(function() {
    var host = '127.0.0.1',
        port = 8888,
        path = '/';

    var status = document.getElementById('wsstatus'),
        icon = document.getElementById('wsicon');

    WS = new WebSocket('ws://' + host + ':' + port + path);

    WS.onopen = function() {
        status.innerHTML = 'Connected';
        wsicon.style.color = 'green';
        wsicon.className = 'glyphicon glyphicon-ok';
        console.log('WebSocket connected');
        refresh();
    };

    WS.onmessage = function(message) {
        var obj = JSON.parse(message.data);
        if (obj.func && obj.data) {
            var func = SOAP[obj.func];
            if (typeof func == 'function') {
                var data = JSON.parse(obj.data);
                func.call(data, data);
            }
        }
    };

    WS.onerror = function(error) {
        status.innerHTML = 'Error';
        wsicon.style.color = 'red';
        wsicon.className = 'glyphicon glyphicon-remove';
        console.log('WebSocket error');
        console.log('WebSocket error', error);
    };

    WS.onclose = function() {
        status.innerHTML = 'Closed';
        wsicon.style.color = 'grey';
        wsicon.className = 'glyphicon glyphicon-repeat';
        console.log('WebSocket disconnected');
    };
});