package edu.southalabama.csc527.smallworld.model;

public class Item {
	private String f_name;
	private String f_article;
	private String f_location;
	private Integer f_takePoints;
	private Integer f_dropPoints;
	
	Item(){
		f_name = "master sword";
		f_article = "THE";
		f_location = "Sacred Grove";
		f_takePoints = 1000000;
		f_dropPoints = -9999;
	}
	
	Item(String name, String article, String location, int takePoints, int dropPoints){
		f_name = name;
		f_article = article;
		f_location = location;
		f_takePoints = takePoints;
		f_dropPoints = dropPoints;
	}
	public Integer getTakePoints() {
		return f_takePoints;
	}
	public void setTakePoints(int points) {
		f_takePoints = points;
	}
	public Integer getDropPoints() {
		return f_dropPoints;
	}
	public void setDropPoints(int points) {
		f_dropPoints = points;
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
	public void setLocation(String location) {
		f_location = location;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Item && ((Item) obj).getName().equals(f_name);
	}
}