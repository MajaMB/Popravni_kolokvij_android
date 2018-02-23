package com.example.mabaruk.popravni_android;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IspisCijene extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ispis_cijene);


        DBAdapter db = new DBAdapter(this);

        db.open();
        //tu trebam dohvatiti sve elemente i ispisati ih
        Cursor c= db.getAllCijene();

        String ispis="Ispis tablice CIJENE \n ID_SASTOJKA, CIJENA, ID: \n\n";
        ispis+= DisplayCijena(c);
        db.close();

        TextView tv1 = (TextView)findViewById(R.id.text_cijene);
        tv1.setText(ispis);
    }

    //funkcija za ispis
    public String DisplayCijena(Cursor c)
    {   String text="";

        while(c.moveToNext()){
            text+=c.getString(0) + ", " +
                    c.getString(1) + ", " +
                    c.getString(2) + "\n";
        }

        return text;
    }



}
