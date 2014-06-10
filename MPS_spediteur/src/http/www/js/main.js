function renderTransportauftrag(transportauftrag) {

    var template = $('#mps-transportauftrag-template').html(),
        instance = $(template);

    instance.find('.mps-transportauftragsNr').html(transportauftrag.transportauftragsNr);


    instance.find('.mps-ausgangsdatum').html(transportauftrag.ausgangsdatum);


    instance.find('.mps-lieferrungErfolgt input').click(function(){
        $.post('http://localhost:8080/spediteur/set/', JSON.stringify({
            taid: transportauftrag.transportauftragsNr,
            tale: instance.find('.mps-lieferrungErfolgt input').prop("checked")
        }));
        updateTransportauftrag();
    });
    if(transportauftrag.lieferungErfolgt === true){
        instance.find('.mps-lieferrungErfolgt input').prop("checked", true).attr("disabled", "disabled");
    }else{
        instance.find('.mps-lieferrungErfolgt input').prop("checked", false);
    }
    instance.find('.mps-transportdienstleister').html(transportauftrag.transportdienstleister);

    if(transportauftrag.lieferungErfolgt == false){
        instance.find('.delete').click(function(){
            if(confirm('Transportauftrag wirklich löschen?')){
                deleteTransportauftrag(transportauftrag.transportauftragsNr);
            }
        });
    }else{
        instance.find('.delete').hide();
    }

    return instance;
}

function updateTransportauftrag(){
    $('#mps-transportauftrag tbody').html('');
    $.get('http://localhost:8080/spediteur/get/', function(json){
        var element,
            taTable = $('#mps-transportauftrag tbody');
        for(var i=0; i<json.length; i++){
            element = renderTransportauftrag(json[i]);
            taTable.append(element);
        }
    });
}

function deleteTransportauftrag(tanr){
    $.post('http://localhost:8080/spediteur/delete', JSON.stringify({
        tanr: tanr
    }), function(bool){
        updateTransportauftrag();
    });
}

$(document).ready(function() {
    updateTransportauftrag();
});