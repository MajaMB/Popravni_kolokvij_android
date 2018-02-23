package com.example.mabaruk.popravni_android;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class KontaktiKojiNePocinju extends ListActivity {

    static String slovo;
    private static String SELECTION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " NOT LIKE '"+slovo+"%'" :
            ContactsContract.Contacts.DISPLAY_NAME + " NOT LIKE '"+slovo+"%'";

    private int MY_PERM=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_kontakti_koji_ne_pocinju);


        // preuzima poruku od intenta
        Intent intent = getIntent();
        slovo = intent.getStringExtra(Main.EXTRA_MESSAGE);

        SELECTION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " NOT LIKE '"+slovo+"%'" :
                ContactsContract.Contacts.DISPLAY_NAME + " NOT LIKE '"+slovo+"%'";


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},MY_PERM);
        }

        CallList();

    }


    public void CallList(){
        //Uri allContacts = Uri.parse("content://contacts/people");
        Uri allContacts = ContactsContract.Contacts.CONTENT_URI;


        Cursor c;
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allContacts, null,null,null,null);

        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader;




            cursorLoader = new CursorLoader(
                    this,
                    allContacts,
                    null,
                    SELECTION,
                    null,
                    null);
            c = cursorLoader.loadInBackground();
        }

        String[] columns = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID};

        int[] views = new int[] {R.id.contactName1, R.id.contactID1};

        SimpleCursorAdapter adapter;

        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            //if(columns[0].toUpperCase().charAt(0)!='a')
            //{
                adapter = new SimpleCursorAdapter(
                        this, R.layout.activity_kontakti_koji_ne_pocinju, c, columns, views);
            //}
        } else {
            //---Honeycomb and later---
                adapter = new SimpleCursorAdapter(
                        this, R.layout.activity_kontakti_koji_ne_pocinju, c, columns, views,
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


        }
        this.setListAdapter(adapter);
    }
}
