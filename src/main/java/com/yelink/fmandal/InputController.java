package com.yelink.fmandal;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class InputController extends GLFWKeyCallback {
    public static boolean[] keys = new boolean[65536];

    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW.GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public static boolean isKeyReleased(int keycode) {
        return keys[keycode];
    }

    public static boolean isKeyPressed(int keycode) {
        return keys[keycode];
    }
}