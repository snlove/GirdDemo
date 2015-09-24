package com.example.zy.girddemo.OpenglAnim.Anim;

import android.content.Intent;
import android.view.KeyEvent;

import com.example.zy.girddemo.MainActivity;
import com.example.zy.girddemo.OpenglAnim.BasicElements.BasciScreen;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;
import com.example.zy.girddemo.R;

/**
 * Created by zy on 2015/9/22.
 */
public class ExitActivity extends BasciScreen implements ExitScreen.CallBackBook{
    private  ExitScreen exitScreen;
    @Override
    public Screen getStartScreen() {
        Intent i = getIntent();
        int imagePostion = i.getExtras().getInt("BookId");
        exitScreen = new ExitScreen(this,imagePostion);
        exitScreen.setCallActi(this);
        return exitScreen;
    }


    @Override
    public void reMain() {
        Intent i = new Intent(ExitActivity.this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.enter_main_again,R.anim.exit_exit);
        ExitActivity.this.finish();
        LogMes.d("TESTScreen", "==============Exti the Screen");
    }
}
