package com.example.zy.girddemo.OpenglAnim.Anim;

import android.content.Intent;
import android.os.Message;

import com.example.zy.girddemo.MainActivity;
import com.example.zy.girddemo.OpenglAnim.BasicElements.BasciScreen;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

/**
 * Created by zy on 2015/9/22.
 */
public class ExitActivity extends BasciScreen implements ExitScreen.CallBackBook{
    private  ExitScreen exitScreen;
    @Override
    public Screen getStartScreen() {
        Intent i = getIntent();
        int imagePostion = i.getExtras().getInt("BookId");
        float[] scale = i.getExtras().getFloatArray("Scale");
        float[] firstPos = i.getExtras().getFloatArray("FirstPos");
        exitScreen = new ExitScreen(this,imagePostion);
        if(scale != null) {
            exitScreen.setEndScale(scale[0], scale[1]);
        }
        if(firstPos!=null) {
            exitScreen.setEndPostion(firstPos[0], firstPos[1]);
        }
        exitScreen.setCallActi(this);
        return exitScreen;
    }


    @Override
    public void reMain() {
        Intent i = new Intent(ExitActivity.this,MainActivity.class);
        startActivity(i);
        ExitActivity.this.finish();
        overridePendingTransition(0,0);
        LogMes.d("TESTScreen", "==============Exti the Screen");
    }
}
