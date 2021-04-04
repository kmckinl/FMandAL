package com.yelink.fmandal.entities;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class Enemies {
	private Vector3f position;
	private VertexArray enemy;
	private Texture enemyTex;
	
	public Enemies() {
		position = new Vector3f(0.0f, 0.0f, 0.0f);
		
		float[] vertices = new float[] {
			1000.0f, 500.0f, 0.0f,
			1200.0f, 500.0f, 0.0f,
			1200.0f, 700.0f, 0.0f,
			1000.0f, 700.0f, 0.0f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			0, 2, 3
		};
		
		float[] tcs = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		};
		
		enemy = new VertexArray(vertices, indices, tcs);
		enemyTex = new Texture("res/panda.png");
	}
	
	public void update() {
		
	}
	
	public void render() {
		enemyTex.bind();
		Shader.CHARACTER.enable();
		enemy.bind();
		Shader.CHARACTER.setUniformMat4f("vw_matrix", Matrix4f.translate(this.position));
		enemy.draw();
		
		Shader.CHARACTER.disable();
		enemyTex.unbind();
	}
}
