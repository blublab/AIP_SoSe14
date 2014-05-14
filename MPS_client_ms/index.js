/*********************/
/*** Modules ***/
/*********************/
var wsLib = require('./lib/ws'),
    dgramLib = require('dgram'),
    httpLib = require('http'),
    soapLib = require('./lib/soap'),
    fsLib = require('fs'),
    pathLib = require("path");

/*********************/
/*** Configuration ***/
/*********************/
var httpServerHost = '127.0.0.1',
    httpServerPort = 8000,
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
    wsServerHost = '127.0.0.1',
    wsServerPort = 8001,
    soapDispatcherProtocol = 'http',
    soapDispatcherHost = 'www.webservicex.net',
    soapDispatcherPort = 80,
    soapDispatcherPath = 'CurrencyConvertor.asmx?WSDL';



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

    ws.on('message', function(message) {
        var client = dgramLib.createSocket('udp4'),
            buffer = new Buffer(message.toString());
        client.send(buffer, 0, buffer.length, udpDispatcherPort, udpDispatcherHost, function(err, bytes) {
            client.close();
        });
    });
});



/*******************/
/*** SOAP Client ***/
/*******************/


soapDispatcherProtocol = 'http';
soapDispatcherHost = 'localhost';
soapDispatcherPort = 4000;
soapDispatcherPath = 'dispatcher?WSDL';

var endpoint = "main.dispatcherWS.IDispatcherWSForClient";
var soapClient = soapLib.createClient(soapDispatcherProtocol + '://' + soapDispatcherHost + ':' + soapDispatcherPort + '/' + soapDispatcherPath, endpoint, function(err, client) {

    if (err) {
        console.log(err);
        return;
    }

    console.log(client.describe());

    client.echo({
        arg0: 'XXX',
    }, function(err, result) {

        if (err) {
            console.log(err);
            return;
        }

        console.log(result);

    });

});

/*
var soapClient = soapLib.createClient(soapDispatcherProtocol + '://' + soapDispatcherHost + ':' + soapDispatcherPort + '/' + soapDispatcherPath, function(err, client) {

    if (err) {
        console.log(err);
        return;
    }

    console.log(client.describe());

    client.ConversionRate({
        FromCurrency: 'EUR',
        ToCurrency: 'USD'
    }, function(err, result) {

        if (err) {
            console.log(err);
            return;
        }

        console.log(result);

    });

});
*/
