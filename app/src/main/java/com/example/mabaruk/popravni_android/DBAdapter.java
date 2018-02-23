package com.example.mabaruk.popravni_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mabaruk on 2/23/18.
 */

public class DBAdapter {

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE1 = "KOLACI";
    static final String DATABASE_TABLE2 = "CIJENE";
    static final int DATABASE_VERSION = 2;

    static final String KEY_ROWID = "_id";
    static final String KEY_NAZIV = "naziv";
    static final String KEY_VRSTA = "vrsta";
    static final String KEY_GLAVNI_SASTOJAK = "glavni_sastojak";
    static final String KEY_SASTOJAKID = "id_sastojka";
    static final String KEY_CIJENA = "cijena";



    static final String TAG = "DBAdapter";



    static final String DATABASE_CREATE =
            "create table KOLACI (_id integer primary key autoincrement, "
                    + "naziv text not null, vrsta text not null, glavni_sastojak text not null);";


    static final String DATABASE_CREATE2 =
            "create table CIJENE (id_sastojka integer primary key autoincrement, "
                    + "cijena integer not null, _id integer not null);";




    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    //---------------------------------------------------------------------------------base adapter------
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE2);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS KOLACI");
            db.execSQL("DROP TABLE IF EXISTS CIJENE");
            onCreate(db);
        }
    }
    //-------------------------------------------------------------------------------------open and close db-----------
    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //--------------------------------------------------------------------------------------insert----------
    //---insert a kolac into the database->KOLACI---
    public long insertKolac(String naziv, String vrsta, String glavni_S)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAZIV, naziv);
        initialValues.put(KEY_VRSTA, vrsta);
        initialValues.put(KEY_GLAVNI_SASTOJAK, glavni_S);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }

    //---insert a cijena into the database->Cijene---
    public long insertCijena(int cijena, int id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CIJENA, cijena);
        initialValues.put(KEY_ROWID, id);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }


    //-------------------------------------------------------------------------------------delete


    public boolean deleteALL(){
        db.execSQL("delete from " + DATABASE_TABLE1);
        db.execSQL("delete from " + DATABASE_TABLE2);
        return true;
    }


    //---deletes a particular kolac---
    public boolean deleteKolac(long rowId)
    {
        return db.delete(DATABASE_TABLE1, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---deletes a particular cijena---
    public boolean deleteCijena(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_SASTOJAKID + "=" + rowId, null) > 0;
    }


    //---------------------------------------------------------------------vrati sve kolace/cijene

    //---retrieves all the Kolaci---
    public Cursor getAllKolaci()
    {
        return db.query(DATABASE_TABLE1, new String[] {KEY_ROWID, KEY_NAZIV,
                KEY_VRSTA, KEY_GLAVNI_SASTOJAK}, null, null, null, null, null);
    }

    //---retrieves all the Cijene---
    public Cursor getAllCijene()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_SASTOJAKID, KEY_CIJENA,
                KEY_ROWID}, null, null, null, null, null);
    }


    //---------------------------------------------------------------------vraca kolac/cijena s zadanim ID ili sastojkom-------------
    //---retrieves a particular kolac---
    public Cursor getKolac(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {KEY_ROWID,
                                KEY_NAZIV, KEY_VRSTA,KEY_GLAVNI_SASTOJAK}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---retrieves a particular cijena---
    public Cursor getCijena(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_SASTOJAKID,
                                KEY_CIJENA, KEY_ROWID}, KEY_SASTOJAKID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getKolacByBADEM() throws SQLException
    {
        Cursor mCursor=null;
        mCursor = db.query(true, DATABASE_TABLE1,
                new String[] {KEY_ROWID, KEY_NAZIV, KEY_VRSTA,KEY_GLAVNI_SASTOJAK},
                "LOWER("+ KEY_GLAVNI_SASTOJAK + ") = \"badem\"" ,
                null,null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //-------------------------------------------------------------------update kolac/cijena preva id-u

    //---updates a kolac---
    public boolean updateKolac(long rowId, String naziv, String vrsta, String glavni_s)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAZIV, naziv);
        args.put(KEY_VRSTA, vrsta);
        args.put(KEY_GLAVNI_SASTOJAK, glavni_s);
        return db.update(DATABASE_TABLE1, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---updates a cijena---
    public boolean updateCijena(long rowId, int cijena, int id)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_CIJENA, cijena);
        args.put(KEY_ROWID, id);
        return db.update(DATABASE_TABLE2, args, KEY_SASTOJAKID + "=" + rowId, null) > 0;
    }




















}
