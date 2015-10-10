package com.example.zy.girddemo.OpenglAnim.Anim;


import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.example.zy.girddemo.OpenglAnim.BasicElements.Texture;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vector2f;
import com.example.zy.girddemo.OpenglAnim.BasicElements.Vertices;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Game;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;
import com.example.zy.girddemo.R;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/9/22.
 */
public class ExitScreen extends Screen {


    private float FrumViewWidth = 6.0f;
    private float FrumViewHeight = 6.0f;
    Vector2f moveDis;
    Vector2f roattDis;
    GLGraphics glGraphics;
    Vertices vertices;
    Vertices backtices;
    private Texture texture;
    private int texId;
    private Texture backTexture;
    private int backId;
    private int bookId;
    private  CallBackBook callBackBook;
    private float viewXScale = 0.3228f;
    private float viewYScale = 0.3206f;


    public ExitScreen(Game game) {
        super(game);
        glGraphics = game.getGraphics();
        setFrum(1.0f);
        initData();

    }

    public ExitScreen(Game game,int bookId) {
        super(game);
        glGraphics = game.getGraphics();
        setFrum(1.0f);
        initData();
        this.bookId = bookId;

    }

    private void initData() {
        vertices = new Vertices(glGraphics, 4, 6, false, true);
        vertices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f}, 0, 16);
        vertices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        backtices = new Vertices(glGraphics, 4, 6, false, true);
        backtices.setVerticesbuff(new float[]{
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f}, 0, 16);
        backtices.setIndexsBuff(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
    }

    private void initTex(String filename) {
        texture = new Texture(game, filename);
        texture.loadTexture();
    }

    private void initTex(int bookId) {
        texture = new Texture(game, bookId);
        texture.loadTexture();
    }

    private void intiBackTex(int recourceId) {
        backTexture = new Texture(game,recourceId);
        backTexture.loadTexture();
    }

    //更新纹理
    @Override
    public void update(float deltaTime) {
         initTex(bookId);
         intiBackTex(R.mipmap.book);

    }

    int j = 1;
    int k = 1;
    int stepScale = 200;
    float stepXsmalle = 180.0f;
    private static final int STOP_SCALE = 20;
    private static final float Scale_FACTOR = 3.0f;

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGl();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, FrumViewWidth, FrumViewHeight, 0, 1, -1);
        float x = moveDis.x - roattDis.x;
        float y = moveDis.y - roattDis.y;

        //回退后合住书本

        if (stepXsmalle > 0.0f) {
//            if (stepXsmalle == 180.0f) {
//                gl.glPushMatrix();
//                gl.glMatrixMode(GL10.GL_MODELVIEW);
//                gl.glLoadIdentity();
//                texture.bindTexture();
//                gl.glTranslatef(FrumViewWidth*0.1f, 0.0f, 0);
//                gl.glScalef(FrumViewWidth*0.9f, FrumViewHeight, 0.0f);
//                gl.glRotatef(-stepXsmalle, 0, 1, 0);
//                vertices.bind();
//                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
//                vertices.unBind();
//                texture.dispose();
//                gl.glPopMatrix();
//
//                gl.glPushMatrix();
//                gl.glMatrixMode(GL10.GL_MODELVIEW);
//                gl.glLoadIdentity();
//                backTexture.bindTexture();
//                gl.glTranslatef(FrumViewWidth*0.1f, 0.0f, 0);
//                gl.glScalef(FrumViewWidth*0.9f, FrumViewHeight, 0.0f);
//                backtices.bind();
//                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
//                backtices.unBind();
//                backTexture.dispose();
//                gl.glPopMatrix();
//            }
            if (stepXsmalle <= 60.0f) {
                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                backTexture.bindTexture();
                gl.glTranslatef(roattDis.x + x / STOP_SCALE * k, roattDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth*0.9f - FrumViewWidth *(0.9f - viewXScale) / STOP_SCALE * k,
                        FrumViewHeight - FrumViewHeight* ( 1.0f- viewYScale) / STOP_SCALE * k, 0.0f);
                backtices.bind();
                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                backtices.unBind();
                backTexture.dispose();
                gl.glPopMatrix();

                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                texture.bindTexture();
                gl.glTranslatef(roattDis.x + x / STOP_SCALE * k, roattDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth*0.9f - FrumViewWidth *(0.9f - viewXScale) / STOP_SCALE * k,
                        FrumViewHeight - FrumViewHeight* ( 1.0f- viewYScale) / STOP_SCALE * k, 0.0f);
                gl.glRotatef(-stepXsmalle, 0, 1, 0);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                texture.dispose();
                gl.glPopMatrix();
                stepXsmalle -= 3.0f;
            }
            if (stepXsmalle >=60.0f && stepXsmalle <= 180.0f) {
                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                texture.bindTexture();
                gl.glTranslatef(roattDis.x + x / STOP_SCALE * k, roattDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth * 0.9f - FrumViewWidth * (0.9f - viewXScale) / STOP_SCALE * k,
                        FrumViewHeight - FrumViewHeight * (1.0f - viewYScale) / STOP_SCALE * k, 0.0f);
                gl.glRotatef(-stepXsmalle, 0, 1, 0);
                vertices.bind();
                vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
                vertices.unBind();
                texture.dispose();
                gl.glPopMatrix();

                gl.glPushMatrix();
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                backTexture.bindTexture();
                gl.glTranslatef(roattDis.x + x / STOP_SCALE * k, roattDis.y + y / STOP_SCALE * k, 0);
                gl.glScalef(FrumViewWidth * 0.9f - FrumViewWidth * (0.9f - viewXScale) / STOP_SCALE * k,
                        FrumViewHeight - FrumViewHeight * (1.0f - viewYScale) / STOP_SCALE * k, 0.0f);
                backtices.bind();
                backtices.draw(GL10.GL_TRIANGLES, 0, backtices.getnumberSize());
                backtices.unBind();
                backTexture.dispose();
                gl.glPopMatrix();
                k ++;
                stepXsmalle -= 6.0f;
            }


        }
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

//            gl.glPushMatrix();
//            gl.glMatrixMode(GL10.GL_MODELVIEW);
//            gl.glLoadIdentity();
//            gl.glTranslatef(moveDis.x, moveDis.y, 0.0f);
//            gl.glScalef(1.0f, 1.0f, 0.0f);
//            vertices.bind();
//            vertices.draw(GL10.GL_TRIANGLES, 0, vertices.getnumberSize());
//            vertices.unBind();
       else {
            callBackBook.reMain();
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

    /**
     *
     *
     * @param scalex_FACTOR 左下角x占屏幕的比例
     * @param scaley_FACTOR  左下角y占屏幕的比例
     */
    public void setEndPostion(float scalex_FACTOR,float scaley_FACTOR) {
        moveDis.x = FrumViewWidth * scalex_FACTOR;
        moveDis.y = FrumViewHeight * (scaley_FACTOR);


        LogMes.d("TAGPOS", "=========moveDis.x: " + moveDis.x + "moveDis.y :" + moveDis.y);
    }


    /**
     * 设置显示屏幕的大小，根据一定比例进行缩放
     * @param screen_FACTOR
     */

    private void setFrum(float screen_FACTOR) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) game.getContext().getSystemService(Context.WINDOW_SERVICE);
//        windowManager.getDefaultDisplay().getMetrics(metrics);
//        float screenWidth = metrics.widthPixels;
//        float screenHeight = metrics.heightPixels;
        Rect appRect = new Rect();
        Window window = game.getApplyWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(appRect);
        int screenWidth = appRect.width();
        int screenHeight = appRect.height();
        FrumViewWidth = screenWidth * screen_FACTOR;
        FrumViewHeight = screenHeight * screen_FACTOR;
        roattDis = new Vector2f(FrumViewWidth*0.083f, 0);
        moveDis = new Vector2f();
    }

    //设置最终的大小
    public void setEndScale(float viewXScale, float viewYScale) {
        this.viewXScale = viewXScale;
        this.viewYScale = viewYScale;
    }

    public void setCallActi(CallBackBook callBackBook) {
        this.callBackBook = callBackBook;
    }
    public interface CallBackBook {
        void reMain();
    }
}

