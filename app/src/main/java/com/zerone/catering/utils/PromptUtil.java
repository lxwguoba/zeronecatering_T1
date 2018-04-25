package com.zerone.catering.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zerone.catering.R;

public class PromptUtil {

	/**
	 * �
	 * 
	 * @author yzyue
	 * 
	 */
	public interface PromptOk {
		public void click();
	}

	/**
	 * �
	 * 
	 * @author yzyue
	 * 
	 */
	public interface PromptCancel {
		public void click();
	}

	private Activity activity;
	private PromptOk ok;
	private PromptCancel cancel;
	private AlertDialog dialog;

	public PromptUtil(Activity activity, PromptOk ok, PromptCancel cancel) {
		super();
		this.activity = activity;
		this.ok = ok;
		this.cancel = cancel;
	}

	/**
	 * ������ʾ��
	 * @param titleText ����
	 * @param contentText ����
	 * @param okText
	 * @param cancelText
	 */
	public void show(String titleText, String contentText, String okText, String cancelText) {
		dialog = new AlertDialog.Builder(activity).create();
		dialog.show();
		Window win = dialog.getWindow();
		win.setContentView(R.layout.dialog_confirm);
		TextView title = (TextView) win.findViewById(R.id.dialog_title);
		title.setText(titleText);
		TextView context = (TextView) win.findViewById(R.id.dialog_context);
		contentText = contentText + "                                                                ";
		context.setText(contentText);
		Button okBtn = (Button) win.findViewById(R.id.dialog_btn_ok);
		okBtn.setText(okText);
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (ok != null) {
					ok.click();
				}
			}
		});
		Button cancelBtn = (Button) win.findViewById(R.id.dialog_btn_cancel);
		cancelBtn.setText(cancelText);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (cancel != null) {
					cancel.click();
				}
			}
		});
	}

}
