package com.yelink.fmandal.font;

import java.util.List;
import java.util.Map;

import com.yelink.fmandal.rendering.VertexArray;

public class TextMesh {

    private float xOffset, yOffset;
    private boolean timed;
    private VertexArray textVAO;

    // I won't save the values just yet, may do that at a later time.
    public TextMesh(float x, float y, float xOffset, float yOffset,
                    float xMax, float yMax, float xTC, float yTC,
                    float xMaxTC, float yMaxTC, boolean timed) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.timed = timed;

        float[] vertices = new float[] {
                xOffset + x, yOffset + y, 0.0f,
                xOffset + x, yOffset + y + yMax, 0.0f,
                xOffset + x + xMax, yOffset + y + yMax, 0.0f,
                xOffset + x + xMax, yOffset + y, + 0.0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                xOffset + xTC, yOffset + yTC,
                xOffset + xTC, yOffset + yMaxTC,
                xOffset + xMaxTC, yOffset + yMaxTC,
                xOffset + xMaxTC, yOffset + yTC
        };

        textVAO = new VertexArray(vertices, indices, tcs);
    }

    public VertexArray getVA() {
        return this.textVAO;
    }

    public float getXOffset() {
        return this.xOffset;
    }

    public float getYOffset() {
        return this.yOffset;
    }

    public boolean getTimed() {
        return this.timed;
    }
}
