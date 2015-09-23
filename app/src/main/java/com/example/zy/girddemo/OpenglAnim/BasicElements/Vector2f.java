package com.example.zy.girddemo.OpenglAnim.BasicElements;

/**
 * Created by zy on 2015/9/9.
 */
public class Vector2f {
    public float x;
    public float y;
    public static float RadiustToDegree = (float) (360 / Math.PI);
    public static float DegreeToRadius = (float) (Math.PI / 360);

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {

    }

    public Vector2f(Vector2f other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void setVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setVector2f(Vector2f other) {
        this.x = other.x;
        this.x = other.y;
    }

    //创建一个副本，方便进行操作
    public Vector2f copy() {
        return new Vector2f(x, y);
    }

    public Vector2f add(float x, float y) {
        this.x = this.x + x;
        this.y = this.y + y;
        return this;
    }

    public Vector2f add(Vector2f other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2f mul(float x, float y) {
        this.x = this.x * x;
        this.y = this.y * y;
        return this;
    }

    public Vector2f mul(Vector2f other) {
        this.x = this.x * other.x;
        this.y = this.y * other.y;
        return this;
    }

    public Vector2f mul(float scale) {
        this.x = this.x * scale;
        this.y = this.y * scale;
        return this;
    }


    public Vector2f sub(float x, float y) {
        this.x = this.x - x;
        this.y = this.y - y;
        return this;
    }

    public Vector2f sub(Vector2f other) {
        this.x = this.x - other.x;
        this.y = this.y - other.y;
        return this;
    }

    //计算得到其长度
    public float len() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    //计算得到单位向量
    public Vector2f nor() {
        float len = len();
        Vector2f nor = new Vector2f();
        if (len != 0) {
            nor.x = this.x / len;
            nor.y = this.y / len;
        }
        return nor;
    }

    //得到向量于x轴的夹角
    public float angle() {
        float angle = (float) (Math.atan2(this.y,this.x) * RadiustToDegree);
        if (angle < 0) {
            angle = angle + 360;
        }
        return  angle;
    }

    public Vector2f roate(float angle) {
        float road = angle * DegreeToRadius;
        float cos = (float) Math.cos(road);
        float sin = (float) Math.sin(road);
        this.x = this.x * cos - this.y * sin;
        this.y = this.x * sin - this.y * cos;
        return  this;
    }

    //计算两个向量之间的距离，参数为起点
    public float dis(float x, float y) {
        this.x = this.x - x;
        this.y = this.y - y;
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float dis(Vector2f other) {
        this.x = this.x - other.x;
        this.y = this.y - other.y;
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    //增加距离的平方，提高效率
    public float disSqure(float x, float y) {
        this.x = this.x - x;
        this.y = this.y - y;
        return  this.x * this.x + this.y * this.y;
    }

    public float disSqure(Vector2f other) {
        this.x = this.x - other.x;
        this.y = this.y - other.y;
        return  this.x * this.x + this.y * this.y;
    }

}
