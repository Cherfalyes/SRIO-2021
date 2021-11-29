var http = require('http');
const host = 'localhost';
const port = 8080;

var server = http.createServer((socket)=>{
    socket.on('data', function(data) {
        msg = JSON.parse(data);
        msgSplit = str.split('/');
        req = msgSplit[0]);
        switch(req){
            case 'POST' :

            break;

            default :
            break;
        }
    }
}).listen(port, host);