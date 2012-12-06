package com.example.activity1;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class ContactAdapter extends ArrayAdapter<ContactInfo>{
	Context context;
	ArrayList<ContactInfo> contact;
	public ContactAdapter(Context context, int textViewResourceId,
			ArrayList<ContactInfo> contacts) {
		super(context, textViewResourceId, contacts);
		this.context=context;
		this.contact=contacts;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.contact_info_layout, parent, false);
			    TextView name = (TextView) rowView.findViewById(R.id.contact_name);
			    TextView number = (TextView) rowView.findViewById(R.id.contact_number);
			    name.setText(contact.get(position).getName().toString());
			    number.setText(contact.get(position).getNumber().toString());
			    rowView.setTag(contact.get(position));
			    // Change the icon for Windows and iPhone
			    

			    return rowView;

	}
}
