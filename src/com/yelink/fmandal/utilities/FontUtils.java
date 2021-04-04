package com.yelink.fmandal.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yelink.fmandal.font.FontChar;

public class FontUtils {
	/*
	 * Creates a Map of the characters within the Font stored with parameters necessary to render
	 */
	public static final FontUtils STANDARD = new FontUtils("res/bangers.fnt");
	
	private BufferedReader br;
	
	private int[] padding;
	private double paddingWidth, paddingHeight, lineHeight, scaleW, verPerPixelSize, horPerPixelSize, spaceWidth;
	private double aspectRatio = 1280/960;
	
	private Map<String, String> values = new HashMap<String, String>();
	private Map<Integer, FontChar> characters = new HashMap<Integer, FontChar>();
	
	public FontUtils(String path) {
		try {
			File file = new File(path);
			br = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load " + path);
		}
		
		processHeader();
		processCharacters();
	}
	
	public double getSpaceWidth() {
		return this.spaceWidth;
	}
	
	public Map<Integer, FontChar> getCharacters() {
		return this.characters;
	}
	
	public int getValue(String variable) {
		return Integer.parseInt(values.get(variable));
	}
	
	public boolean readLine() {
		values.clear();
		
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load line");
		}
		
		if (line == null) {
			return false;
		}
		
		for(String st : line.split(" ")) {
			String[] pairs = st.split("=");
			if(pairs.length == 2) {
				values.put(pairs[0], pairs[1]);
			}
		}
		
		return true;
	}
	
	public void processHeader() {
		for (int i = 0; i < 4; i++) {
			readLine();
			
			if(i == 0) {
				String[] numbers = values.get("padding").split(",");
				padding = new int[numbers.length];
				for(int j = 0; j < padding.length; j++) {
					padding[j] = Integer.parseInt(numbers[j]);
				}
				
				paddingWidth = padding[1] + padding[3];
				paddingHeight = padding[0] + padding[2];
			} else if (i == 1) {
				lineHeight = Double.parseDouble(values.get("lineHeight"));
				scaleW = Double.parseDouble(values.get("scaleW"));
			}
		}
		
		int lineHeightPixels = (int) (lineHeight - paddingHeight);
		float LINE_HEIGHT = 0.03f;
		verPerPixelSize = LINE_HEIGHT / (double) lineHeightPixels;
		horPerPixelSize = verPerPixelSize/aspectRatio;
	}
	
	public void processCharacters() {
		while(readLine()) {
			if(values.get("id") != null) {
				int id = getValue("id");
				double xTex = (getValue("x") + (padding[1] - 3)) / scaleW; //3 is desired padding
				double yTex = (getValue("y") + (padding[0] - 3)) / scaleW;
				
				double width = (getValue("width") - (paddingWidth -(2*3)));
				double height = (getValue("height") - (paddingHeight - (2 * 3)));
				double quadWidth = width * horPerPixelSize;
				double quadHeight = height * verPerPixelSize;
				double xTexSize = width / scaleW;
				double yTexSize = height / scaleW;
				double xOffset = (getValue("xoffset") + padding[1] - 3) * horPerPixelSize;
				double yOffset = (getValue("yoffset") + padding[0] - 3) * verPerPixelSize;
				double xAdvance = (getValue("xadvance") - paddingWidth) * horPerPixelSize;

				characters.put(id, new FontChar(id, xTex, yTex, xTexSize, yTexSize, xOffset, yOffset, quadWidth, quadHeight, xAdvance));

			}
		}
		values.clear();
		
		spaceWidth = (characters.get(32).getxAdvance() - paddingWidth) * horPerPixelSize;
	}
}