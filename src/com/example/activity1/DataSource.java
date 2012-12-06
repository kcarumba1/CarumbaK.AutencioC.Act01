package com.example.activity1;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DataSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_NUMBER };

	  public DataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  public void editContact(ContactInfo ci)
	  {	  ContentValues values = new ContentValues();
	      values.put(MySQLiteHelper.COLUMN_NAME, ci.getName());
	      values.put(MySQLiteHelper.COLUMN_NUMBER, ci.getNumber());
	  	  this.open();
	  	  database.update(MySQLiteHelper.TABLE, values,MySQLiteHelper.COLUMN_ID+"=?",new String[] {ci.getIdToString()});
	  	  this.close();
		  
	  }
	  public void createContacts(ContactInfo ci)
	  {		ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_NAME, ci.getName());
	    values.put(MySQLiteHelper.COLUMN_NUMBER, ci.getNumber());
	    this.open();
	    long insertId = database.insert(MySQLiteHelper.TABLE, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    
	    cursor.close();
	  }
	  

	  public void deleteContacts(ContactInfo ci) {
	    long id = ci.getId();
	    database.delete(MySQLiteHelper.TABLE, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public ArrayList<ContactInfo> getAllContacts() {
	    ArrayList<ContactInfo> contact_info = new ArrayList<ContactInfo>();
	    this.open();
	    Cursor cursor = database.query(MySQLiteHelper.TABLE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ContactInfo comment = cursorToContact(cursor);
	      contact_info.add(comment);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return contact_info;
	  }

	  private ContactInfo cursorToContact(Cursor cursor) {
	    ContactInfo ci = new ContactInfo();
	    ci.setId(cursor.getLong(0));
	    ci.setName(cursor.getString(1));
	    ci.setNumber(cursor.getString(2));
	    return ci;
	  }
	} 