package com.example.mabaruk.popravni_android;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class IspisBaze extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ispis_baze);

        DBAdapter db = new DBAdapter(this);

        db.open();
    //tu trebam dohvatiti sve elemente i ispisati ih
        Cursor c= db.getAllKolaci();

        String ispis="Ispis tablice KOLACI  \n ID, NAZIV, VRSTA, GLAVNI_SASTOJAK:\n\n";
        ispis+= DisplayKolac(c);
        db.close();

        TextView tv1 = (TextView)findViewById(R.id.text_kolaci);
        tv1.setText(ispis);



    }


    //funkcija za ispis
    public String DisplayKolac(Cursor c)
    {   String text="";

        while(c.moveToNext()){
            text +=c.getString(0) + ", " +
                    c.getString(1) +", " +
                     c.getString(2) + ", " +
                     c.getString(3) + "\n";
        }

        return text;
    }

}
