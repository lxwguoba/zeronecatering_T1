package com.zerone.catering.handler;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zerone.catering.action.ckeckupdataapp.InitUpdateActivity;
import com.zerone.catering.constant.Constant;
import com.zerone.catering.utils.LogUtil;
import com.zerone.catering.utils.PromptUtil;

/**
 * APP更新
 *
 * @date 2017年9月14日
 * @author  liuxignwen
 *
 */
public class UpdateHandler extends Handler {

	/**
	 * 操作类型-检测是否需要更新
	 */
	public static final int TYPE_UPDATE = 1000;

	private Activity activity;
	private String url ;
	private String version;
	public UpdateHandler(Activity activity,String version,String url) {
		super();
		this.activity = activity;
		this.version=version;
		this.url=url;
	}

	/**
	 * 处理消息
	 */
	@Override
	public void handleMessage(final Message msg) {
		try {
			super.handleMessage(msg);
			if (msg.what == UpdateHandler.TYPE_UPDATE) {
				String clientVersion = Constant.app_version;// APP的版本号
//				Log.i("URL","当前APP的版本号:" + clientVersion);
				String[] str_arr=clientVersion.split("");
				if (version.equals(clientVersion)){
//					LogUtil.info("版本号一致,无需更新");
					return;
				}
//				LogUtil.error("版本号不一致,需要更新");
				PromptUtil prompt = new PromptUtil(activity, new InitUpdateActivity(activity, version,url), null);
				prompt.show("温馨提醒", "发现最新版本[" +  version + "],是否需要升级?", "现在升级", "下次再说");
			}else if (msg.what==100){

			}
		} catch (Exception e) {
			LogUtil.error("查询服务器版本失败", e);
		}
	}
}
