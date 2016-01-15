package iac.sleepy.loginsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 21524677 on 2015/11/18.
 */
public class DBHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "profile.db";
    public static final String TABLE_NAME = "profiles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "productname";
    public static final String COLUMN_PASSWORD = "password";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //CREATE TABLE products(_id INTEGER PRIMARY KEY AUTOINCREMENT , productname TEXT );
    //Create db table command just remember.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addProfile (String username,String passwd)
    {
        if(Checkdb(username) < 0)
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_USERNAME, username);
            cv.put(COLUMN_PASSWORD, passwd);
            db.insert(TABLE_NAME, null, cv);
            return true;
        }
        else
            return false;
    }
    public int Checkdb(String username) {
        Cursor c = null;
        int count = -1;
        SQLiteDatabase db = getReadableDatabase();
        try {
            String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?";
            c = db.rawQuery(query, new String[]{username});
            if (c.moveToNext()) {
                count = c.getInt(0);
            }
            return count;
        }
        finally
        {
            if (c != null)
                c.close();
            if (db != null)
                db.close();
        }
    }
    public boolean CheckLogin(String username,String password)
    {
        if(Checkdb(username) < 0)
            return false;
        Cursor c = null;
        String dbusername;
        String dbpasswd;
        SQLiteDatabase db = getReadableDatabase();
        try
        {

            //SELECT * FROM table_name WHERE column_username = ?
            //Because here we need to get username & password hence use '*'
            //if just need to get username can limit usage by use 'column_username'
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?";

            //query target as input "username"
            c = db.rawQuery(query, new String[]{username});

            //must set
            c.moveToFirst();

            //Get the storage dbusername&dbpasswd
            dbusername = c.getString(c.getColumnIndex(COLUMN_USERNAME));
            dbpasswd = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            if(username.compareTo(dbusername) == 0 && password.compareTo(dbpasswd) == 0)
                return true;
            else
                return false;
        }
        finally
        {
            if (c != null)
                c.close();
            if (db != null)
                db.close();
        }
    }
    public String GetdbData(int position,int key)
    {
        int temp = position;
        SQLiteDatabase db = getReadableDatabase();
        String ReturnData;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(position>1)
        {
            c.moveToNext();
            position--;
        }
        if(c.isAfterLast())
            return null;
        if(c.getString(c.getColumnIndex(COLUMN_USERNAME)) != null && key == 0 )
        {
            ReturnData = c.getString(c.getColumnIndex(COLUMN_USERNAME));
            return ReturnData;
        }
        else if(c.getString(c.getColumnIndex(COLUMN_PASSWORD)) != null && key == 1 )
        {
            ReturnData = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            return ReturnData;
        }
        else
            return null;

    }

    //DELETE FROM TABLE_NAME WHERE COLUMN_USERNAME ='\'username'\'
    public void delProfile (String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME
        + "=\"" + username + "\";");
    }

    //UPDATE TABLE_NAME SET COLUMN_PASSWORD=\password\ WHERE COLUMN_USERNAME='\'username'\'
    public void modProfile (String username, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+ TABLE_NAME + " SET " + COLUMN_PASSWORD
        + "=\"" + password + "\" WHERE " + COLUMN_USERNAME + "=\"" + username + "\";");
    }
}













