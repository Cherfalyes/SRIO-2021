## Documentation pour TD2

- Avant de commencer, il est important de créer un jeu de données en ajoutant quelques contacts avec leurs numéros de téléphones.
- Pour récupérer les contacts de l'utilisateur, le code suivant vous est fourni, ce dernier retourne le nom et le numéro de téléphone du premier contact.

// Définir un curseur qui va parcourir les contacts, le carnet d'adresses d'android se présente comme un tableau ayant multiples colonnes
Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

String name, number;

// Tester si le curseur peut avancer
if (cursor.moveToNext())
{
    // retourne le nom du contact dans lequel se situe le curseur
    @SuppressLint("Range") name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

    // retourne le numéro de téléphone du contact dans lequel se situe le curseur
    @SuppressLint("Range") number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
}

// Fermer le curseur, cette commande est obligatoire
cursor.close();

