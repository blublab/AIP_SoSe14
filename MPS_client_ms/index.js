/***************/
/*** Modules ***/
/***************/
var wsLib = require('./lib/ws'),
    dgramLib = require('dgram'),
    httpLib = require('http'),
    soapLib = require('./lib/soap'),
    fsLib = require('fs'),
    pathLib = require("path");

/*********************/
/*** Configuration ***/
/*********************/
var WStoSOAP = {},

    wsServerHost = '127.0.0.1',
    wsServerPort = 8001,

    httpServerHost = '127.0.0.1',
    httpServerPort = 8002,
    documentRoot = './www',
    defaultFile = '/index.html',
    mimeTypes = {
        html: 'text/html',
        css: 'text/css',
        js: 'text/javascript',
        svg: 'image/svg+xml',
        eot: 'application/vnd.ms-fontobject',
        ttf: 'application/octet-stream',
        woff: 'application/x-font-woff'
    },

    soapDispatcherProtocol = 'http',
    soapDispatcherHost = '127.0.0.1',
    soapDispatcherPort = 7891,
    soapDispatcherPath = 'dispatcher?wsdl';


/************************/
/*** WebSocket Server ***/
/************************/
var wsServer = new wsLib.Server({
    host: wsServerHost,
    port: wsServerPort
}, function() {
    console.log('WebSocket Server ' + wsServerHost + ':' + wsServerPort);
});
wsServer.on('connection', function(ws) {

    console.log('Client-GUI user connected (' + wsServer.clients.length + ')');

    ws.on('message', function(msg) {
        try {
            var message = JSON.parse(msg);
            WStoSOAP[message.func](message.data, function(err, result) {
                if (!err) {
                    try {
                        JSON.parse(result.return);
                        ws.send(JSON.stringify({
                            func: message.func,
                            data: result.return
                        }));
                    } catch (e) {
                        console.log("Invalid return from WebService (" + message.func + ")");
                    }
                }
            });
        } catch (e) {
            console.log('Invalid message from WebSocket');
        }
    });
});

/*******************/
/*** SOAP CLient ***/
/*******************/
WStoSOAP = {
    getAllAngebote: function(data, func){
        func(false, {"return": '[{"angebotNr": 2, "gueltingAb": "19.12.2013", "gueltigBis": "23.12.2013", "preis": "59.23", "status": "angenommen", "bauteil": 40, "auftrag": 3}, {"angebotNr": 1, "gueltingAb": "5.12.2013", "gueltigBis": "10.12.2013", "preis": "20", "status": "", "bauteil": 12, "auftrag": null}]'})
    },
    getAllAuftraege: function(data, func){
        func(false, {"return": '[{"auftragNr": 5, "istAbgeschlossen": "false", "beauftragtAm": "04.01.2003"}]'})
    },
    getAllBauteile: function(data, func){
        func(false, {"return": '[{"bauteilNr": 321, "name": "Rad"}, {"bauteilNr": 654, "name": "Axt"}, {"bauteilNr": 987, "name": "Pümpel"}]'});
    },
    createAngebot: function(data, func){
        func(false, {"return": '["created???"]'});
    },
    acceptAngebot: function(data, func){
        func(false, {"return": '["accepted???"]'});
    }
}
/*
var endpoint = "monitor.WebServiceInterface",
    url = soapDispatcherProtocol + '://' + soapDispatcherHost + ':' + soapDispatcherPort + '/' + soapDispatcherPath;

soapLib.createClient(url, endpoint, function(err, client) {

    if (err) {
        console.log(err);
        return;
    }

    for (var name in client.describe().WebServiceImplService.WebServiceImplPort) {
        WStoSOAP[name] = (function(key) {
            return function(args, func) {
                if (args === Object(args) && typeof func === 'function') {
                    client[key](args, func);
                }
            };
        })(name);
    }

});
*/


/*******************/
/*** HTTP Server ***/
/*******************/
var httpServer = httpLib.createServer(function(request, response) {
    var requestUrl = documentRoot + (request.url === '/' ? defaultFile : request.url);

    if (fsLib.existsSync(requestUrl) && fsLib.lstatSync(requestUrl).isFile()) {

        var content;
        if (content = fsLib.readFileSync(requestUrl)) {

            var extension = requestUrl.replace(/.+\.([^\.]+)$/i, '$1'),
                contentType = extension in mimeTypes ? mimeTypes[extension] : 'text/plain';

            //console.log('OK : ' + requestUrl);
            response.writeHeader(200, {
                'Content-Type': contentType,
                'Content-Length': content.length,
                'Connection': 'close'
            });
            response.end(content, 'utf-8');
        } else {
            //console.log('ERROR : ' + requestUrl);
            response.writeHeader(500);
            response.end();
        }
    } else {
        //console.log('NOT FOUND: ' + requestUrl);
        response.writeHeader(404);
        response.end();
    }
});
httpServer.listen(httpServerPort, httpServerHost, function() {
    console.log('HTTP Server ' + httpServerHost + ':' + httpServerPort);
});