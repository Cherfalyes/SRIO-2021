Partie I: Simple application avec Webview
1/ Création d’une simple application android avec une Activity
File → New → New Project → Basic Activity → Donner un nom dans Name → Finish

2/ Création d’une instance de webview et 3/ Affichage d’un site avec webview
- Ajouter webview dans le layout de l’activité

<WebView
    android:id="@+id/webview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
- Ajouter les commandes pour créer une instance de webview

myWebView = (WebView) findViewById(R.id.webview);
myWebView.loadUrl("http://www.youtube.fr");
- Ajouter la permission d’accès à internet dans le document AndroidManifest

<uses-permission android:name="android.permission.INTERNET" />

Pour utiliser WebView sans avoir beaucoup de problèmes, surtout si la version d’Android est ancienne, rajouter dans le fichier Build.gradle la ligne suivante dans la balise dependencies :

implementation 'androidx.webkit:webkit:1.2.0'

Partie II: Exécuter du code local au sein d'une instance Webview
1-2/ Rajouter les deux documents index.html et main.js

Création d’une webview dans la classe MainActivity
// Création d'une webview
private WebView myWebView = (WebView) findViewById(R.id.webview); // Création d’une Webview
myWebView.loadData( "chemin vers le fichier HTML", "text/html", "base64"); 
Activer le code JavaScript dans le contexte Webview
// Création d'une variable de configuration websettings
WebSettings webSettings = myWebView.getSettings(); // création d’une configuration Websettings
this.setMyWebviewSettings(webSettings);
myWebSettings.setJavaScriptEnabled(true); // activer l’exécution de Javascript dans la Webview
Donner les autorisations nécessaires dans le fichier AndroidManifest, par exemple :
```XML
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.READ_CONTACTS"/>
```

Donner les autorisations nécessaires pendant l’exécution (on runtime), pour cela, copier la méthode onPermissionRequest dans la classe MainActivity.java. 

// Demande de permissions
public void onPermissionRequest(final PermissionRequest request) {
    if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        request.grant(request.getResources());
    }
}
La demande de permissions se fait dans la méthode onCreate() avant la création de la webview. Par exemple, demander l’autorisation d’accès à internet ainsi que celle d’accès à l’état du téléphone, comme suit :

String[] permissions = {"android.permission.READ_PHONE_STATE" , "android.permission.INTERNET"};
this.requestAllPermissions(permissions);


Mise en place du débuggeur internet dans la webview

myWebView.setWebChromeClient(new WebChromeClient() {
    // Mise en place d'un débuggeur web -- très pratique pour trouver les problèmes d'exécution web
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d("MyApplication", consoleMessage.message() + " -- From line " +
                consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
        return true;
    }