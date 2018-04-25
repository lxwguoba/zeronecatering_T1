package com.zerone.catering.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.zerone.catering.R;


public class UIUtil {


	/**
	 * �˳�ϵͳ��ȷ����ʾ
	 */
	public static void existPrompt(final Activity activity){
		final AlertDialog dialog = new AlertDialog.Builder(activity).create();
		dialog.show();
		Window win = dialog.getWindow();
		win.setContentView(R.layout.dialog_confirm);
		TextView title = (TextView) win.findViewById(R.id.dialog_title);
		title.setText("��ܰ��ʾ");
		TextView context = (TextView) win.findViewById(R.id.dialog_context);
		context.setText("��ȷ���Ƿ���Ҫ�˳�����!");
		Button okBtn = (Button) win.findViewById(R.id.dialog_btn_ok);
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				activity.startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		Button cancelBtn = (Button) win.findViewById(R.id.dialog_btn_cancel);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
}
