package com.example.zy.girddemo.OpenglAnim.Anim;


import com.example.zy.girddemo.OpenglAnim.BasicElements.Texture;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vector2f;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vertices;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Game;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Input;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/9/9.
 */
public class RectAngle extends Screen {


    private float FrumViewWidth = 6.0f;
    private float FrumViewHeight = 6.0f;
    Vector2f cannon = new Vector2f(1.0f, 4.0f);
    Vector2f moveDis = new Vector2f(2.0f, 2.0f);
    Vector2f roattDis = new Vector2f(1.0f, 0.0f);
    Vector2f touchPos = new Vector2f();
    GLGraphics glGraphics;
    Vertices vertices;
    Vertices backtices;
    private boolean startAnimation = false;
    private Texture texture;
    private int texId;


    public RectAngle(Game game) {
        super(game);
        glGraphics = game.getGraphics();
        vertices = new Vertices(glGraphics, 4, 6, false, true);
        vertices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f}, 0, 16);
        vertices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        texture = new Texture(game, "bobargb8888.png");
        texId = texture.loadTexture();
        backtices = new Vertices(glGraphics, 4, 6, true, false);
        backtices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,}, 0, 24);
        backtices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
    }

    public RectAngle(Game game, int bookId) {
        super(game);
        glGraphics = game.getGraphics();
        vertices = new Vertices(glGraphics, 4, 6, false, true);
        vertices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f}, 0, 16);
        vertices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        texture = new Texture(game, bookId);
        texId = texture.loadTexture();
        backtices = new Vertices(glGraphics, 4, 6, true, false);
        backtices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,}, 0, 24);
        backtices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
    }


    //得到旋转的角度
    @Override
    public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        LogMes.d("Cannon", "==================" + touchEvents.size());
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            //世界坐标转换
            touchPos.x = (event.x / (float) glGraphics.getWidth()) * FrumViewWidth;
            touchPos.y = (1 - event.y / (float) glGraphics.getHeight()) * FrumViewHeight;

        }
        if (len > 0) {
            startAnimation = true;
        }


    }

    int j = 1;
    int k = 1;
    int stepScale = 200;
    float stepXsmalle = 0.0f;
    private static final int STOP_SCALE = 20;
    private static final float Scale_FACTOR = 3.0f;
    float testangle = 63.0f;

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGl();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, FrumViewWidth, 0, FrumViewHeight, 1, -1);


        //startAnimation = false;
        float x = moveDis.x - cannon.x;
        float y = moveDis.y - cannon.y;
        // 移动到指定位置
        // 移动到指定位置
        if (j <= 21) {
            gl.glPushMatrix();
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            texture.bindTexture();
            gl.glTranslatef(cannon.x + x / 20 * j, cannon.y + y / 20 * j, 0.0f);
            vertices.bind();
            vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
            vertices.unBind();
            j++;
        }
        //在给定位置进行打开动作,模拟书本翻页动作
        else {
            if (stepXsmalle <= 180.0f) {
                if(stepXsmalle<=60.0f) {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(moveDis.x - 1.0f / STOP_SCALE * k, moveDis.y - 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(1.0f + 4.0f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                    //gl.glScalef(1.0f, 1.0f, 0.0f);
                    backtices.bind();
                    backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                    backtices.unBind();

                    gl.glPushMatrix();
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(moveDis.x - 1.5f / STOP_SCALE * k, moveDis.y - 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(1.0f + 4.5f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                    // gl.glScalef(1.0f, 1.0f, 0.0f);
                    gl.glRotatef(-stepXsmalle, 0, 1, 0);
                    vertices.bind();
                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                    vertices.unBind();
                    gl.glPopMatrix();
                    stepXsmalle += 3.0f;
                }
                if (stepXsmalle > 60.0f) {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(moveDis.x - 1.5f / STOP_SCALE * k, moveDis.y - 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(1.0f + 4.5f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                    //gl.glScalef(1.0f, 1.0f, 0.0f);
                    backtices.bind();
                    backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                    backtices.unBind();

                    gl.glPushMatrix();
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(moveDis.x - 1.5f / STOP_SCALE * k, moveDis.y - 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(1.0f + 4.5f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                    // gl.glScalef(1.0f, 1.0f, 0.0f);
                    gl.glRotatef(-stepXsmalle, 0, 1, 0);
                    vertices.bind();
                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                    vertices.unBind();
                    gl.glPopMatrix();
                    stepXsmalle += 1.5f;
                }
                if (k < 20) {
                    k++;
                } else {
                    k = 20;
                }

            } else {
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                texture.dispose();
                gl.glScalef(6.0f, 6.0f, 6.0f);
                gl.glColor4f(1.0f, 0.5f, 1.0f, 1.0f);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
            }
        }
        gl.glPopMatrix();
        LogMes.d("Scale", "========let the squre scale");

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
