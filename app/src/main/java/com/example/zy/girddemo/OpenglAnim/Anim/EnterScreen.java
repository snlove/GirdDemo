package com.example.zy.girddemo.OpenglAnim.Anim;


import com.example.zy.girddemo.OpenglAnim.BasicElements.Texture;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vector2f;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vertices;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Game;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/9/9.
 */
public class EnterScreen extends Screen {


    private float FrumViewWidth = 6.0f;
    private float FrumViewHeight = 6.0f;
    Vector2f cannon = new Vector2f(1.0f, 4.0f);
    Vector2f moveDis;
    Vector2f roattDis = new Vector2f(0.5f, 0.0f);
    Vector2f touchPos = new Vector2f();
    GLGraphics glGraphics;
    Vertices vertices;
    Vertices backtices;
    private boolean startAnimation = false;
    private Texture texture;
    private int texId;
    private Texture backTexture;
    private int backTexId;


    public EnterScreen(Game game, String filename) {
        super(game);
        glGraphics = game.getGraphics();
        initData();
        initTex("filename");
    }

    public EnterScreen(Game game, int bookId) {
        super(game);
        glGraphics = game.getGraphics();
        initData();
        initTex(bookId);
    }

    private void initData() {
        vertices = new Vertices(glGraphics, 4, 6, false, true);
        vertices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f}, 0, 16);
        vertices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        backtices = new Vertices(glGraphics, 4, 6, true, false);
        backtices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,}, 0, 24);
        backtices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
    }

    public void initTex(String filename) {
        texture = new Texture(game, filename);
        texId = texture.loadTexture();
    }

    public void initTex(int texId) {
        texture = new Texture(game, texId);
        texId = texture.loadTexture();
    }
    public void initBackTex(int texid) {
        backTexture = new Texture(game, texid);
        backTexId = backTexture.loadTexture();
    }

    /**
     * 进行某些数据的更新操作
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {

    }

    private int j = 1;
    private int k = 1;
    private int stepScale = 200;
    private float roateAngle = 0.0f;
    private static final int STOP_SCALE = 20;
    private static final float Scale_FACTOR = 3.0f;

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGl();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, FrumViewWidth, 0, FrumViewHeight, 1, -1);
        float x = roattDis.x - moveDis.x;
        float y = roattDis.y - moveDis.y;
        // 移动到指定位置
        // 移动到指定位置

        //在给定位置进行打开动作,模拟书本翻页动作

        if (roateAngle <= 180.0f) {
            if (roateAngle <= 60.0f) {
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                texture.bindTexture();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(1.0f + 4.0f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                //gl.glScalef(1.0f, 1.0f, 0.0f);
                backtices.bind();
                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                backtices.unBind();

                gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(1.0f + 4.5f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                // gl.glScalef(1.0f, 1.0f, 0.0f);
                gl.glRotatef(-roateAngle, 0, 1, 0);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                gl.glPopMatrix();
                roateAngle += 3f;
            }
            if (roateAngle > 60.0f) {

                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(1.0f + 4.5f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                //gl.glScalef(1.0f, 1.0f, 0.0f);
                backtices.bind();
                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                backtices.unBind();

                gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(1.0f + 4.5f / STOP_SCALE * k, 1.0f + 5.0f / STOP_SCALE * k, 0.0f);
                gl.glRotatef(-roateAngle, 0, 1, 0);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                gl.glPopMatrix();
                roateAngle += 1.5f;
            }
            if (k < 20) {
                k++;
            } else {
                k = 20;
            }

        }
        gl.glPopMatrix();


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

    public void setEnterPos(int postion) {
        List<Vector2f> enterLists = new ArrayList<Vector2f>();
        enterLists.add(0, new Vector2f(1.0f, 5.0f));
        enterLists.add(1, new Vector2f(3.0f, 5.0f));
        enterLists.add(2, new Vector2f(5.0f, 5.0f));
        enterLists.add(3, new Vector2f(1.0f, 3.0f));
        enterLists.add(4, new Vector2f(3.0f, 3.0f));
        enterLists.add(5, new Vector2f(5.0f, 3.0f));
        enterLists.add(6, new Vector2f(1.0f, 0.0f));
        enterLists.add(7, new Vector2f(3.0f, 0.0f));
        enterLists.add(8, new Vector2f(5.0f, 0.0f));
        moveDis = enterLists.get(postion);
    }
}
