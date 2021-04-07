package com.yelink.fmandal.font;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yelink.fmandal.entities.TextObject;

public class TextReader {
	
	private float xOffset = 0;
	private float yOffset = 0;
	
	private List<Integer> characters = new ArrayList<Integer>();
	private List<String> words = new ArrayList<String>();
	
	public TextReader() {}
	
	public TextObject loadText(String text, float x, float y, Map<Integer, FontChar> characterMap, boolean timed) {
		characters.clear();
		words.clear();
		
		TextObject textOb = new TextObject(text, x, y, 0, timed);
		
		for(String word : text.split(" ")) {
			words.add(word);
		}
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				characters.add((int) word.charAt(i));
			}
			
			characters.add(32);
		}

		textMeshes(characterMap, textOb);
		
		return textOb;
	}
	
	public void textMeshes(Map<Integer, FontChar> characterMap, TextObject textOb) {
		List<TextMesh> meshes = new ArrayList<TextMesh>();
		//Still missing code to determine if words will fit on line
		float x = textOb.getX();
		float y = textOb.getY();
		float xMax = 0.0f;
		float yMax = 0.0f;
		float ySum = 0.0f;
		int yCount = 0;
		float yAvg = 0.0f;
				
		for (int ch : characters) {
			if(ch == 32) {
				xMax = 24;
				yMax = 24;
			} else if (ch == 46) {
				xMax = (float) characterMap.get(ch).getSizeX() * 640;
				yMax = (float) characterMap.get(ch).getSizeY() * 480;
				y = yAvg - yMax;
			} else {
				xMax = (float) characterMap.get(ch).getSizeX() * 640;// + 24;
				yMax = (float) characterMap.get(ch).getSizeY() * 480;// + 24;
						
				ySum += yMax;
				yCount++;
				yAvg = ySum / yCount;

			}
					
			float xTC = (float) characterMap.get(ch).getxTC();
			float yTC = (float) characterMap.get(ch).getyTC();
			float xMaxTC = (float) characterMap.get(ch).getxMaxTC();
			float yMaxTC = (float) characterMap.get(ch).getyMaxTC();

			
			meshes.add(new TextMesh(x, y, xOffset, yOffset,
									xMax, yMax, xTC, yTC, xMaxTC, yMaxTC, textOb.getTimed()));
					
			x += xMax;
			y = textOb.getY();
					
		}
		
		textOb.setMeshes(meshes);
	}
}
