package com.example.zy.girddemo.OpenglAnim.Anim;

import android.content.Intent;
import android.view.KeyEvent;

import com.example.zy.girddemo.OpenglAnim.BasicElements.BasciScreen;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

/**
 * Created by zy on 2015/9/22.
 */
public class EnterActivity extends BasciScreen {
    private  int imagerPostion;
    @Override
    public Screen getStartScreen() {
        Intent receiveId = getIntent();
        imagerPostion = receiveId.getExtras().getInt("bookCoverId");
        LogMes.d("BOOKTAG", "=====================" + imagerPostion);
        return new RectAngle(this,imagerPostion);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.putExtra("BookId", imagerPostion);
            intent.setClass(EnterActivity.this, ExitActivity.class);
            startActivity(intent);
            EnterActivity.this.finish();
        }
        return false;
    }
}
