package com.sky_wf.chinachat.chat.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sky_wf.chinachat.activity.ChatActivity;
import com.sky_wf.chinachat.chat.utils.ChatConstants;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.Utils;

import okhttp3.internal.Util;

/**
 * @Date : 2018/6/11
 * @Author : WF
 * @Description : PaseteEditText is extended EditText which is used to paste the content
 */
public class PaseteEditText extends EditText
{
    private Context context;

    public PaseteEditText(Context context)
    {
        super(context);
        this.context = context;
    }

    public PaseteEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public PaseteEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public boolean onTextContextMenuItem(int id)
    {
        if (id == android.R.id.paste)
        {
            String txt = Utils.getCopyText();
            if (txt.startsWith(Constansts.COPY_IMAGE))
            {
                txt = txt.replace(Constansts.COPY_IMAGE, "");
                Intent intent = new Intent(context, AlertDialog.class);
                String string = "发送以下图片？";
                intent.putExtra("title", string);
                intent.putExtra("forwardImage", txt);
                intent.putExtra("cancel", true);
                ((Activity) context).startActivityForResult(intent,
                        ChatConstants.REQUEST_CODE_COPY_AND_PASTE);

            }
        }
        return super.onTextContextMenuItem(id);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(!TextUtils.isEmpty(text)&&text.toString().startsWith(Constansts.COPY_IMAGE))
        {
            setText("");
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
