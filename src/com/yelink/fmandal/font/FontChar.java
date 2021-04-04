package com.yelink.fmandal.font;

public class FontChar {
	private int id;
	private double xTC, yTC, xMaxTC, yMaxTC, xOffset, yOffset, sizeX, sizeY, xAdvance;
	
	public FontChar(int id, double xTC, double yTC, double xTexSize, double yTexSize, 
			double xOffset, double yOffset, double sizeX, double sizeY, double xAdvance) {
		this.id = id;
		this.xTC = xTC;
		this.yTC = yTC;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.xMaxTC = xTexSize + xTC;
		this.yMaxTC = yTexSize + yTC;
		this.xAdvance = xAdvance;
	}

	public int getId() {
		return id;
	}

	public double getxTC() {
		return xTC;
	}

	public double getyTC() {
		return yTC;
	}

	public double getxMaxTC() {
		return xMaxTC;
	}

	public double getyMaxTC() {
		return yMaxTC;
	}

	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	public double getxAdvance() {
		return xAdvance;
	}
}
