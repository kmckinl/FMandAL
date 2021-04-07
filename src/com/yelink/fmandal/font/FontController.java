package com.yelink.fmandal.font;

import java.util.Map;

import com.yelink.fmandal.entities.TextObject;
import com.yelink.fmandal.rendering.TextRenderer;
import com.yelink.fmandal.utilities.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FontController {
	/*
	 * Stores any Fonts to be used for rendering and a Map containing all
	 * strings of text to be rendered.
	 */
	
	private int id = 0;
	private Font bangers;
	private TextRenderer textRenderer;
	private TextReader textReader;
	private Map<Integer, TextObject> textMap = new HashMap<Integer, TextObject>();
	private List<TextObject> timedText = new ArrayList<TextObject>();
	
	public FontController(){
		// Load Fonts
		bangers = new Font("bangers", "res/bangers.fnt", "res/bangers.png");
		
		// Load renderer
		textRenderer = new TextRenderer();
		
		// Load reader
		textReader = new TextReader();
		
		// Load Shader (maybe)
	}
	
	public int loadText(String fontName, String text, float x, float y, boolean timed) {
		/*
		 * The use of a Map containing the fonts will be deployed in cases where more than one 
		 * font exists, and fontName will be used to locate the font.
		 * 
		 * May create two separate Maps, one for regular text (both permanent and input removed) 
		 * and one for combat text
		 */
		textMap.put(this.id, textReader.loadText(text, x, y, bangers.getCharMap(), timed));
		
		// Temporary store the assigned ID then increment for the next load
		int currId = this.id;
		this.id += 1;
		
		// Return the ID of the text that was loaded
		return currId;
	}
	
	public void renderText(Vector3f playerPosition) {
		// Call TextRenderer
		
		try {
			for (int id : textMap.keySet()) {
				if (textMap.get(id).getTimed()) {
					if (textMap.get(id).getTimer() <= 0) {
						textMap.remove(id);
					} else {
						textMap.get(id).decTimer(); 
					}
				}
			
				textRenderer.render(textMap, bangers.getTexture(), playerPosition);
			
			}
		} catch (java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeText(int id) {
		// Remove a string of text from the render list
		// May use TextObject input instead
		
	}
}
