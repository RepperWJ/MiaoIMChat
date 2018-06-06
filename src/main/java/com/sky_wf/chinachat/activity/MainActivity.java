package com.sky_wf.chinachat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.base.BaseFragmentActivity;
import com.sky_wf.chinachat.activity.fragment.Fragment_Discover;
import com.sky_wf.chinachat.activity.fragment.Fragment_Friends;
import com.sky_wf.chinachat.activity.fragment.Fragment_Msg;
import com.sky_wf.chinachat.activity.fragment.Fragment_Porfile;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.Debugger;



public class MainActivity extends BaseFragmentActivity
{
    private Fragment[] fragments;
    private Fragment_Msg msg_fragment;
    private Fragment_Friends friend_fragment;
    private Fragment_Discover discover_fragment;
    private Fragment_Porfile profile_fragment;
    private TextView unreadMsgLable;
    private TextView unreadAdressLable;
    private TextView unreadFindLable;
    private TextView unreadProfileLable;
    private ImageView[] imageBottom;// 底部img
    private TextView[] txtBottom;//底部txt
    private TextView txt_title;
    private ImageView img_right;

    private int currentTabIndex = 0;// 当前Fragment的index
    private int index  = 0;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debugger.d(TAG, ">>onCreate<<");
        findViewById();
        initViews();
        initTabView();
    }

    private void initViews() {
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.icon_add);
        txt_title.setVisibility(View.VISIBLE);
    }

    private void findViewById()
    {
        img_right = (ImageView)findViewById(R.id.img_right);
        txt_title = (TextView)findViewById(R.id.txt_left);
    }

    private void initTabView() {
        unreadMsgLable = (TextView)findViewById(R.id.unread_msg_number);
        unreadAdressLable = (TextView)findViewById(R.id.unread_friend_number);
        unreadFindLable = (TextView)findViewById(R.id.unread_discover_number);
        unreadProfileLable = (TextView)findViewById(R.id.unread_profile_number);
        msg_fragment = new Fragment_Msg();
        friend_fragment = new Fragment_Friends();
        discover_fragment = new Fragment_Discover();
        profile_fragment = new Fragment_Porfile();
        fragments = new Fragment[]{msg_fragment,friend_fragment,discover_fragment,profile_fragment};
        imageBottom = new ImageView[4];
        imageBottom[0] = (ImageView)findViewById(R.id.img_chinachat);
        imageBottom[1] = (ImageView)findViewById(R.id.img_friend);
        imageBottom[2] = (ImageView)findViewById(R.id.img_discover);
        imageBottom[3] = (ImageView)findViewById(R.id.img_profile);
        imageBottom[0].setSelected(true);
        
        txtBottom = new TextView[4];
        txtBottom[0] = (TextView)findViewById(R.id.txt_chinachat);
        txtBottom[1] = (TextView)findViewById(R.id.txt_friend);
        txtBottom[2] = (TextView)findViewById(R.id.txt_discover);
        txtBottom[3] = (TextView)findViewById(R.id.txt_profile);
        txtBottom[0].setTextColor(0xFF45C01A);
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container,msg_fragment)
                .add(R.id.frame_container,friend_fragment)
                .add(R.id.frame_container,discover_fragment)
                .add(R.id.frame_container,profile_fragment)
                .hide(friend_fragment)
                .hide(profile_fragment)
                .hide(discover_fragment)
                .show(msg_fragment)
                .commit();



    }

    /**根据点击，呈现对应的Fragment
     * @param view
     */
    public  void OnTabClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bottom_chinachat:
                index = Constansts.index_Msg;
                txt_title.setText("喵信");
                break;
            case R.id.bottom_friend:
                index = Constansts.index_Friend;
                break;
            case R.id.bottom_discover:
                index = Constansts.index_Discover;
                break;
            case R.id.bottom_profile:
                index = Constansts.index_Profile;
                break;
        }
        if(currentTabIndex!=index)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(fragments[currentTabIndex]);
            if(!fragments[index].isAdded())
            {
                transaction.add(R.id.frame_container,fragments[index]);
            }
            transaction.show(fragments[index]);
            transaction.commit();
        }
        imageBottom[currentTabIndex].setSelected(false);
        imageBottom[index].setSelected(true);
        txtBottom[currentTabIndex].setTextColor(0xFF999999);
        txtBottom[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;

    }

    @Override
    protected void onStop() {
        super.onStop();
        Debugger.d(TAG, ">>onStop<<");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debugger.d(TAG, ">>onDestroy<<");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode())
        {
            case KeyEvent.KEYCODE_BACK:
//                FragmentManager manager = getSupportFragmentManager();
//                manager.popBackStack();
//                return true;
                MyApplication.exitActivity();

        }
        return super.onKeyDown(keyCode, event);
    }
}
