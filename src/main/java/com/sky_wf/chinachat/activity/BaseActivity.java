package com.sky_wf.chinachat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @Date :  2018/5/7
 * @Author : WF
 * @Description :
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startTargetActivity(Activity activity)
    {
        Intent intent = new Intent(this,activity.getClass());
        startActivity(intent);
    }
}
