package edu.southalabama.csc527.smallworld.model;
import java.io.*;

import java.util.List;
import java.util.ArrayList;

// This is a stub.
public class Item {
	private String f_name;
	private String f_article;
	private String f_location;
	private int f_takePoints;
	private int f_dropPoints;
	private List<Subplace> f_subplaces = new ArrayList<Subplace>();
	
	Item(){
		f_name = "master sword";
		f_article = "THE";
		f_location = "Sacred Grove";
		f_takePoints = 1000000;
		f_dropPoints = -9999;
		f_subplaces.add(new Subplace());
	}
	
	public void addSubplace(Subplace subplace) {
		f_subplaces.add(subplace);
	}
	public List<Subplace> getSubplaces(){
		return f_subplaces;
	}
	
	public int getTakePoints() {
		return f_takePoints;
	}
	public int getDropPoints() {
		return f_dropPoints;
	}
	public String getName() {
		return f_name;
	}
	public String getArticle() {
		return f_article;
	}
	public String getLocation() {
		return f_location;
	}
}