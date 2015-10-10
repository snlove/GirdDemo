package com.example.zy.girddemo.OpenglAnim.OpenglUtil;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/8/28.
 */
public class GLGraphics  {
    GLSurfaceView glSurfaceView;
    GL10 gl;

    public GLGraphics(GLSurfaceView view) {
        this.glSurfaceView = view;
    }

    public GL10 getGl() {
        return gl;
    }

    public void setGl(GL10 gl) {
        this.gl = gl;
    }

    public int getWidth() {
        return  glSurfaceView.getWidth();
    }

    public int getHeight() {
        return  glSurfaceView.getHeight();
    }


}
