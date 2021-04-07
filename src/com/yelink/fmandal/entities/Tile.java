package com.yelink.fmandal.entities;

import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Vector3f;

public class Tile {
	
	private float xOffset, yOffset;
	
	private Vector3f position, movement;
	private VertexArray vao;
	private Texture texture;
	
	public Tile(float x, float y, float xOffset, float yOffset) {
		this.position = new Vector3f(x, y, 0.1f);
		this.movement = new Vector3f(0.0f, 0.0f, 0.0f);
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
		float[] vertices = new float[] {
			this.position.x, this.position.y, 0.1f,
			this.position.x + 64.0f, this.position.y, 0.1f,
			this.position.x + 64.0f, this.position.y + 64.0f, 0.1f,
			this.position.x, this.position.y + 64.0f, 0.1f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2, 
			0, 2, 3
		};
		
		float[] tcs = new float[] {
			xOffset, yOffset,
			xOffset + 0.25f, yOffset,
			xOffset + 0.25f, yOffset + 0.25f,
			xOffset, yOffset + 0.25f
		};
		
		
		vao = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/road.png");
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getYOffset() {
		return this.yOffset; 
	}
	
	public VertexArray getVAO() {
		return this.vao;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
}
