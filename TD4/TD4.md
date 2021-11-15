# Initiation à l'analyse statique d'application Android
Le but de ce TP est de vous sensibiliser et de vous familiariser avec certaines notions de sécurité informatique.
Les pratiques que vous allez mettre en oeuvre ne sont autorisées que sur du matériel vous appartenant ou pour lequel
vous avez les autorisations écrites nécessaires émanant des autorités idoines.

**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinéas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende.

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

# Context

Pour des raisons de sécurité ou de maintenance, il est parfois nécessaire d'analyser le fonctionnement d'un programme ou d'une application.
Cependant, il est probable que vous n'ayez pas accès au code source de cette application, mais seulement à l'APK final.
De nombreux outils et techniques ont été développées pour faciliter l'analyse d'applications dans ce contexte.

# Objectifs

Dans ce TP, il vous est demandé de vous familiariser avec deux outils d'analyse statiques communément utilisés pour analyser manuellement et automatiquement le contenu d'une application Android.

# Partie 1: Analyse manuelle d'une application

Dans cette première partie, votre objectif est de vous apprendre à naviguer dans l'architecture d'une application Android sans son code source.

## Apktool

### Utiliser Apktool
Pour installer Apktool, vous pouvez suivre les instructions à la page suivante : [https://ibotpeaches.github.io/Apktool/install/](https://ibotpeaches.github.io/Apktool/install/)

Apktool est un outil qui s'utilise en ligne de commande (Command-line interface). Vous utiliserez donc le terminal pour interagir avec l'outil.

1. Tout au long de ce `td4`, vous utiliserez l'APK d'une application de contacts qui vous servira d'exemple. Téléchargez la version `6.13.1` de l'application [ici](https://f-droid.org/en/packages/com.simplemobiletools.contacts.pro/).
2. Vérifiez que le fichier que vous avez téléchargé est bien celui souhaité en certifiant que le checksum (sha256) est identique à celui-ci : `bf97db9c40a26feff57e9cc0d4b7a0f31e3a50ae3dcf623955dc2265a8ba884a`.
Pour effectuer cette opération, vous pourrez utiliser l'outil `openssl` en ligne de commande.
    - Que signifie `sha256` ? 
    - Expliquez succinctement le principe d'une fonction de hashage.
3. A l'aide de la documentation d'`apktool`, effectuez un décodage complet de l'APK que vous avez téléchargé.
    - Quels sont les différents types de fichiers que cet APK contient ?
    - Quelles sont les permissions demandées par cette application ?
    - Où se trouve le bytecode de l'application ?
    - Qu'est ce que `smali`, et pourquoi est-il utilisé dans `apktool` ?
4. Parcourez la représentation `smali` du bytecode et localisez le ou les endroits où l'application récupère la liste des contacts du téléphone.
Pour cela, vous pourrez utiliser la commande `grep`. La [documentation officielle](https://developer.android.com/reference/android/provider/ContactsContract.Contacts) d'Android vous permettra de connaitre le nom de la méthode à trouver.
5. A l'aide de la documentation d'`apktool`, re-construisez (`build`) l'application complète afin d'obtenir un nouvel APK.
    - Déterminer le `sha256` du nouvel APK créé. Est-il différent du `sha256` de l'APK original ? Pourquoi ?

## Questions

Les questions pour cet première partie sont celles présentes ci-dessus.

# Partie 2: Analyser une application programmatiquement

Dans cette seconde partie de TD, votre premier objectif est de réussir a faire fonctionner Soot en utilisant leur tutoriel.
La seconde partie de ce TP aura pour but de vous apprendre à extraire automatiquement des informations et instrumenter le code de l'APK d'une application Android.
Votre dernier objectif, est de modifier le bytecode de l'application pour envoyer la liste des contacts (et numéros) à votre serveur pirate (voir TP2-3).

## Soot

Soot est un framework qui permet d'analyser et d'instrumentaliser le bytecode d'un programme Java.
Comme une application Android est elle aussi écrite en Java, il est possible d'utiliser Soot comme framework d'analyse statique pour Android.

## Soot Hello World

1. Clonez le dépôt du [tutoriel de Soot](https://github.com/noidsirius/SootTutorial) et lisez le README.md
2. Répliquez la partie logger du tutoriel du chapitre 3: Android Instrumentation

## Parcourir et instrumenter le bytecode de l'application

1. Modifiez le code du tutoriel afin qu'elle analyse l'application de contact utilisé précédement.
2. Listez toutes les classes et méthodes contenues dans l'application (sans exécuter l'application).
3. Ecrivez le code Java nécessaire pour localiser la méthode qui permet de récupérer la liste des contacts de l'appareil
4. Pour s'assurer que c'est la bonne méthode, instrumentez le code afin de logger la liste des contacts

## Injecter une faille dans le bytecode de l'application

Dans cette partie, il vous est demandé de modifier le comportement de l'application pour que celle-ci déclenche un un envoi de la liste des contacts ainsi que leur numéros.

Le but de cette partie, est de recréer le comportement de l'application du TP2 à partir d'une application existante. Vous veillerez à ce que l'application résultante de l'injection de code soit compatible avec votre serveur pirate.

Vous allez tout d'abord injecter un code java benin afin de comprendre comment injecter du code java statique :
1. Changez dans le code de la partie précédente l'injection de la fonction de log par un appel a une methode statique en java.
2. Recompilez l'application
3. Signez l'application
4. Installez et exécutez l'application sur un émulateur et vérifiez que la fonction s'exécute correctement

Maintenant que vous savez injecter du code, transformez le code benin précédent pour qu'il effectue le vol des données de contacts :
1. Changez dans le code benin en une requête POST vers votre serveur pirate.
2. Recompilez l'application
3. Signez l'application
4. Installez et exécutez l'application sur un émulateur et vérifiez que le serveur reçois bien les données volée.

## Questions 
1. Expliquez le concept de représentation intermédiaire de bytecode, faites un schéma explicatif.
2. Definissez avec vos propre mots ce qu'est le repackaging d'application (Application repackaging).
3. Quels sont les moyens de defenses à la disposition des développeurs (Pour se protéger ou au moins gener l'attaquant) ?
    - Donnez quelques techniques de defences.
    - Donnez quelques outils utilisant ces techniques.
4. Par quelles méthodes un utilisateur peut-il éviter ce genre d'application ?