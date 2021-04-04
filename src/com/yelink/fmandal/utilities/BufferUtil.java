package com.yelink.fmandal.utilities;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.system.MemoryUtil;

public class BufferUtil {
	public static FloatBuffer floatBuffer(float[] array) {
		FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(array.length);
		return floatBuffer.put(array).flip();
	}
	
	public static IntBuffer intBuffer(int[] array) {
		IntBuffer intBuffer = MemoryUtil.memAllocInt(array.length);
		return intBuffer.put(array).flip();
	}
	
	public static ShortBuffer shortBuffer(short[] array) {
		ShortBuffer shortBuffer = MemoryUtil.memAllocShort(array.length);
		return shortBuffer.put(array).flip();
	}
	
	
}
