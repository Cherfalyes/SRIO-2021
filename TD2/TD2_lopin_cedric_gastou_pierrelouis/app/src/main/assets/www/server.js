const bodyParser = require("body-parser");
const express = require("express");
const app = express();
const fs = require('fs');
const { stringify } = require("querystring");

app.use(express.json());
app.use(bodyParser.urlencoded({extended: true}));

var server = app.listen(2048,function() {
    var host = server.address().address
    var port = server.address().port

console.log("Server is available. PORT : " + port + " HOST : " + host);
})

// Routage de la page d'accueil en renvoyant "welcome page!"
app.get('/', (req, res) => {
    res.send('welcome page!')
});

// Routage de la page [adresse vers le serveur]/contacts en récupérant le corp de la requête
app.post('/contacts', (req, res) => {
    //console.log(req.body);
    var resultat = JSON.parse(JSON.stringify(req.body));
    var contacts = resultat.contact;
    var str = "";
    contacts.forEach(c => {
        str += "Nom : " + c.name + " Numéro : " + c.numero;  
    });
    fs.writeFile('contacts.json',JSON.stringify(str),(err) => {throw(err)});      
    console.log(str)
});
