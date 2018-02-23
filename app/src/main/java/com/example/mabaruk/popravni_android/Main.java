package com.example.mabaruk.popravni_android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.mabaruk.popravni_android.message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DBAdapter db = new DBAdapter(this);




        //---add data to table---
        db.open();


        boolean id1 = db.deleteALL();


        long id = db.insertKolac("sacher", "torta", "badem");
        id = db.insertKolac("madjarica", "kolac", "cokolada");
        id = db.insertKolac("macije_oci", "kolac", "cokolava");
        id = db.insertKolac("carotCake", "torta", "mrkva");
        id = db.insertKolac("brownie", "kolac", "cokolada");
        id = db.insertKolac("cookie", "keks", "Badem");



        id = db.insertCijena(17,0);
        id = db.insertCijena(8,1);
        id = db.insertCijena(10,2);
        id = db.insertCijena(7,3);
        id = db.insertCijena(8,4);
        id = db.insertCijena(5,5);

        //-----------------------delete using id
        //db.deleteKolac(0);
        //db.deleteCijena(0);
        db.close();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


//--------------------------------------------------------------------------------------TODO:funkcije za buttone



    public void IspisiKolace(View view)
    {
        Intent intent = new Intent(this, IspisBaze.class);
        startActivity(intent);
    }


    public void IspisiCijene(View view)
    {
        Intent intent = new Intent(this, IspisCijene.class);
        startActivity(intent);
    }


//brisiKolac i brisiCijenu su lastminute pisani i nadam se da rade
    public void brisiCijenu(View view)
    {   EditText tv1 = (EditText)findViewById(R.id.unos2);
        String slovo=tv1.getText().toString();
        int index=Integer.parseInt(slovo);
        DBAdapter db = new DBAdapter(this);

        db.open();

        db.deleteCijena(index);
        db.close();


    }
    public void brisiKolac(View view)
    {   EditText tv1 = (EditText)findViewById(R.id.unos2);
        String slovo=tv1.getText().toString();
        int index=Integer.parseInt(slovo);
        DBAdapter db = new DBAdapter(this);

        db.open();

        db.deleteKolac(index);
        db.close();


    }

    public void IspisiKontakte1(View view)
    {   Intent intent = new Intent(this, KontaktiKojiPocinju.class);

        EditText tv1 = (EditText)findViewById(R.id.unos);
        String slovo=tv1.getText().toString();
        if(slovo.length()==0){
            Toast.makeText(this, "Unesite slovo!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        intent.putExtra(EXTRA_MESSAGE, slovo);
        startActivity(intent);
    }

    public void IspisiKontakte2(View view)
    {
        Intent intent = new Intent(this, KontaktiKojiNePocinju.class);
        EditText tv1 = (EditText)findViewById(R.id.unos);
        String slovo=tv1.getText().toString();
        if(slovo.length()==0){
            Toast.makeText(this, "Unesite slovo!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        intent.putExtra(EXTRA_MESSAGE, slovo);
        startActivity(intent);
    }

    public void PrikaziBadem(View view)
    {
        DBAdapter db = new DBAdapter(this);

        db.open();
        Cursor c= db.getKolacByBADEM();

        String ispis= KolacImena(c);

        EditText tv1 = (EditText) findViewById(R.id.badem);
        tv1.setText(ispis);
        db.close();
    }

    //funkcija za ispis
    public String KolacImena(Cursor c)
    {   String text="";

        while(c.moveToNext()){
            text+=c.getString(1) + "\n";
        }

        return text;
    }
}
