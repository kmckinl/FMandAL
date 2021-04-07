package com.yelink.fmandal.rendering;

import java.util.HashMap;
import java.util.Map;

import com.yelink.fmandal.utilities.ShaderUtils;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader TEST, CHARACTER, ENEMIES, TERRAIN;
	public static FontShader FONT;
	
	private boolean enabled = false;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll() {
		TEST = new Shader("shaders/test.vs", "shaders/test.fs");
		CHARACTER = new Shader("shaders/character.vs", "shaders/character.fs");
		ENEMIES = new Shader("shaders/enemies.vs", "shaders/enemies.fs");
		TERRAIN = new Shader("shaders/terrain.vs", "shaders/terrain.fs");
		FONT = new FontShader(); 
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		
		int result = glGetUniformLocation(ID, name);
		
		if(result == -1) {
			System.err.println("Could not find uniform variable " + name + ".");
		} else {
			locationCache.put(name,  result);
		}
		return result;
	}
	
	public void setUniform1i(String name, int value) {
		if(!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		if(!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y) {
		if (!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		if(!enabled) enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		if (!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable() {
		glUseProgram(ID);
		enabled = true;
	}
	
	public void disable() {
		glUseProgram(0);
		enabled = false;
	}
	
	public void cleanUp() {
		glUseProgram(0);
		glDeleteProgram(ID);
	}
}