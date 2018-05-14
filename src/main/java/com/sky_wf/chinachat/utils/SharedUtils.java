package com.sky_wf.chinachat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @Date :  2018/5/3
 * @Author : WF
 * @Description :SharedPreferences工具类
 *
 */
public class SharedUtils {
    private  static SharedUtils instance  = null;
    private SharedPreferences preferences ;
    private SharedPreferences.Editor editor;
    private final String FILE_NAME = "default";
    private final String TAG = "SharedUtils";
    private Context context;

    public static SharedUtils getInstance(Context context)
    {
        if(null==instance)
        {
            instance = new SharedUtils();
            instance.getDefaultPreferences(context);
        }
        return instance;
    }

    public SharedPreferences getDefaultPreferences(Context context)
    {
          preferences = PreferenceManager.getDefaultSharedPreferences(context);
          editor = preferences.edit();
        return preferences;
    }

    public SharedPreferences getActivityPreferences(Activity activity)
    {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        return preferences;
    }

    public SharedPreferences getSharedPreferences(Context context,String fileName)
    {
        if(null==fileName||fileName.length()==0)
        {
            fileName = FILE_NAME;
        }
        preferences = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        editor = preferences.edit();
        return preferences;
    }


    public void putInt(String key,int value)
    {
        editor.putInt(key,value);
        editor.commit();
    }

    public void putBoolean(String key,boolean value)
    {
        editor.putBoolean(key,value);
        editor.commit();
    }

    public void putString(String key,String value)
    {
        editor.putString(key,value);
        editor.commit();
    }

   public int getInt(String key)
   {
       return preferences.getInt(key,0);
   }

    public boolean getBoolean(String key)
    {
        return preferences.getBoolean(key,false);
    }

    public String getString(String key)
    {
        return preferences.getString(key,"");
    }

    public boolean isExist(String key)
    {
        return preferences.contains(key);
    }

    public void remove(String key)
    {
        if(isExist(key))
        {
            editor.remove(key);
            boolean isResult =editor.commit();
            Debugger.d(TAG,"--<<SharedUtils remove>>--"+isResult);

        }
    }

    public void clear()
    {
        if(editor!=null)
        {
            editor.clear();
        }
    }

    public void commit()
    {
        if(editor!=null)
        {
            editor.commit();
        }
    }




}
