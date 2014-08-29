package com.android.framework.core.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.framework.R;

public class RoundCornerImageView extends ImageView {
	
	private int mBorderThickness = 0;
	private Context mContext;
	private int defaultColor = 0x00000000;
	// 边框颜色
	private int mBorderColor = 0;
	private int mRadius = 0;
	// 控件默认长、宽
	private int defaultWidth = 0;
	private int defaultHeight = 0;

	public RoundCornerImageView(Context context) {
		super(context);
		mContext = context;
	}

	public RoundCornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setCustomAttributes(attrs);
	}

	public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setCustomAttributes(attrs);
	}

	private void setCustomAttributes(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.roundedimageview);
		mBorderThickness = a.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
		mBorderColor = a.getColor(R.styleable.roundedimageview_border_outside_color, defaultColor);
		mRadius = a.getDimensionPixelSize(R.styleable.roundedimageview_round_corner_radius, 0);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		if (drawable.getClass() == NinePatchDrawable.class) {
			return;
		}
		
		// 重新测量宽度和高度
		this.measure(0, 0);

		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
		if (defaultWidth == 0) {
			defaultWidth = getWidth();
		}
		if (defaultHeight == 0) {
			defaultHeight = getHeight();
		}

		Bitmap roundBitmap = getCroppedRoundCornerBitmap(bitmap, mRadius);

		canvas.drawARGB(0, 0, 0, 0);
//		canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
		drawRoundCornerBorder(canvas, mRadius);
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}
	
	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public Bitmap getCroppedRoundCornerBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != defaultWidth || squareBitmap.getHeight() != defaultHeight) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, defaultWidth, defaultHeight, true);
		} else {
			scaledSrcBmp = squareBitmap;
		}
		
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
		RectF rectF = new RectF(mBorderThickness, mBorderThickness, scaledSrcBmp.getWidth() - mBorderThickness, scaledSrcBmp.getHeight() - mBorderThickness);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布局文件XML看不到效果)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		
		return output;
	}

	/**
	 * 画圆角
	 */
	private void drawRoundCornerBorder(Canvas canvas, int radius) {
		Paint paint = new Paint();
		/* 去锯齿 */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(mBorderColor);
		/* 设置paint的　style　为STROKE：空心 */
		paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽度 */
		paint.setStrokeWidth(mBorderThickness);
		
		RectF rectF = new RectF(0, 0, defaultWidth, defaultHeight);
		
		canvas.drawRoundRect(rectF, radius, radius, paint);
	}
}
