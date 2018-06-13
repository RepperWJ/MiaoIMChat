package com.sky_wf.chinachat.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

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

    public void hideKeyBoard()
    {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        {
            if(getCurrentFocus()!=null)
            {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


}
