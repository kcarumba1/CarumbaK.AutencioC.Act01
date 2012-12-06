package com.example.activity1;

import android.app.Activity;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditContact extends Activity{
	ContactInfo ci;
	EditText name;
	EditText number;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact_layout);
		Bundle extras= getIntent().getExtras();
		if(extras!=null)
		{	ci=(ContactInfo)extras.getSerializable("ci");
		
			name=(EditText)findViewById(R.id.name_text);
			number=(EditText)findViewById(R.id.number_text);
			
			name.setText(ci.getName());
			number.setText(ci.getNumber());
		}
		
		Button edit_button=(Button)findViewById(R.id.edit_confirm_button);
		Button cancel_button=(Button)findViewById(R.id.edit_cancel_button);
		
		edit_button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataSource ds=new DataSource(getApplicationContext());
				ci.setName(name.getText().toString());
				ci.setNumber(number.getText().toString());
				ds.editContact(ci);
				setResult(RESULT_OK);
				finish();
				
			}
		});
		cancel_button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		
        
 	}
}
