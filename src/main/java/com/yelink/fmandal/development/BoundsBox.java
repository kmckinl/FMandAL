package com.yelink.fmandal.development;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class BoundsBox {
    private Vector3f position, movement;
    private VertexArray vao;

    public BoundsBox(float x, float y, float width, float height, float[] center) {
        this.position = new Vector3f(center[0] - width / 2, center[1] - height / 2, 0.1f);
        this.movement = new Vector3f(0.0f, 0.0f, 0.0f);

        float[] vertices = new float[] {
                position.x, position.y, 0.1f,
                position.x + width, position.y, 0.1f,
                position.x + width, position.y + height, 0.1f,
                position.x, position.y + height, 0.1f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                0, 2, 3
        };

        vao = new VertexArray(vertices, indices);
    }

    public void render() {
        Shader.TEST.enable();
        vao.bind();
        Shader.TEST.setUniformMat4f("vw_matrix", Matrix4f.translate(this.movement));
        vao.draw();


        Shader.TEST.disable();
        vao.unbind();
    }

    public void updatePosition(float x, float y) {
        this.movement.x += x;
        this.movement.y += y;
    }
}
