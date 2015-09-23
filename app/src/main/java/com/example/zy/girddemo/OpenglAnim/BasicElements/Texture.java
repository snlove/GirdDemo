package com.example.zy.girddemo.OpenglAnim.BasicElements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;


import com.example.zy.girddemo.OpenglAnim.OpenglUtil.FileIO;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Game;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/8/28.
 */
public class Texture {

    private String filename;
    private int textureId;
    private int[] textureIds;
    int minFliter;
    int magFliter;
    FileIO fileIO;
    private float width;
    private float height;
    private int bookId;
    //封装了画笔和GLSufaceView
    GLGraphics glGraphics;
    Game game;
    public Texture(Game game,String filename) {
        this.filename = filename;
        this.game = game;
        glGraphics = game.getGraphics();
    }

    public Texture(Game game, int bookId) {
        this.game = game;
        this.bookId = bookId;
        glGraphics = game.getGraphics();
    }

    public  int loadTexture() {
        GL10 gl = glGraphics.getGl();
        textureIds = new int[1];
        InputStream in = null;
        try {
            fileIO = game.getFileIO();
            in = fileIO.readAsset("ddddd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(game.getContext().getResources(), bookId);
        if (bitmap == null) {
            LogMes.d("TAG", "=========bitmap is null,the file is error");
        } else {
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
            LogMes.d("Bitmap","========="+ width +" "+  height);
        }
        gl.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        bindTexture();
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        setFliters(GL10.GL_NEAREST, GL10.GL_NEAREST);
        gl.glBindTexture(GL10.GL_TEXTURE_2D,0);
        return  textureId;
    }

    public InputStream getFile() {
        InputStream in = null;
        try {
            in = fileIO.readAsset(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  in;
    }

    public  void setFliters(int minFliter, int magFliter) {
        this.magFliter = magFliter;
        this.minFliter = minFliter;
         GL10 gl = glGraphics.getGl();
         gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,minFliter);
         gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,magFliter);
    }

    public void bindTexture() {
        GL10 gl= glGraphics.getGl();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);
    }

    public void reload() {
        loadTexture();
        bindTexture();
        setFliters(minFliter,magFliter);
        glGraphics.getGl().glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }

    public  void dispose() {
        GL10 gl = glGraphics.getGl();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        gl.glDeleteTextures(1,textureIds,0);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
