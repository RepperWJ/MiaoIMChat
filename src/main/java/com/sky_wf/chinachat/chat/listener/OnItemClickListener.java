package com.sky_wf.chinachat.chat.listener;

import android.view.View;

/**
 * @Date :  2018/4/19
 * @Author : WF
 * @Description :
 */
public interface OnItemClickListener {
    void onClick(View view,int position);
    void onLongClick(View view,int position);
}
