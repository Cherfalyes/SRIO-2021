# Comprendre les enjeux de sécurité sur Android

Le but de ce TP est de vous sensibiliser et de vous familiariser avec certaines notions de sécurité informatique.
Les pratiques que vous allez mettre en oeuvre ne sont autorisées que sur du matériel vous appartenant ou pour lequel
vous avez les autorisations écrites nécessaires émanant des autorités idoines.

**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinèas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende.

## Consignes

### Rapport écrit

Afin d'évaluer correctement vos connaissances, il vous est demandé d'écrire et de rendre un rapport qui montre à l'enseignant votre compréhension du sujet.
Ce rapport doit fidèlement rendre compte de votre travail.
Vous y écrirez ce que vous comprenez de l'exercice, les différents problèmes que vous rencontrez et comment vous réussissez à les résoudre.
Ce rapport doit respecter les consignes suivantes:
- Le rapport est fichier `.md` (markdown) ou `.pdf` appelé `rapport_[nom_prénom]_[nom_prénom].md` (ou `rapport_[nom_prénom]_[nom_prénom].pdf`) que vous placerez dans le dossier correspondant au TD évalués (e.g `td3`) de votre répertoire git.
- Il vous est demandé d'expliquer les différentes étapes du TP et de montrer que vous les avez comprises.
- Dans certaines parties du TP, il vous est demandé de répondre à des questions théoriques. Vous répondrez à ces questions dans le rapport dans un français correct et en prenant soin de les numéroter.
- Seuls les rapports au format `.md` et `.pdf` seront pris en compte dans l'évaluation (attention à ne pas rendre votre rapport au format `.odt` ou `.doc`).
- Copier/coller des lignes de commande ou du code sans en expliquer leur fonctionnement n'aura aucune valeur dans l'évaluation de votre rapport.

### Code

Vous serez également évalué sur le code produit pendant le TD.
Les consignes suivantes sont à respecter :
- Le code produit doit pouvoir être compilé sur n'importe quelle machine qui possède l'environnement de développement standard (Android Studio dans le cadre de ce TP). Tout code qui ne compile pas sur la machine de l'enseignant ne sera pas pris en compte dans l'évaluation.
- Le code doit être clair et commenté.
- Pour chaque nouveau projet d'application Android, vous placerez le répertoire complet du projet (incluant les fichiers nécessaires à sa compilation) à dans le dossier portant le nom du TD courant (e.g `td1`) et vous lui donnerez un nom explicite tel que `td1_partie1_webview`.
- Avoir *Android Studio* ou *Intellij* installé sur sa machine.
- Vous prendrez soin de vérifier que votre application s'exécute correctement sur un émulateur qui possède les propriétés suivantes :
    - Device model: Pixel 2
    - Version de l'OS: Android 10 (Q, API 29), with Google Play
    - CPU architecture: x86
    
Il est de votre responsabilité de vérifier que l'application Android que vous développez est compatible avec la version d'Android et l'appareil mentionnés ci-dessus.
Toute application non fonctionnelle en raison de problèmes d'incompatibilité ne sera pas prise en compte dans la note finale du TP.

## Contexte

En raison de sa popularité, la plateforme Android est une cible de choix pour des personnes malveillantes qui voudraient attaquer un grand nombre de personnes.
Depuis l'avènement d'Android sur le marché des smartphones, le nombre d'appareils infectés par des malware n'a fait qu'augmenter.
Les auteurs de malware fabriquent des applications qui ont pour but d'espionner ou de voler des utilisateurs de smart devices.
Pour mieux se protéger de cette menace, il est important de comprendre et de connaitre les mécanismes mis en oeuvre par ces logiciels malveillant.

Pour maximiser leur impact, les malwares dissimulent leurs comportements malicieux aux yeux de l'utilisateur en
se faisant passer pour des applications dites bénignes (i.e avec un comportement qui semble légitime).
Cela permet à une application mal intentionnée de rester plus longtemps en activité sur le téléphone de la cible et donc de faire plus de dégâts.

## Objectif
Dans ce TP, il vous est demandé d'exploiter les faiblesses du système d'exploitation Android pour voler des données personnelles de l'utilisateur à son insu.
Plus particulièrement, vous allez exploiter les vulnérabilités de *Webview*, explorées au cours du TP précédent, pour concevoir une application Android qui envoie des données collectées sur un téléphone à un serveur pirate distant.

L'objectif final de ce TD est de développer votre propre malware maquillé sous la forme d'un carnet d'adresses.

### Partie I: Implémenter un carnet d'adresses

Dans cette partie, il vous est demandé de développer la partie inoffensive (bénigne) de l'application, qui servira plus tard de camouflage pour déjouer la vigilance de l'utilisateur.

Voici les caractéristiques de l'application:

L'application est un carnet d'adresses qui permet à l'utilisateur de parcourir les différents contacts de son téléphone.
Lors de la sélection d'un contact, l'utilisateur peut au choix lui envoyer un sms ou composer son numéro de téléphone.

* Ecrivez le code Javascript et Java nécessaire pour récupérer les contacts de l'utilisateur et les afficher sous forme de liste au sein de la Webview.
    * Pour chaque contact, récupérez le son nom ainsi que son numéro de téléphone
    * Pour passer les données au contexte Webview, formattez les contacts au format `JSON` et sérialisez le tableau `JSON` obtenu en une `String`.
    * Pour récupérer les données dans le contexte Webview, dé-sérialisez la `String` obtenue pour obtenir un objet javascript manipulable.
* Ecrivez le code Javascript nécessaire pour pouvoir saisir le contenu d'un SMS après sélection d'un contact.
* Ecrivez le bridge qui permet d'envoyer un SMS à un contact à partir du contenu saisi lors de l'action précédente.
* Ecrivez le code nécessaire qui permet de notifier l'utilisateur que le SMS a bien été envoyé.
* Ecrivez le bridge qui permet de composer le numéro de téléphone d'un contact sélectionné.

#### Questions:

1. Pourquoi est-il nécessaire de sérialiser les données de contacts pour les passer du contexte Java au contexte Webview ?
2. Quels sont les types de données qui peuvent être utilisés pour passer des données de Java à Webview ?
3. Quelles permissions doivent être accordées à l'application pour être complètement fonctionnelle ?

### Partie II: Voler les contacts de l'utilisateur

Dans cette partie, vous allez modifier le code de l'application produit dans la partie 1 pour envoyer les contacts enregitrés dans le téléphone lorsque l'utilisateur ouvre votre application.

Dans certains scénarios malicieux, un pirate subtilise les contacts de plusieurs centaines de téléphones pour 
ensuite effectuer des tentatives phishing avec les numéros de téléphones collectés.
<!--
A partir du travail effectué lors du TD5, il vous est demandé de mettre à jour le code de votre application pour 
collecter les contacts de l'utilisateur.
-->

<!--
1. Ecrivez le code nécessaire pour implémenter le bridge qui permet de collecter les contacts de l'utilisateur dans 
le contexte de la *Webview*.
2. Ecrivez le bridge nécessaire qui permet de récupérer l'identifiant unique du téléphone dans le contexte de la Webview
-->

#### Etape 1: Envoyer les contacts à un serveur distant.

Dans un premier temps, il vous est demandé d'écrire le code Javascript nécessaire pour envoyer la liste de contacts
ainsi que le numéro `imei` collecté à un serveur distant.

Le message envoyé doit être au format `JSON` et aura la structure suivante :
```json
{
	"imei": "string",
	"contacts": [
		{
			"name": "string",
			"phone_number": "string",
			"mail_address": "string"
		}
	]
}
```
Pour garantir l'unicité des données, les contacts que vous collectez sur chaque téléphone doivent être associés à un identifiant unique.
Chaque Android device possède un identifiant unique qu'il est possible d'obtenir en appelant la méthode 
`TelephonyManager.getImei()` ([documentation](https://developer.android.com/reference/android/telephony/TelephonyManager#getImei())).

* Ecrivez le code __Javascript__ (dans le context de Webview) nécessaire pour envoyer une requête `http` contenant le message décrit précédemment.

##### Questions
1. Comment se prémunir d'une attaque comme celle que vous développez ?
2. Comment vous assurer que qu'une application installée sur votre téléphone fait uniquement ce qu'elle prétend offrir comme service et pas plus ?


#### Etape 2: Développer un simple serveur web pirate

L'objectif de cette partie est de concevoir un serveur pirate capable de recevoir des données au format `json` au travers du protocol `http`.
Vous écrirez le code nécessaire dans le répertoire suivant : `td6/pirate_server`.

Voici les spécifications requises pour le serveur : 

- Le serveur doit utiliser la plateforme `node.js`.
- Le serveur doit communiquer avec ses clients au travers du protocole `http`.
- Le serveur doit être capable d'agir correctement en fonction de la requête qui lui est envoyée (`http` routing).
- Le serveur doit être capable de *parser* le contenu de la requête reçue.
- Pour chaque message reçu, le serveur doit écrire le contenu du champ `contacts` dans un fichier nommé avec le numéro `imei` correspondant (`{imei}.txt`). Ces fichiers doivent être stockés dans le répertoire `td6/pirate_server/stolen_contacts`.
- Le serveur doit renvoyer une erreur au client si la requête qui lui est envoyée n'est pas prise en charge par celui-ci.
- Le serveur doit renvoyer une erreur au client si l'information contenue dans la requête n'est pas dans un format valide (`json`)

Pour vous aider, vous pouvez consulter :

- la documentation de l'interface `http` de nodejs : [https://nodejs.org/api/http.html](https://nodejs.org/api/http.html)
- les exemples fournis dans la documentation de nodejs : [https://nodejs.org/api/synopsis.html](https://nodejs.org/api/synopsis.html)

### Partie III: Envoyer des sms surtaxés

Dans cette partie, vous devrez modifier le code de votre application malicieuse pour permettre au serveur pirate d'envoyer une commande qui ordonne à votre application d'envoyer des sms à un numéro de téléphone surtaxé à l'insu de l'utilisateur.

Pour que le serveur pirate puisse facilement envoyer des messages à l'application malicieuse, il vous est demandé d'ouvrir une connexion websocket entre le serveur et l'application.

* Ecrivez le code Javascript nécessaire sur le serveur et le client pour ouvrir une connexion websocket.
* Ecrivez le code Javascript nécessaire sur le serveur pour envoyer une commande `send_sms`.
Cette commande est un message dont la structure est la suivante:
```json
{
	"command": "send_sms",
	"payload":
		{
			"phone_number": "string",
			"content": "string"
		}
}
```
* Ecrivez le code Javascript nécessaire sur le client pour traiter correctement la réception de la commande `send_sms`.
* Utilisez le bridge implémenté précédemment pour envoyer un sms à un contact pour envoyer un sms surtaxé à partir du contenu du message de la commande `send_sms`.
