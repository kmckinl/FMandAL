package com.yelink.fmandal.rendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.yelink.fmandal.entities.TextObject;
import com.yelink.fmandal.font.TextMesh;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class TextRenderer {

    public TextRenderer() {
        // If I choose the route of a separate shader for Fonts
        // I'll initialize that here
    }

    public void render(Map<Integer, TextObject> textMap, Texture texture, Vector3f position) {
        //GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glDisable(GL11.GL_DEPTH_TEST);
        texture.bind();
        Shader.FONT.enable();

        for (int id : textMap.keySet()) {
            //GL13.glActiveTexture(GL13.GL_TEXTURE0);

            renderText(textMap.get(id), position);
        }

        Shader.FONT.disable();
        texture.unbind();
        //GL11.glDisable(GL11.GL_BLEND);
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void renderText(TextObject text, Vector3f position) {
        List<TextMesh> meshList = text.getMeshes();
        for (TextMesh mesh : meshList) {
            mesh.getVA().bind();

            if (mesh.getTimed()) {
                Shader.FONT.setUniformMat4f("vw_matrix", Matrix4f.translate(position));
            } else {
                Shader.FONT.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
            }

            Shader.FONT.setUniform2f("texOffset", mesh.getXOffset(), mesh.getYOffset());

            mesh.getVA().draw();

            mesh.getVA().unbind();
        }
    }
}
