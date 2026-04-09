package edu.southalabama.csc527.smallworld.model;
import java.io.*;

import java.util.List;
import java.util.ArrayList;

// This is a stub.
public class Item {
	private List<Subplace> f_subplaces = new ArrayList<Subplace>();
	public void addSubplace(Subplace subplace) {
		f_subplaces.add(subplace);
	}
	public List<Subplace> getSubplaces(){
		return f_subplaces;
	}
	
	public String getTakePoints() {
		return "-10";
	}
	public String getDropPoints() {
		return "99";
	}
	public String getName() {
		return "placeholder name";
	}
	public String getArticle() {
		return "thy";
	}
	public String getLocation() {
		return "this is where i am";
	}
}