package com.sky_wf.chinachat.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.base.BaseFragment;
import com.sky_wf.chinachat.utils.Debugger;

/**
 * @Date :  2018/5/14
 * @Author : WF
 * @Description :发现
 */
public class Fragment_Discover extends BaseFragment {
    private final String TAG = "Fragment_Discover";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Debugger.d(TAG,"<<onAttach>>");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debugger.d(TAG,"<<onCreate>>");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Debugger.d(TAG,"<<onCreateView>>");
        View view = inflater.inflate(R.layout.fragment_discover,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Debugger.d(TAG,"<<onActivityCreated>>");
    }

    @Override
    public void onStart() {
        super.onStart();
        Debugger.d(TAG,"<<onStart>>");
    }

    @Override
    public void onResume() {
        super.onResume();
        Debugger.d(TAG,"<<onResume>>");
    }

    @Override
    public void onStop() {
        super.onStop();
        Debugger.d(TAG,"<<onStop>>");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Debugger.d(TAG,"<<onDestroyView>>");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Debugger.d(TAG,"<<onDestroy>>");
    }

    @Override
    protected void initView() {

    }
}
