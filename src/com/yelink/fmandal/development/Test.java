package com.yelink.fmandal.development;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class Test {
	private VertexArray testVA;
	
	public Test() {
		float[] vertices = new float[] {
			100.0f, 100.0f, 0.0f,
			200.0f, 100.0f, 0.0f,
			200.0f, 200.0f, 0.0f,
			100.0f, 200.0f, 0.0f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			0, 2, 3
		};
		
		float[] tcs = new float[] {
			0, 0,
			0, 1,
			1, 1,
			1, 0
		};
		
		testVA = new VertexArray(vertices, indices);
	}
	
	public void update() {
		
	}
	
	public void render() {
		Shader.TEST.enable();
		testVA.bind();
		Shader.TEST.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		testVA.draw();
		
		Shader.TEST.disable();
		//testVA.unbind();
		
	}
	
	public void cleanUp() {
		//Shader.TEST.cleanUp();
		//testVA.cleanUp();
	}
}
