package application;

import javafx.scene.shape.Circle;
import javafx.scene.control.Button;

public class CircleButton extends Button 
{
	/** row coordinate in array */
	private int row;
	
	/** column coordinate in array */
	private int column;
	
	public CircleButton(int row, int column)
	{
		this.row = row;
		this.column = column;
		this.setDefaultStyle();
	}
	
	public int getRow()
	{
		return this.row;
	}
	
	public int getColumn()
	{
		return this.column;
	}
	
	public void setDefaultStyle()
	{
		this.getStyleClass().removeAll("circleButtonBlackClick", "circleButtonRedClick");
		this.getStyleClass().add("circleButton");
	}
	
	public void setColor(String color)
	{
		this.getStyleClass().add(String.format("circleButton%sClick", color));
	}
}
