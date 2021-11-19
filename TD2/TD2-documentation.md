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

- Pour ajouter le jar dans le projet `Android`, il faut se mettre sur la vue `Project` dans l'explorateur de fichiers d'Android Studio. Ensuite, il faut glisser le JAR dans le fichier libs qui se trouve dans l'arborescence `nomDuProject/app/libs`. Après, aller dans `File -> Project Structure... -> Dependencies -> + (Add dependency)

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

## Partie II: Voler les contacts de l'utilisateur
