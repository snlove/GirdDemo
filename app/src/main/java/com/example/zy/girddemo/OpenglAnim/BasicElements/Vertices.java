package com.example.zy.girddemo.OpenglAnim.BasicElements;



import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zy on 2015/9/1. 把相关的点都放在一个数组了，根据位置进行索引
 */
public class Vertices   {

    private final IntBuffer verticesbuff;
    private final ShortBuffer indexsBuff;
    private int[] tempBuff;
    private final boolean hasColor;
    private final boolean hasTexCoords;
    private final GLGraphics glGraphics;
    final  int vrtexSize;
    private  int numberSize;


    public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndeices, boolean hasColor, boolean hasTexCoords) {
        this.glGraphics = glGraphics;
        this.hasColor = hasColor;
        this.hasTexCoords = hasTexCoords;
        this.vrtexSize = (2 + (hasColor ? 4 :0) + (hasTexCoords ? 2 :0)) * 4;

        //初始化相关缓冲数据
        ByteBuffer vbb = ByteBuffer.allocateDirect(maxVertices * vrtexSize);
        vbb.order(ByteOrder.nativeOrder());
        verticesbuff = vbb.asIntBuffer();

        if (maxIndeices > 0) {
            ByteBuffer ibb = ByteBuffer.allocateDirect(maxIndeices * Short.SIZE / 8);
            ibb.order(ByteOrder.nativeOrder());
            indexsBuff = ibb.asShortBuffer();
        } else  {
            indexsBuff = null;
        }

        this.tempBuff = new int[maxVertices * vrtexSize / 4];
    }


    public void setVerticesbuff(float[] vertices,int offset,int length) {
        this.verticesbuff.clear();
        int len = offset + length;
        for (int i = offset, j = 0; i < len; i++, j++) {
            tempBuff[j] = Float.floatToRawIntBits(vertices[i]);
            verticesbuff.put(tempBuff, 0, length);
            verticesbuff.flip();
        }

    }

    public void setIndexsBuff(short[] indesx, int offset, int length) {
        indexsBuff.clear();
        indexsBuff.put(indesx, offset, length);
        indexsBuff.flip();
        numberSize = indesx.length;
    }

    public void draw(int primitvtType, int offset, int numVertices) {
        GL10 gl = glGraphics.getGl();

        if (indexsBuff != null) {
            gl.glDrawElements(primitvtType,numVertices,GL10.GL_UNSIGNED_SHORT,indexsBuff);

        } else {
            gl.glDrawArrays(primitvtType, offset, numVertices);
        }

      LogMes.d("TAG", "----------顶点渲染方法");

    }

    //进行性能优化，避免重复多次调用
    public void bind() {
        GL10 gl = glGraphics.getGl();

        verticesbuff.position(0);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2,GL10.GL_FLOAT,vrtexSize,verticesbuff);

        if (hasColor) {
            verticesbuff.position(2);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, vrtexSize, verticesbuff);
        }
        if (hasTexCoords) {
            verticesbuff.position(hasColor ? 6 : 2);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, vrtexSize, verticesbuff);
        }
    }

    public void unBind() {
        GL10  gl = glGraphics.getGl();
        gl.glDisable(GL10.GL_VERTEX_ARRAY);
        if (hasColor) {
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        }

        if (hasTexCoords) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
    }

    public int  getnumberSize() {
        return  numberSize;
    }
}
