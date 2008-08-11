package org.jdamico.dataobjects;

public class FormCoordinates {
	private AxisObj axis;
	private String formOwner;
	private int xValue;
	private int yValue;
	
	public int getXValue() {
		return xValue;
	}
	public void setXValue(int value) {
		xValue = value;
	}
	public int getYValue() {
		return yValue;
	}
	public void setYValue(int value) {
		yValue = value;
	}
	
	
	public AxisObj getAxis() {
		return axis;
	}
	public void setAxis(AxisObj axis) {
		this.axis = axis;
	}
	public String getFormOwner() {
		return formOwner;
	}
	public void setFormOwner(String formOwner) {
		this.formOwner = formOwner;
	}
	
	
	
	
}
