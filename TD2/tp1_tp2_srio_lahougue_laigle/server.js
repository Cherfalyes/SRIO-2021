const http = require("http");
const host = 'localhost';
const fs = require('fs')
const port = 8080;

const requestListener = function ( req , res) {
    // Spécifier le type du contenu à reçevoir (application/json)
	res.setHeader("Content-Type", "application/json");
	switch (req.url) { //traitement de l'url envoyée au serveur
		case "/": // Requête vers la page d'accueil 
			res.writeHead(200);
			res.end("Page d'accueil !");
            res.sendFile("")
			break
		case "/contacts": // requête vers la page d'authentification de la forme (adresseduServeur/contacts)
			res.writeHead(200);
            // Traitement de la requête de type GET
			if (req.method == 'GET')
			{
				res.end("Page d'authentification")
                // Traitement la requête de type POST
			}else if (req.method == 'POST')
			{
				var requete = ""
				req.on('data', chunk =>
				{
					requete += chunk;
				})
				req.on('end', () => {
					var informations = JSON.parse(requete)
                    console.log(informations);
                    // Enregistrer les informations contenues dans la requête dans le fichier "contacts.json" dans le répertoire courant du serveur
                    fs.writeFileSync('./contacts.json', JSON.stringify(requete));
                    // Vérifier si l'identifiant et le mot de passe envoyés correspondent bien aux données d'un compte client dans la base de données
                    //var authentification = checkInDatabase(informations[0].username, informations[0].password)
                    // authentification réussie
				})
			}
			break
		default: // Si l'utilisateur envoie une route qui n'existe pas au serveur
			res.writeHead(404);
			res.end(JSON.stringify({error:"Resource not found"}));
	}
}

const server = http.createServer(requestListener);
server.listen(port, host, () => {
    console.log(`Server is listening at http://${host}:${port}`);
});