package com.yelink.fmandal.entities;

import java.util.ArrayList;
import java.util.List;

import com.yelink.fmandal.font.TextMesh;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.rendering.VertexArrayObject;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class TextObject {
    /*
     * TextObject will hold the VertexArrayObjects (Vertex Arrays) for a given string of text
     */
    private String text;
    private int id;
    private int timer = 120;
    private boolean timed;
    private boolean render =  false;
    private VertexArrayObject vao;
    private Vector3f position;

    private List<TextMesh> meshes;// = new ArrayList<TextMesh>();
    private static List<VertexArrayObject> meshesVAO = new ArrayList<VertexArrayObject>();

    public TextObject(String text, float x, float y, int id, boolean timed) {
        this.text = text;
        this.position = new Vector3f(x, y, 0.0f);
        this.id = id;
        this.timed = timed;
    }

    public int getId() {
        return this.id;
    }

    public static void addMesh(float[] vertices, byte[] indices, float[] tcs, float xTC, float yTC) {
        meshesVAO.add(new VertexArrayObject(new VertexArray(vertices, indices, tcs), xTC, yTC));
    }

    public void setMeshes(List<TextMesh> textMeshes) {
        this.meshes = textMeshes;
    }

    public List<TextMesh> getMeshes() {
        return this.meshes;
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public void decTimer() {
        this.timer -= 1;
    }

    public int getTimer() {
        return this.timer;
    }

    public boolean getTimed() {
        return this.timed;
    }

    public String getText() {
        return this.text;
    }

    public void cleanUp() {
        for (VertexArrayObject mesh : meshesVAO) {
            mesh.getVA().cleanUp();
        }
    }
}
