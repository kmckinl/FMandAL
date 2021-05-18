package com.yelink.fmandal.rendering;

public class VertexArrayObject {
    private float xOffset, yOffset;

    private VertexArray va;

    public VertexArrayObject(VertexArray va) {
        this.va = va;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    public VertexArrayObject(VertexArray va, float xOffset, float yOffset) {
        this.va = va;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public float getXOffset() {
        return this.xOffset;
    }

    public void setXOffset(float newX) {
        this.xOffset = newX;
    }

    public float getYOffset() {
        return this.yOffset;
    }

    public void setYOffset(float newY) {
        this.yOffset = newY;
    }

    public VertexArray getVA() {
        return this.va;
    }

}