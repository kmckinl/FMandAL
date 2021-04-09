package com.yelink.fmandal;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class HUDElement {
	
	private boolean charIcon;
	private int frameTick = 0;
	private float xOffset, yOffset, xUpdate;
	private Vector3f position, movement;
	private VertexArray vao;
	
	public HUDElement(float x, float y, float width, float height, float xOffset, float yOffset, boolean charIcon) {
		this.charIcon = charIcon;
		this.position = new Vector3f(x, y, -0.1f);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xUpdate = (1.0f / 3.0f);
		this.movement = new Vector3f(0.0f, 0.0f, 0.0f);
		
		float[] vertices = new float[] {
			position.x, position.y - height, position.z,
			position.x + width, position.y - height, position.z,
			position.x + width, position.y, position.z,
			position.x, position.y, position.z
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			0, 2, 3
		};
		
		float[] tcs = new float[] {
			this.xOffset, this.yOffset,
			this.xOffset + (1.0f / 5.0f), this.yOffset,
			this.xOffset + (1.0f / 5.0f), this.yOffset + (1.0f / 4.0f),
			this.xOffset, this.yOffset + (1.0f / 4.0f)
		};
		
		vao = new VertexArray(vertices, indices, tcs);
		
	}
	
	public void update() {
		this.frameTick++;
		
		if(this.charIcon) {
			if (this.frameTick % 15 == 0) {
				if (this.xOffset >= (2.0f / 5.0f)) {
					this.xUpdate = -(1.0f / 5.0f);
				} else if (this.xOffset <= 0.0f) {
					this.xUpdate = (1.0f / 5.0f);
				}
				
				this.xOffset += this.xUpdate;
			}
		}
		
		
		if (this.frameTick >= 1050) {
			this.frameTick = 0;
		}
	}
	
	public void render() {
		this.vao.bind();
		Shader.HUD.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		Shader.HUD.setUniform2f("texOffset", this.xOffset, 0.0f);
		this.vao.draw();
		
		this.vao.unbind();
	}
	
	public VertexArray getVAO() {
		return this.vao;
	}
}
