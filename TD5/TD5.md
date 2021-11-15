# Initiation à l'analyse dynamique d'application Android
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

Dans ce TP, il vous est demandé de vous familiariser avec un outil d'analyse dynamique nommé **Frida** communément utilisés pour analyser le fonctionnement d'une application Android.

# Frida

Frida est un outil d'instrumentation dynamique pour les développeurs, rétro-ingénérateurs et chercheurs en sécurité. Elle permet, dans le cas d'android, de créer des scripts qui peuvent modifier le comportement de l'application de diverses manières, notamment par l'ajout de log, le monitoring et l'injection de code.

Pour utiliser Frida vous devez tout d'abord installer le frida-server sur votre emulateur.
Pour ce faire, le site de Frida propose un tutoriel [ici](https://frida.re/docs/android/)

## Partie 1: Instrumentation de code à l'aide de Frida

Dans cette première partie, il vous sera demandé d'instrumenter une application réalisée au TP1 : MsgApp.

> Pour cette étape vous bénéficiez d'un avantage majeur : l'accès au code.

Comme vous avez pu le constater au dernier TP, l'instrumentation de code peut être plus ou moins fastidieuse selon l'outil utilisé et selon le but pour lequel l'outil est construit.

1. A l'aide du code de l'application, écrivez les scripts pour frida permettant de logger dans la console python les message envoyés via l'application

2. Utilisez Frida pour modifier le message envoyé par l'application

### Questions

- Quel mechanisme de Frida vous permet d'acceder à l'implementation des fonction en Java
- Expliquez comment, sans changer le fonctionnement de l'application, vous avez pu injecter du code


## Partie 2: Débusquer du code malicieux

Dans cette partie, je vais vous demander d'utiliser toute les compétences et connaissances acquises durant ce cours pour trouver le code malicieux exécuté par l'application fournie. (demandez par mail à l'encadrant l'apk pour votre groupe)

### Questions 

- Expliquez votre démarche d'investigation (quels outils sont utilisé et dans quel but)
- Rendez compte de vos découvertes