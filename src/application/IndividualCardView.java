package application;

import javafx.scene.image.Image;

public class IndividualCardView {
	private String number; 
	private String action; 
	public String color;
	public Image icon; 
	public Image backIcon;
	public boolean isDown = false; 
	private String name;

	public IndividualCardView(String number, String action, String color) {
		this.color = color;
		this.number = number;
		this.action = action;
		
		this.name = this.color + this.action + this.number;
		
		setImage();
	}
	public void setImage() {
		String path = "/unocards/" + name + ".png";
		String backPath = "/unocards/backSide.png";
		this.icon = new Image(path, 175, 175, true, true);
		this.backIcon = new Image(backPath, 175, 175, true, true);
	}
	public void setFaceDown(boolean s) {
		this.isDown = s;
	}
	public String getName() {
		return name;
	}
	public Image getIcon() {
		return icon;
	}
	public Image getBackIcon() {
		return backIcon;
	}
	public String getNumber() {
		return number;
	}
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String toString() {
		return this.name;
	}}
