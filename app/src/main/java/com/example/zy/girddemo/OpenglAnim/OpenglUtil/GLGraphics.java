package com.example.zy.girddemo.OpenglAnim.OpenglUtil;

import android.opengl.GLSurfaceView;
import android.view.View;

import com.example.zy.girddemo.OpenglAnim.OpenglUtil.BasicUtil.Pool;

import java.util.ArrayList;
import java.util.List;

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

    public static interface Input {

        public static class KeyEvent {
            public static final int KEY_DOWN = 0;
            public static final int KEY_UP = 1;

            public int type;
            public int keyCode;
            public char keyChar;

            public String toString() {
                StringBuilder builder = new StringBuilder();
                if (type == KEY_DOWN)
                    builder.append("key down, ");
                else
                    builder.append("key up, ");
                builder.append(keyCode);
                builder.append(",");
                builder.append(keyChar);
                return builder.toString();
            }
        }

        public static class TouchEvent {
            public static final int TOUCH_DOWN = 0;
            public static final int TOUCH_UP = 1;
            public static final int TOUCH_DRAGGED = 2;

            public int type;
            public int x, y;
            public int pointer;

            public String toString() {
                StringBuilder builder = new StringBuilder();
                if (type == TOUCH_DOWN)
                    builder.append("touch down, ");
                else if (type == TOUCH_DRAGGED)
                    builder.append("touch dragged, ");
                else
                    builder.append("touch up, ");
                builder.append(pointer);
                builder.append(",");
                builder.append(x);
                builder.append(",");
                builder.append(y);
                return builder.toString();
            }
        }

        public boolean isKeyPressed(int keyCode);

        public boolean isTouchDown(int pointer);

        public int getTouchX(int pointer);

        public int getTouchY(int pointer);

        public float getAccelX();

        public float getAccelY();

        public float getAccelZ();

        public List<KeyEvent> getKeyEvents();

        public List<TouchEvent> getTouchEvents();
    }

    public static class KeyboardHandler implements View.OnKeyListener {
        boolean[] pressedKeys = new boolean[128];
        Pool<Input.KeyEvent> keyEventPool;
        List<Input.KeyEvent> keyEventsBuffer = new ArrayList<Input.KeyEvent>();
        List<Input.KeyEvent> keyEvents = new ArrayList<Input.KeyEvent>();

        public KeyboardHandler(View view) {
            Pool.PoolObjectFactory<Input.KeyEvent> factory = new Pool.PoolObjectFactory<Input.KeyEvent>() {
                @Override
                public Input.KeyEvent createObject() {
                    return new Input.KeyEvent();
                }
            };
            keyEventPool = new Pool<Input.KeyEvent>(factory, 100);
            view.setOnKeyListener(this);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }

        @Override
        public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
            if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
                return false;

            synchronized (this) {
                Input.KeyEvent keyEvent = keyEventPool.newObject();
                keyEvent.keyCode = keyCode;
                keyEvent.keyChar = (char) event.getUnicodeChar();
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                    keyEvent.type = Input.KeyEvent.KEY_DOWN;
                    if(keyCode > 0 && keyCode < 127)
                        pressedKeys[keyCode] = true;
                }
                if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                    keyEvent.type = Input.KeyEvent.KEY_UP;
                    if(keyCode > 0 && keyCode < 127)
                        pressedKeys[keyCode] = false;
                }
                keyEventsBuffer.add(keyEvent);
            }
            return false;
        }

        public boolean isKeyPressed(int keyCode) {
            if (keyCode < 0 || keyCode > 127)
                return false;
            return pressedKeys[keyCode];
        }

        public List<Input.KeyEvent> getKeyEvents() {
            synchronized (this) {
                int len = keyEvents.size();
                for (int i = 0; i < len; i++)
                    keyEventPool.free(keyEvents.get(i));
                keyEvents.clear();
                keyEvents.addAll(keyEventsBuffer);
                keyEventsBuffer.clear();
                return keyEvents;
            }
        }
    }
}
