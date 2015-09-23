package com.example.zy.girddemo.OpenglAnim.Anim;



import android.content.Intent;

import com.example.zy.girddemo.MainActivity;
import com.example.zy.girddemo.OpenglAnim.BasicElements.BasciScreen;
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
 * Created by zy on 2015/9/22.
 */
public class ExitScreen extends Screen {


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
    private   boolean returnMain = false;
    public ExitScreen(Game game) {
        super(game);
        glGraphics =  game.getGraphics();
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
    float stepXsmalle = 180.0f;
    private static final int STOP_SCALE = 20;
    private static final float Scale_FACTOR = 3.0f;
    float x = moveDis.x - cannon.x;
    float y = moveDis.y - cannon.y;
    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGl();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, FrumViewWidth, 0, FrumViewHeight, 1, -1);


           //回退后合住书本

            if(stepXsmalle >0.0f) {
                if(stepXsmalle == 180.0f) {
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    texture.bindTexture();
                    gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    gl.glTranslatef(1.0f, 0.0f, 0);
                    gl.glScalef(5.0f, 6.0f, 0.0f);
                    gl.glRotatef(-stepXsmalle, 0, 1, 0);
                    vertices.bind();
                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                    vertices.unBind();

                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(1.0f, 0.0f, 0);
                    gl.glScalef(5.0f, 6.0f, 0.0f);
                    backtices.bind();
                    backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                    backtices.unBind();
                }
                if(stepXsmalle<=120.0f) {

                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(roattDis.x + 1.0f / STOP_SCALE * k, roattDis.y + 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(5.0f - 4.0f / STOP_SCALE * k, 6.0f - 5.0f / STOP_SCALE * k, 0.0f);
                    backtices.bind();
                    backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                    backtices.unBind();
                    gl.glPopMatrix();

                    gl.glPushMatrix();
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(roattDis.x + 1.0f / STOP_SCALE * k, roattDis.y + 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(5.0f - 4.0f / STOP_SCALE * k, 6.0f - 5.0f / STOP_SCALE * k, 0.0f);
                    gl.glRotatef(-stepXsmalle, 0, 1, 0);
                    vertices.bind();
                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                    vertices.unBind();
                    gl.glPopMatrix();
                    stepXsmalle -= 3.0f;
            }
                if(stepXsmalle>120.0f && stepXsmalle <=180.0f) {
                    gl.glPushMatrix();
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(roattDis.x + 1.0f / STOP_SCALE * k, roattDis.y + 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(5.0f - 4.0f / STOP_SCALE * k, 6.0f - 5.0f / STOP_SCALE * k, 0.0f);
                    gl.glRotatef(-stepXsmalle, 0, 1, 0);
                    vertices.bind();
                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                    vertices.unBind();
                    gl.glPopMatrix();

                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glTranslatef(roattDis.x + 1.0f / STOP_SCALE * k, roattDis.y + 2.0f / STOP_SCALE * k, 0);
                    gl.glScalef(5.0f - 4.0f / STOP_SCALE * k, 6.0f - 5.0f / STOP_SCALE * k, 0.0f);
                    backtices.bind();
                    backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                    backtices.unBind();
                    gl.glPopMatrix();
                    k++;
                    stepXsmalle -= 3.0f;
                }

            }  else {
//                if (j <= 21) {
//                    gl.glPushMatrix();
//                    gl.glMatrixMode(GL10.GL_MODELVIEW);
//                    gl.glLoadIdentity();
//                    gl.glTranslatef(moveDis.x - x / 20 * j, moveDis.y - y / 20 * j, 0.0f);
//                    vertices.bind();
//                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
//                    vertices.unBind();
//                    j++;
//                } else {
//                    gl.glMatrixMode(GL10.GL_MODELVIEW);
//                    gl.glLoadIdentity();
//                    gl.glTranslatef(cannon.x, cannon.y, 0);
//                    vertices.bind();
//                    vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
//                    vertices.unBind();
//                }

                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glTranslatef(moveDis.x, moveDis.y, 0.0f);
                gl.glScalef(1.0f, 1.0f, 0.0f);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                reMain();
            }



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
           returnMain = false;
    }

    @Override
    public void dispose() {

    }

    public void reMain() {
        Intent i = new Intent();
        i.setAction("android.intent.action.exit");
        game.getContext().startActivity(i);
        LogMes.d("Exit","==============Exti the Screen");
    }
}

