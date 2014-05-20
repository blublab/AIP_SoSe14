var WS = null,
    host = '127.0.0.1',
    port = 8001,
    path = '/';

var SOAP = {
    getAllAngebote: function(data) {
        $('#mps-table-angebote tbody').html('');
        $('#angebote').html('');
        for (var i = 0; i < data.length; i++) {
            renderAngebot(data[i]);
        }
    },
    getAllAuftraege: function(data) {
        $('#mps-table-auftrag tbody').html('');
        for (var i = 0; i < data.length; i++) {
            renderAuftrag(data[i]);
        }
    },
    getAllBauteile: function(data) {
        $('#bauteil').html('');
        for (var i = 0; i < data.length; i++) {
            renderBauteil(data[i]);
        }
    },
    createAngebot: function(data) {
        alert(JSON.stringify(data));
        updateAngebote();
    },
    acceptAngebot: function(data) {
        alert(JSON.stringify(data));
        updateAngebote();
        updateAuftraege();
    }
};

function renderAngebot(data) {

    console.log(data);

    var template = $($('#mps-uebersicht-angebot').html());

    template.find('.mps-uebersicht-angebotNr').html(data.angebotNr);
    template.find('.mps-uebersicht-gueltingAb').html(new Date(parseInt(data.gueltingAb)));
    template.find('.mps-uebersicht-gueltigBis').html(new Date(parseInt(data.gueltigBis)));
    template.find('.mps-uebersicht-preis').html(data.preis + ' €');
    template.find('.mps-uebersicht-status').html(data.status);
    template.find('.mps-uebersicht-bauteil').html(data.bauteil);
    template.find('.mps-uebersicht-auftrag').html(data.auftrag);

    $('#mps-table-angebote tbody').append(template.get(0));

    // Select

    var template = $($('#mps-select-angebote').html());

    template.val(data.angebotNr).html("Angebot " + data.angebotNr);

    $('#angebote').append(template.get(0));
}

function renderAuftrag(data) {

    var template = $($('#mps-uebersicht-auftrag').html());

    template.find('.mps-uebersicht-auftragNr').html(data.auftragNr);
    template.find('.mps-uebersicht-istAbgeschlossen').html(data.istAbgeschlossen);
    template.find('.mps-uebersicht-beauftragtAm').html(moment(parseInt(data.beauftragtAm)).format('HH:mm:ss'));

    $('#mps-table-auftrag tbody').append(template.get(0));
}

function renderBauteil(data) {

    var template = $($('#mps-select-bauteile').html());

    template.val(data.bauteilNr).html(data.name);

    $('#bauteil').append(template.get(0));

}

function updateBauteile() {
    if (WS.readyState == WS.OPEN) {
        WS.send(JSON.stringify({
            func: "getAllBauteile",
            data: {}
        }));
    }
}

function updateAngebote() {
    if (WS.readyState == WS.OPEN) {
        WS.send(JSON.stringify({
            func: "getAllAngebote",
            data: {}
        }));
    }
}

function updateAuftraege() {
    if (WS.readyState == WS.OPEN) {
        WS.send(JSON.stringify({
            func: "getAllAuftraege",
            data: {}
        }));
    }
}

function createAngebot() {
    var nr = $('#kunde').val(),
        bt = $('#bauteil').val();
    WS.send(asd = JSON.stringify({
        func: "createAngebot",
        data: {arg0: nr, arg1: bt}
    }));
    $('.nav-tabs a[href="#uebersicht"]').tab('show');
}

function acceptAngebot() {
    var nr = $('#angebote').val();
    WS.send(JSON.stringify({
        func: "acceptAngebot",
        data: {arg0: nr}
    }));
    $('.nav-tabs a[href="#uebersicht"]').tab('show');
}

$(document).ready(function() {
    var status = document.getElementById('wsstatus'),
        icon = document.getElementById('wsicon');

    WS = new WebSocket('ws://' + host + ':' + port + path);

    WS.onopen = function() {
        status.innerHTML = 'Connected';
        wsicon.style.color = 'green';
        wsicon.className = 'glyphicon glyphicon-ok';
        console.log('WebSocket connected');
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
        console.log(error);
    };

    WS.onclose = function() {
        status.innerHTML = 'Closed';
        wsicon.style.color = 'grey';
        wsicon.className = 'glyphicon glyphicon-repeat';
        console.log('WebSocket disconnected');
    };

});