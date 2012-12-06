package com.example.activity1;

import java.io.Serializable;

public class ContactInfo implements Serializable{
		long id;
		String contact_name;
		String contact_number;
		
		public long getId()
		{	return id;
		}
		public String getIdToString()
		{	return String.valueOf(id);
			
		}
		public void setId(long id)
		{	this.id=id;
			
		}
		public String getName()
		{	return this.contact_name;
		
		}
		public String getNumber()
		{	return this.contact_number;
			
		}
		public void setName(String name)
		{	this.contact_name=name;
		
		}
		public void setNumber(String number)
		{	this.contact_number=number;
		}
}
