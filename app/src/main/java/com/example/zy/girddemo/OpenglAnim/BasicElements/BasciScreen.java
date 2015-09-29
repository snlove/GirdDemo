package com.example.zy.girddemo.OpenglAnim.BasicElements;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;


import com.example.zy.girddemo.OpenglAnim.OpenglUtil.BasicUtil.AndroidFileIO;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.BasicUtil.AndroidInput;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.BasicUtil.FileIO;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Game;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public abstract class BasciScreen extends Activity implements Game, Renderer {
    enum GLState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    GLSurfaceView glView;
    GLGraphics glGraphics;
    GLGraphics.Input input;
    FileIO fileIO;
    Screen screen;
    GLState state = GLState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);

       // 设置glsurfaceView alpha
        glView.setZOrderOnTop(true);
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glView.setRenderer(this);
        setContentView(glView);

        glGraphics = new GLGraphics(glView);
        fileIO = new AndroidFileIO(getAssets());
        input = new AndroidInput(this, glView, 1, 1);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");

        context = this.getBaseContext();

    }

    public void onResume() {
        super.onResume();
        glView.onResume();
        wakeLock.acquire();
    }

    /**
     * 绘画开始时的设置，一般进行一些初始化操作，只使用一次的
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGl(gl);
        synchronized (stateChanged) {
            if (state == GLState.Initialized)
                screen = getStartScreen();
            state = GLState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    //屏幕发生改变时使用
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        GLState state = null;

        synchronized (stateChanged) {
            state = this.state;
        }

        if (state == GLState.Running) {
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            screen.update(deltaTime);
            screen.present(deltaTime);
        }

        if (state == GLState.Paused) {
            screen.pause();
            synchronized (stateChanged) {
                this.state = GLState.Idle;
                stateChanged.notifyAll();
            }
        }

        if (state == GLState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized (stateChanged) {
                this.state = GLState.Idle;
                stateChanged.notifyAll();
            }
        }
    }

    @Override
    public void onPause() {
        synchronized (stateChanged) {
            if (isFinishing())
                state = GLState.Finished;
            else
                state = GLState.Paused;
            while (true) {
                try {
                    stateChanged.wait();
                    break;
                } catch (InterruptedException e) {
                }
            }
        }
        wakeLock.release();
        glView.onPause();
        super.onPause();
    }



    @Override
    public GLGraphics.Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public GLGraphics getGraphics() {
        if (glGraphics != null) {
            return glGraphics;
        } else
            throw new IllegalStateException("We are using OpenGL!");
    }


    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public Context getContext() {
        return context;
    }


}
