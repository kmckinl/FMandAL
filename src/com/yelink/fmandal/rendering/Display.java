package com.yelink.fmandal.rendering;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL13.*;

public class Display {
	public static final int WINDOW_WIDTH = 1920;
	public static final int WINDOW_HEIGHT = 1080;
	
	public static long window;

	public static void createDisplay(String title, boolean resizable) {
		if (!glfwInit()) {
			return;
		}
		
		if (resizable == true) {
			glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		} else {
			glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		}
		
		window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, title, NULL, NULL);
		if (window == NULL) {
			return;
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - WINDOW_WIDTH) / 2, (vidmode.height() - WINDOW_HEIGHT) / 2);
		
		// Input Stuff
		//glfwSetKeyCallback(window, new Input());
		//glfwSetCursorPosCallback(window, new Cursor());
		//glfwSetMouseButtonCallback(window, new MouseInput());
		//glfwSetInputMode(window, GLFW_STICKY_MOUSE_BUTTONS, GLFW_TRUE);
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		//glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		//17, 21, 28
		glClearColor(237.0f / 255.0f, 203.0f / 255.0f, 177.0f / 255.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void updateDisplay() {
		glfwPollEvents();
		
		//glfwSwapInterval(0);
		//glfwSwapBuffers(window);
	}
	
	public static void closeDisplay() {
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
}
