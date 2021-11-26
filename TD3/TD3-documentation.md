# Documentation pour TD3

## Partie I: Traquer la position GPS
### Création d'une application Google Maps
Dans cette partie, il vous est demandé de créer une application de Type Maps. Pour ce faire, suivez pas-à-pas la documentation disponible pour Android [https://developers.google.com/maps/documentation/android-sdk/start](https://developers.google.com/maps/documentation/android-sdk/start)

### Obtention des coordonnées GPS depuis l'appareil
- Pour ce faire, vous devez d'abord avoir les permission d'accès à la localisation. Ces dernières doivent être demandées aussi pendant l'exécution à l'aide de la méthode requestAllPermissions() fournie dans le TD précédent (TD1 et TD2).
- Les permissions nécessaires sont `android.permission.ACCESS_COARSE_LOCATION` et `android.permission.ACCESS_FINE_LOCATION`.

- Pour obtenir les coordonnées actuelles depuis le téléphone Android, la classe `FusedLocationProviderClient` est utilisée. Vous pouvez suivre la documentation officielle depuis le lien suivant : 
[https://developer.android.com/training/location/retrieve-current.html#GetLocation](https://developer.android.com/training/location/retrieve-current.html#GetLocation)

Dans la classe onCreate
```Java
FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(this);
locationProvider.getLastLocation()..addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                });
```
Après cela, il faudrait modifier la méthode `onLocationChanged(Location location)` Pour y modifier le changement de caméra vers la nouvelle position GPS.

```Java
 public void onLocationChanged(Location location)
    {
        // Retirer le contenu de la Map
        mMap.clear();
        // Création d'un nouveau Marqueur
        MarkerOptions mp = new MarkerOptions();
        // Définition de la position actuelle dans le marqueur
        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        // Spécification du titre du marqueur
        mp.title("my position");
        // Ajout du marqueur dans la Map
        mMap.addMarker(mp);
        // Définition du changement de la caméra vers la nouvelle position
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
    }                

```

Bonus : apportez les modifications nécessaires au code donné au dessus pour que la Map se mette à jour quand vous vous déplacez. La documentation pour cette partie est disponible la documentation officielle d'Android sur le lien suivant :
(https://developer.android.com/training/location/request-updates)[https://developer.android.com/training/location/request-updates]

### Partie II : Ecrire une application de News

Dans cette partie, vous devez concevoir une application Android qui affiche une liste des dernières photos postées par la NASA sur leur site.\
La Nasa offre plusieurs `api endpoints` qui permettent d'accéder à différents contenus.
Pour cela, vous pourrez utiliser l'API `api.nasa.gov` dont la documentation est disponible ici: [https://api.nasa.gov/index.html#getting-started](https://api.nasa.gov/index.html#getting-started).
Inscrivez vous sur le site et utilisez la clé API qui vous est fournie.

Pour le TP, vous utiliserez l'API *APOD* (Astronomy Picture Of the Day), qui offre une photo ainsi que sa description tous les jours.
Pour concevoir votre application de news, vous devrez avoir un fil d'actualité qui contient les photos de l'API sur plusieurs jours.

Vous développerez cette application uniquement en **Java**.

* Implémentez une classe qui permet de récupérer les photos postées par la NASA. Vous utiliserez une requête GET pour récupérer les informations nécessaires
* Ecrivez le `Layout` ainsi que le code Java nécessaire pour afficher la liste de photos sous la forme suivante:

#### Récupération des données depuis le site de la Nasa
La documentation que propose le site de la Nasa vous permets d'effectuer des requêtes sur leur site. Complétez la méthode getRequest qu'est proposée ci-dessous pour envoyer la requête au site de la Nasa

```Java
    
    public void getRequest(Context mContext, String url) throws IOException, JSONException {
        final JSONObject[] jsnobject = {new JSONObject()};
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);
                        retours.add(response);
                        try {
                            jsnobject[0] = new JSONObject(response);
                            String url = jsnobject[0].get("url"):
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    }
```
#### Utilisation de Glide pour afficher une image depuis son URL
Pour se faire, la page GIT de `Glide` explique la démarche. [https://github.com/bumptech/glide](https://github.com/bumptech/glide)

## Partie III : Collecter les données GPS de l'utilisateur
### Envoi des coordonnées depuis l'application GPSTracker
Pour cela, il suffit de créer un intent. Ce dernier contiendra deux champs `longitude` et `latitude`.

```Java
// Création d'un intent nommé "Localisation"
Intent intent = new Intent("Localisation");
// Ajout d'un champ d'information qui s'appelle longitude contenant la longitude de l'appareil
intent.putExtra("longitude", ""+longitude);
// Ajout d'un champ d'information qui s'appelle latitude contenant la latitude de l'appareil
intent.putExtra("latitude", ""+latitude);
// Ajout d'un champ d'information pour y inclure l'heure d'interception des coordonnées
intent.putExtra("quand", ""+quand);
// Envoi de l'intent "Localisation" en Broadcast à toutes les applications du téléphone
getApplicationContext().sendBroadcast(intent);
```

### Réception des coordonnées sur l'appication News
Une fois l'intent qui contient les coordonnées envoyé depuis l'application `GPSTracker`, il faudrait l'intercépter sur l'application News. Pour se faire, plusieurs étapes sont à respecter :
1. Créer une classe MyReceiver qui étend la classe BroadCastReceiver, celle-ci va servir de recepteur de broadcasts. Elle doit être dans le même répertoire que la classe `MainActivity.java`
2. déclaration du receveur de broadcasts dans le fichier AndroidManifest
3. Instanciation de la classe MyReceiver et mise en place d'un filtre à intents
4. Inteception des coordonnées

#### Création de la classe MyReceiver qu'est une sous-classe de la classe BroadCastReceiver
Cette classe est de la forme suivante :
```Java
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Intent Reçu");
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        // Pour obtenir la longitude depuis l'intent
        String longitude = intent.getStringExtra("longitude");
        // De même pour la latitude et la date/heure d'envoi (le champ "quand")
    }
```

#### Déclaration de MyReceiver dans le ficher AndroidManifest
La déclaration du receveur doit contenir un `IntentFilter`, celui-ci devra contenir le nom de l'action que l'on a attributé à notre intent pendant sa création, dans notre cas `Localisation`
```xml
<application>
<activity>
...
</activity>
<receiver android:name=".MyReceiver" android:exported="true" android:enabled="true">
            <intent-filter>
                <action android:name="Localisation" />
            </intent-filter>
        </receiver>
</application>
```

#### Création d'une instance de MyReceiver 
Dans la méthode onCreate() de la classe MainActivity.java, vous devez créer une instance de `MyReceiver` et y inclure un `IntentFilter` comme suit : 
```Java
MyReceiver receiver;
IntentFilter filter ;

protected void onCreate(Bundle savedInstanceState) {
    ...
receiver = new MyReceiver();
filter = new IntentFilter();
// Ajouter le nom de l'action de l'intent à intercepter
filter.addAction("Localisation");
this.registerReceiver(receiver,filter);
        
```
#### Création de la classe Coordonnées
Cette classe servira à y mettre les contacts pour les convertir en String sous format JSON avant de les envoyer au serveur. Celle-ci doit contenir la longitude, latitude, et les informations sur le temps d'envoi.

#### Envoi des coordonnées depuis l'application News au serveur Pirate
Après avoir intercepté l'intent contenant les coordonnées, et la conversion des coordonnées en String Json, il faut créer une `postStringRequest` et l'envoyer au serveur. Pour cela, réutilisez le même serveur du TP2 en y ajoutant une route (URLServeur/localisation). Réutilisez la même méthode `postStringRequest` du TP2 pour envoyer la requête.