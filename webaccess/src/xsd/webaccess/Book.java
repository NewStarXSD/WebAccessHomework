package xsd.webaccess;

import java.io.Serializable;

public class Book implements Serializable {
	public String name;
	public String date;
	
	public Book() {
	}
	
	public Book(String name, String date) {
		this.name = name;
		this.date = date;
	}
}