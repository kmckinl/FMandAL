package com.yelink.fmandal.rendering;

public class FontShader extends Shader{

    private static final String VERTEX = "shaders/font.vs";
    private static final String FRAGMENT = "shaders/font.fs";

    public FontShader() {
        super(VERTEX, FRAGMENT);
    }

}
