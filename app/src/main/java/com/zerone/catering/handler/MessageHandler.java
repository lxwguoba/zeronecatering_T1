package com.zerone.catering.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zerone.catering.R;
/**
 * �������������ֹͣ
 * 
 * @author yzyue
 * 
 */
public class MessageHandler extends Handler {

	protected ProgressDialog progress;
	protected Activity activity;


	public static final int PROGRESS_STOP = 0;

	public static final int PROGRESS_MESSAGE = 1;

	public static final int SHOW_PROGRESS = 4;
	public static final int TIP = 10;

	public MessageHandler(Activity activity) {
		super();
		this.activity = activity;
	}

	public MessageHandler(ProgressDialog progress, Activity activity) {
		super();
		this.progress = progress;
		this.activity = activity;
	}

	/**
	 * �
	 * @param progress
	 * @param activity
	 * @param termType
	 * @return
	 * @throws Exception
	 */
	public static MessageHandler buildMessageHandler(ProgressDialog progress, Activity activity, String termType) throws Exception {
		MessageHandler messageHandler = null;
		return messageHandler;
	}

	@Override
	public void handleMessage(final Message msg) {
		try {
			switch (msg.what) {
			case SHOW_PROGRESS:// ��ʾ������
				if (progress != null) {
					this.progress.setMessage((String) msg.obj);
					this.progress.show();
				}
				break;
			case PROGRESS_STOP://
				if (progress != null) {
					this.progress.dismiss();
				}
				break;
			case PROGRESS_MESSAGE://
				try {
					if (progress != null) {
						this.progress.setMessage((String) msg.obj);
						this.progress.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case TIP:// ��
				final AlertDialog dialog = new AlertDialog.Builder(activity).create();
				dialog.show();
				Window win = dialog.getWindow();
				win.setContentView(R.layout.dialog_tip);
				TextView context = (TextView) win.findViewById(R.id.dialog_context);
				context.setText((String) msg.obj + "                                ");
				Button okBtn = (Button) win.findViewById(R.id.dialog_btn_ok);
				okBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		} catch (Exception e) {
			Log.e("URL","更新时出现的问题："+e);
		}
	}

}
