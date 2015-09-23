package com.example.zy.girddemo.OpenglAnim.BasicElements;



import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/9/11.
 */
public class Cameor2D {
    private Vector2f postion;
    private float FrumWidth;
    private float FrumHeigh;
    private float zoom;
    private GLGraphics glGraphics;

    public Cameor2D(GLGraphics glGraphics, float frumHeigh, float frumWidth) {
        this.FrumHeigh = frumHeigh;
        this.FrumWidth = frumWidth;
        glGraphics = this.glGraphics;
        postion.setVector2f(frumWidth / 2, frumHeigh / 2);
        this.zoom = 1;
    }

    public void setViewPortMartrix() {
        GL10  gl = glGraphics.getGl();
        gl.glViewport(0,0,glGraphics.getWidth(),glGraphics.getHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(postion.x -FrumWidth/2 * zoom,postion.x+ FrumWidth/2 * zoom,postion.y - FrumHeigh/2 * zoom,postion.y + FrumHeigh/2 * zoom,1,-1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * 将触摸屏幕的点转换为当前视图下的坐标
     * @param touch
     */
    public void touchToWorld(Vector2f touch) {
        touch.x = (touch.x / glGraphics.getWidth() )* FrumWidth * zoom;
        touch.y = (touch.y /glGraphics.getHeight()) * FrumHeigh * zoom;
        //在当前坐标系下的坐标
        touch.add(postion).sub(FrumWidth * zoom / 2, FrumHeigh * zoom / 2);
    }
}
