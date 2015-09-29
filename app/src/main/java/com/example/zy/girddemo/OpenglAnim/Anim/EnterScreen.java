package com.example.zy.girddemo.OpenglAnim.Anim;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.zy.girddemo.OpenglAnim.BasicElements.Texture;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vector2f;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vertices;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Game;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;
import com.example.zy.girddemo.R;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/9/9.
 */
public class EnterScreen extends Screen {


    private float FrumViewWidth = 6.0f;
    private float FrumViewHeight = 6.0f;
    Vector2f moveDis= new Vector2f(0.0f,0.0f);
    Vector2f roattDis = new Vector2f(FrumViewWidth*0.083f, 0.0f);
    GLGraphics glGraphics;
    Vertices vertices;
    Vertices backtices;
    private boolean startAnimation = false;
    private Texture texture;
    private int texId;
    private Texture backTexture;
    private int backTexId;
    private int bookId;
    private float viewXScale = 0.3228f;
    private float viewYScale = 0.3206f;

    public EnterScreen(Game game, String filename) {
        super(game);
        glGraphics = game.getGraphics();
        setFrum(1.0f);
        initData();
        initTex("filename");
    }

    public EnterScreen(Game game, int bookId) {
        super(game);
        glGraphics = game.getGraphics();
        setFrum(1.0f);
        LogMes.d("Screen","======="+ FrumViewWidth+"======"+FrumViewHeight);
        initData();
        this.bookId = bookId;
        //initTex(bookId);
    }

    private void initData() {
        vertices = new Vertices(glGraphics, 4, 6, false, true);
        vertices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f}, 0, 16);
        vertices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        backtices = new Vertices(glGraphics, 4, 6, false, true);
        backtices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,}, 0, 16);
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
            initTex(bookId);
            initBackTex(R.mipmap.book);
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
                backTexture.bindTexture();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth*viewXScale + FrumViewWidth*(0.92f-viewXScale) / STOP_SCALE * k,
                        FrumViewHeight*viewYScale + FrumViewHeight*(1.0f-viewYScale) / STOP_SCALE * k, 0.0f);
                backtices.bind();
                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                backtices.unBind();
                backTexture.dispose();
                gl.glPopMatrix();


                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                texture.bindTexture();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth*viewXScale + FrumViewWidth*(0.92f-viewXScale) / STOP_SCALE * k,
                        FrumViewHeight*viewYScale + FrumViewHeight*(1.0f-viewYScale) / STOP_SCALE * k, 0.0f);
                gl.glRotatef(-roateAngle, 0, 1, 0);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                texture.dispose();
                gl.glPopMatrix();
                roateAngle += 3f;
            }
            if (roateAngle > 60.0f) {

                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                backTexture.bindTexture();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth*viewXScale + FrumViewWidth*(0.92f-viewXScale) / STOP_SCALE * k,
                        FrumViewHeight*viewYScale + FrumViewHeight*(1.0f-viewYScale) / STOP_SCALE * k, 0.0f);
                backtices.bind();
                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                backtices.unBind();
                backTexture.dispose();
                gl.glPopMatrix();


                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                texture.bindTexture();
                gl.glTranslatef(moveDis.x + x / STOP_SCALE * k, moveDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth*viewXScale + FrumViewWidth*(0.92f-viewXScale) / STOP_SCALE * k,
                        FrumViewHeight*viewYScale + FrumViewHeight*(1.0f-viewYScale) / STOP_SCALE * k, 0.0f);
                gl.glRotatef(-roateAngle, 0, 1, 0);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                texture.dispose();
                gl.glPopMatrix();
                roateAngle += 1.5f;
            }
            if (k < 20) {
                k++;
            } else {
                k = 20;
            }

        } else {
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glColor4f(0.4f,0.6f,0.6f,1);
            gl.glScalef(FrumViewWidth,FrumViewHeight,0.0f);
            vertices.bind();
            vertices.draw(GL10.GL_TRIANGLES,0,vertices.getnumberSize());
            vertices.unBind();
        }



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

    public void setEnterPos(float xScale,float yScale) {
        moveDis.x = FrumViewWidth * xScale;
        moveDis.y = FrumViewHeight *(1.0f - yScale);
    }

    public void setFrum(float scaleFrum) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) game.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        float screenWidth = metrics.widthPixels;
        float screenHeigt = metrics.heightPixels;
        FrumViewWidth = screenWidth * scaleFrum;
        FrumViewHeight = screenHeigt * scaleFrum;
        roattDis = new Vector2f(FrumViewWidth*0.083f, 0.0f);
    }
}
