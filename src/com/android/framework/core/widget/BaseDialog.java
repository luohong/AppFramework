package com.android.framework.core.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.framework.R;

public abstract class BaseDialog extends Dialog {

	protected final Context context;

	public BaseDialog(Context context) {
		super(context, R.style.BaseDialog);
		this.context = context;
	}

	protected BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public BaseDialog(Context context, int theme) {
		super(context, R.style.BaseDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init(context);
	}

	private void init(Context context) {
		View view = View.inflate(context, getLayoutId(), null);
		setContentView(view);
		getWindow().setGravity(Gravity.CENTER);
		setCanceledOnTouchOutside(true);
		
		initDialogViews();
		afterDialogViews();
		
		Window win = getWindow();
	    WindowManager m = win.getWindowManager();
		DisplayMetrics  dm = new DisplayMetrics();    
	    m.getDefaultDisplay().getMetrics(dm);    
	    
	    int width = context.getResources().getDimensionPixelSize(R.dimen.dialog_max_width);
	    
	    if (dm.widthPixels < width) {
	    	width = dm.widthPixels;
	    }

		WindowManager.LayoutParams p = win.getAttributes();
		p.width = width;
	    win.setAttributes(p);
	}

	protected abstract int getLayoutId();

	protected abstract void initDialogViews();

	protected abstract void afterDialogViews();
}
