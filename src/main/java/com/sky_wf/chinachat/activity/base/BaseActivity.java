package com.sky_wf.chinachat.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sky_wf.chinachat.utils.Utils;

/**
 * @Date :  2018/5/15
 * @Author : WF
 * @Description :
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        setListener();
    }

    public void startTargetActivity(Activity activity)
    {
        Intent intent = new Intent(this,activity.getClass());
        startActivity(intent);
    }

    protected abstract void initTitle();

    protected abstract void setListener();

    protected boolean isNetAvaliable()
    {
        return Utils.isNetAvaliable(this);
    }


}
