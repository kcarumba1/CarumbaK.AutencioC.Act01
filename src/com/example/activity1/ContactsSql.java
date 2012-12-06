package com.example.activity1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsSql extends Activity{
	ListView contact_list,contact_option;
	private static final int DIALOG_ALERT_MAIN_CONTACT_DB=10;
	private static final int DIALOG_ALERT_LOCAL_DB=11;
	private static final int edit_success=111;
	ArrayList<ContactInfo> adap=new ArrayList<ContactInfo>();
	AlertDialog dialog;
	ContactInfo contact_detail;
	Button viewLocalDB;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_layout);
		contact_list=(ListView)findViewById(R.id.contact_list);
		viewLocalDB=(Button)findViewById(R.id.btn_go);
		viewLocalDB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(viewLocalDB.getText().equals("LocalDB"))
				{	displayLocalDBContacts();
					viewLocalDB.setText("Main Contacts DB");
				}
				else
				{	viewLocalDB.setText("LocalDB");
					displayContactsDB();
				}
				
				
				
			}
		});
        
 	}
	public void displayContactsDB()
	{	adap.clear();
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cur.getCount() > 0) 
        {
		    while (cur.moveToNext()) 
		    {	
		        String name = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		        String number = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		        ContactInfo ci=new ContactInfo();
		        ci.setName(name);
		        ci.setNumber(number);
		        adap.add(ci);
		        
//		        	
	 	    }
        }
        ContactAdapter arrayAdapter=new ContactAdapter(this, R.layout.contact_info_layout,adap);
        contact_list.setAdapter(arrayAdapter);
        contact_list.setOnItemClickListener(new OnItemClickListener() {
			

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				contact_detail=(ContactInfo) arg1.getTag();
				showDialog(DIALOG_ALERT_MAIN_CONTACT_DB);
				}
		});
	}
	@Override
	  protected Dialog onCreateDialog(int id) {

	      Builder builder = new AlertDialog.Builder(this);
	      ListView modeList = new ListView(this);
	      String[] stringArray;
	      ArrayAdapter<String> modeAdapter;
		switch (id) {
	    case DIALOG_ALERT_MAIN_CONTACT_DB:
	      // Create out AlterDialog
	      builder.setMessage(contact_detail.getName()+": "+contact_detail.getNumber());
	      builder.setCancelable(true);
	      
	      
	      stringArray = new String[] { "Add Contact to local db"};
	      modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
	      modeList.setAdapter(modeAdapter);
	      modeList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String value=arg0.getAdapter().getItem(arg2).toString();
				Toast.makeText(getApplicationContext(), "Added contact to local db", Toast.LENGTH_SHORT).show();
				DataSource ds=new DataSource(getApplicationContext());
				ds.createContacts(contact_detail);
				dialog.dismiss();
				displayLocalDBContacts();
				
			}
		});break;
	    case DIALOG_ALERT_LOCAL_DB:
		      // Create out AlterDialog
	    	builder.setMessage(contact_detail.getName()+": "+contact_detail.getNumber());
		      builder.setCancelable(true);
		      
		      
		      stringArray = new String[] { "EDIT CONTACT","DELETE CONTACT"};
		      modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
		      modeList.setAdapter(modeAdapter);
		      modeList.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					DataSource ds=new DataSource(getApplicationContext());
					String value=arg0.getAdapter().getItem(arg2).toString();
					if(value=="DELETE CONTACT")
					{	ds.open();
						ds.deleteContacts(contact_detail);
						ds.close();
						Toast.makeText(getApplicationContext(), "Deleted contact to local db", Toast.LENGTH_SHORT).show();
					
					}
					else if(value=="EDIT CONTACT")
					{	edit_contact();
						
					}
					dialog.dismiss();
					displayLocalDBContacts();
					
				}
			});break;
		      
	    }
		  builder.setView(modeList);
	      
	      dialog = builder.create();
	      dialog.show();
	    return super.onCreateDialog(id);
	  }
		public void edit_contact()
		{
			Intent i= new Intent(this,EditContact.class);
			i.putExtra("ci",contact_detail);
			startActivityForResult(i,edit_success);
		}
		public void displayLocalDBContacts()
		{
			
			contact_list=(ListView)findViewById(R.id.contact_list);
			ContentResolver cr = getContentResolver();
	        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	                null, null, null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
	        
	        DataSource ds=new DataSource(getApplicationContext());
	        adap.clear();
	        adap=ds.getAllContacts();
	        
	        ContactAdapter arrayAdapter=new ContactAdapter(this, R.layout.contact_info_layout,adap);
	        contact_list.setAdapter(arrayAdapter);
	        contact_list.setOnItemClickListener(new OnItemClickListener() {
				

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					contact_detail=(ContactInfo) arg1.getTag();
					showDialog(DIALOG_ALERT_LOCAL_DB);
					}
			});
		
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (resultCode == RESULT_OK && requestCode == edit_success) {
			  	Toast.makeText(getApplicationContext(), "EDITED CONTACT FROM LOCAL DB", Toast.LENGTH_SHORT).show();
			  	viewLocalDB.setText("Main Contacts DB");
			  	displayLocalDBContacts();
		    }
		  }

	  
	
	
}