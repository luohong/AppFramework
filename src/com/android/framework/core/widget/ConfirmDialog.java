package com.android.framework.core.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.framework.R;

public class ConfirmDialog extends BaseDialog {
	public static final String CONTENT = "content";

	public static final String TITLE = "title";

	public static Bundle getBundle(String title, String content) {
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		bundle.putString(CONTENT, content);
		return bundle;
	}

	private String confirmMsg;

	private View.OnClickListener onOKListener;
	private View.OnClickListener onCancelListener;

	private TextView tvMsg;

	private Button btnCancel;

	private Button btnOK;

	private String cancelText;

	private String okText;

	private boolean cancelEnable;

	public ConfirmDialog(Context context) {
		super(context);
		init(null, true, "取消", "确定", null);
	}

	public ConfirmDialog(Context context, String confirmMsg, boolean cancelEnable, String cancelText, String okText, View.OnClickListener onOKListener) {
		super(context);
		init(confirmMsg, cancelEnable, cancelText, okText, onOKListener);
	}

	public ConfirmDialog(Context context, String confirmMsg, boolean cancelEnable, String cancelText, String okText, View.OnClickListener onOKListener,
			View.OnClickListener cancelListener) {
		super(context);
		init(confirmMsg, cancelEnable, cancelText, okText, onOKListener, cancelListener);
	}

	public ConfirmDialog(Context context, String confirmMsg, String cancelText, String okText, View.OnClickListener onOKListener) {
		super(context);
		init(confirmMsg, true, cancelText, okText, onOKListener);
	}

	public ConfirmDialog(Context context, String confirmMsg, View.OnClickListener onOKListener) {
		super(context);
		init(confirmMsg, true, "取消", "确定", onOKListener);
	}

	protected void btnCancel(View v) {
		if (onCancelListener != null) {
			onCancelListener.onClick(v);
		}
		dismiss();
	}

	protected void btnOK(View v) {
		if (onOKListener != null) {
			onOKListener.onClick(v);
		}
		dismiss();
	}

	protected int getLayoutId() {
		return R.layout.confirm_dialog;
	}

	public void init(String confirmMsg, boolean cancelEnable, String cancelText, String okText, View.OnClickListener onOKListener) {
		this.cancelEnable = cancelEnable;
		this.confirmMsg = confirmMsg;
		this.cancelText = cancelText;
		this.okText = okText;
		this.onOKListener = onOKListener;
	}

	protected void init(String confirmMsg, boolean cancelEnable, String cancelText, String okText, View.OnClickListener onOKListener, View.OnClickListener onCancelListener) {
		this.cancelEnable = cancelEnable;
		this.confirmMsg = confirmMsg;
		this.cancelText = cancelText;
		this.okText = okText;
		this.onOKListener = onOKListener;
		this.onCancelListener = onCancelListener;
	}

	protected void initCancelButton() {
		btnCancel = (Button) findViewById(R.id.btnCancel);
		if (cancelEnable) {
			btnCancel.setText(cancelText);
			btnCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					btnCancel(v);
				}
			});
		} else {
			btnCancel.setVisibility(View.GONE);
		}
	}

	protected void initOkButton() {
		btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setText(okText);
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				btnOK(v);
			}
		});
	}

	protected void initViewContent() {
		tvMsg = (TextView) findViewById(R.id.tvMsg);
		if (!TextUtils.isEmpty(confirmMsg)) {
			setContent(confirmMsg);
		}
	}

	protected void initViews() {
		initViewContent();
		initCancelButton();
		initOkButton();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		initViews();
	}

	public void setContent(CharSequence content) {
		tvMsg.setText(content);
	}

	public void setOnOKListener(View.OnClickListener onOKListener) {
		this.onOKListener = onOKListener;
	}

	public void setCancelListener(View.OnClickListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

	public String getCancelText() {
		return cancelText;
	}

	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
	}

	public String getOkText() {
		return okText;
	}

	public void setOkText(String okText) {
		this.okText = okText;
	}

	@Override
	protected void afterDialogViews() {
	}

	@Override
	protected void initDialogViews() {
	}
}
