package com.android.framework.core.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.framework.R;

/**
 * 自定义处理对话框.
 * 
 * @author John
 * 
 */
public class ProgressDialog extends BaseDialog {
	/**
	 * 信息栏组件.
	 */
	TextView tvMessage;

	/**
	 * 处理栏组件.
	 */
	ProgressBar pbLoading;

	public ProgressDialog(Context context) {
		super(context);// , R.style.ProgressDialog
	}

	protected ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 设置相关信息
	 */
	public void setMessage(CharSequence message) {
		tvMessage.setText(message);
	}

	public void setMessage(int id) {
		tvMessage.setText(id);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.dialog_progress;
	}

	@Override
	protected void onFindViews() {
		// 去掉标题
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setGravity(Gravity.CENTER);

		pbLoading = (ProgressBar) findViewById(android.R.id.progress);
		tvMessage = (TextView) findViewById(android.R.id.message);
	}

	@Override
	protected void onInitViewData() {
		
	}
	
	@Override
	public void initWindowLayoutParams() {

		// 设置对话框透明度和宽高
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		Window window = getWindow();
		LayoutParams attributes = window.getAttributes();
		attributes.alpha = 0.7f;
		attributes.width = displayMetrics.widthPixels / 2;
		attributes.height = attributes.width;
		window.setAttributes(attributes);
		setCanceledOnTouchOutside(false);
	}
}
