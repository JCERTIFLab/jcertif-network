package com.jcertif.reseaujcertif.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jcertif.reseaujcertif.persistances.Msg;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	public static int VERSION_BDD = 1;
	public static String NOM_BDD = "messages.db";
 
	public String TABLE_MESSAGES = "table_messages";
	public String COL_ID = "ID";
	public String COL_USER = "user";
	public String COL_SENDER_ID = "sender_id";
	public String COL_RESIVER_ID = "resiver_id";
	public String COL_DATE = "date";
	public String COL_MSG = "msg";
 
	public DatabaseHandler(Context context) {
		super(context, NOM_BDD, null, VERSION_BDD);
	}
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
        		+ COL_USER + " TEXT,"
        		+ COL_SENDER_ID + " TEXT,"
        		+ COL_RESIVER_ID + " TEXT,"
        		+ COL_DATE + " TEXT,"
                + COL_MSG + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }
    
    public void addMessage(Msg msg) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(COL_USER, msg.getUser());
        values.put(COL_SENDER_ID, msg.getId_Sender()); 
        values.put(COL_RESIVER_ID, msg.getId_Resiver()); 
        values.put(COL_DATE, msg.getDate()); 
        values.put(COL_MSG, msg.getMsg()); 
 
        db.insert(TABLE_MESSAGES, null, values);
        db.close(); 
    }
 
    public List<Msg> getAllMessages(int user_id) {
        List<Msg> listMessages = new ArrayList<Msg>();
   
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES + " WHERE "+COL_USER+" = \""+user_id+"\"";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	Msg msg = new Msg();
            	msg.setUser(cursor.getString(1));
            	msg.setId_Sender(cursor.getString(2));
            	msg.setId_Resiver(cursor.getString(3));
            	msg.setDate(cursor.getString(4));
            	msg.setMsg(cursor.getString(5));
           
            	listMessages.add(msg);
            } while (cursor.moveToNext());
        }
        db.close(); 
        return listMessages;
    }

}