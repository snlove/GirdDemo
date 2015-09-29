package com.example.zy.girddemo.OpenglAnim.Anim;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.zy.girddemo.OpenglAnim.BasicElements.BasciScreen;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

/**
 * Created by zy on 2015/9/22.
 */
public class EnterActivity extends BasciScreen {
    private  int imagerPostion;
    private EnterScreen enterScreen;
    @Override
    public Screen getStartScreen() {
        Intent receiveId = getIntent();
        imagerPostion = receiveId.getExtras().getInt("bookCoverId");
        Bundle b = receiveId.getBundleExtra("Postion");
        float[] postion = b.getFloatArray("PostionScale");
        LogMes.d("BOOKTAG", "=====================" + imagerPostion);
        enterScreen = new EnterScreen(this,imagerPostion);
        enterScreen.setEnterPos(postion[0],postion[1]);
        return enterScreen;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.putExtra("BookId", imagerPostion);
            intent.setClass(EnterActivity.this, ExitActivity.class);
            startActivity(intent);
            EnterActivity.this.finish();
            overridePendingTransition(0,0);
//            ExitScreen exitScreen = new ExitScreen(this,imagerPostion);
//            this.setScreen(exitScreen);
            LogMes.d("TESTScreen", "==============Enter the Screen");
        }
        return false;
    }
}
