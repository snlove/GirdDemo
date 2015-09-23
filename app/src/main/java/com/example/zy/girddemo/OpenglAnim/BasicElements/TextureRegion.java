package com.example.zy.girddemo.OpenglAnim.BasicElements;

/**
 * Created by zy on 2015/9/11.
 */
public class TextureRegion {

    private float u1;
    private float v1;
    private float u2;
    private float v2;
    private Texture texture;

    public TextureRegion(Texture texture, float x, float y, float width, float height) {
        this.u1 = x / texture.getWidth();
        this.v1 = y / texture.getHeight();
        this.u2 = this.u1 + width / texture.getWidth();
        this.v2 = this.v1 + height / texture.getHeight();
    }
}
