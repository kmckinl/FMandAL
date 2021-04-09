package com.yelink.fmandal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;

public class HUDController {
	
	public enum Indices {
		FLYMAN, AQUALAD, FMHEALTH, ALHEALTH, FLY // Maybe do a map of lists, even though flyman and aqualad would only have one item
	}
	private Player player;
	
	//private List<HUDElement> hudElements = new ArrayList<HUDElement>();
	private Map<Indices, List<HUDElement>> hudElements = new HashMap<Indices, List<HUDElement>>();
	private Texture texture;
	
	public HUDController(Player player) {
		this.player = player;
		this.texture = new Texture("res/hudsprites.png");
		
		// Fly-Man Icon
		//this.hudElements.add(new HUDElement(192.0f, 144.0f, 96.0f, 96.0f, 0.0f, 0.0f, true));
		this.hudElements.put(Indices.FLYMAN, new ArrayList<HUDElement>());
		this.hudElements.get(Indices.FLYMAN).add(new HUDElement(192.0f, 144.0f, 96.0f, 96.0f, 0.0f, 0.0f, true));
		//this.fmIndex = 0;
		// Fly-Man Health
		this.hudElements.put(Indices.FMHEALTH, new ArrayList<HUDElement>());
		for (int i = 0; i < player.getCharacter().getHealth() / 20; i++) {
			this.hudElements.get(Indices.FMHEALTH).add(new HUDElement(288.0f + (float) i * 50.0f, 144.0f, 48.0f, 48.0f, 0.0f, (2.0f / 4.0f), false));
		}
		System.out.println(this.hudElements.get(Indices.FMHEALTH).size());
		// Aqua Lad Icon
		//this.hudElements.add(new HUDElement(256.0f, 144.0f, 96.0f, 96.0f, 0.0f, (1.0f / 4.0f), true));
		//this.alIndex = 2;
		this.hudElements.put(Indices.AQUALAD, new ArrayList<HUDElement>());
		this.hudElements.get(Indices.AQUALAD).add(new HUDElement(600.0f, 144.0f, 96.0f, 96.0f, 0.0f, (1.0f / 4.0f), true));
		
		//Aqua Lad Health
		this.hudElements.put(Indices.ALHEALTH, new ArrayList<HUDElement>());
		for (int i = 0; i < player.getCharacter().getHealth() / 20; i++) {
			this.hudElements.get(Indices.ALHEALTH).add(new HUDElement(696.0f + (float) i * 50.0f, 144.0f, 48.0f, 48.0f, 0.0f, (2.0f / 4.0f), false));
		}
		
	}
	
	public void update() {
		/*for (HUDElement hude : hudElements) {
			hude.update();
		}*/
		for (Indices index : hudElements.keySet()) {
			for (HUDElement hudList : hudElements.get(index)) {
				hudList.update();
			}
		}
	}
	
	public void render() {
		/*this.texture.bind();
			Shader.HUD.enable();
		for (HUDElement hude : hudElements) {
			hude.render();
		}
		Shader.HUD.disable();
		this.texture.unbind();*/
		this.texture.bind();
		Shader.HUD.enable();
		for (Indices index : hudElements.keySet()) {
			for (HUDElement hudList : hudElements.get(index)) {
				hudList.render();
			}
		}
		Shader.HUD.disable();
		this.texture.unbind();
	}
}
