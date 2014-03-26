package com.android.framework.core.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

/**
 * 
 * 图片处理工具�?图片裁剪 加圆�?等等
 * 
 * @author lizhiyong<lizhiyong@haodou.com>
 * 
 * $Id: ImageUtil.java 4651 2014-01-21 10:05:04Z zhiyong $
 * 
 */
public class ImageUtil {

    private static final int BUFF_SIZE = 8192;

    /***
     * 测试拍照后的图片是正常的还是旋转�?     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        return degree;
    }
    
    
    /**
     * 
     * 旋转图片，同时压�?     * 
     * @param filePath
     * @param degree
     */
 	public static void rotatePhoto(String filePath, int degree, int width, int height) {
 		if (degree != 0) {
 			try {
 				BitmapFactory.Options options = new BitmapFactory.Options();
		        //先获取宽�?		        options.inJustDecodeBounds = true;
		        BitmapFactory.decodeFile(filePath, options);
		        options.inSampleSize = ImageUtil.calculateInSampleSize(options, width, height);
		        options.inJustDecodeBounds = false; 
		        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		        
		        int relwidth = bitmap.getWidth();
	            int relheight = bitmap.getHeight();
	            Matrix matrix = new Matrix();
	            matrix.postRotate(degree);
	            bitmap = Bitmap.createBitmap(bitmap, 0, 0, relwidth, relheight, matrix, true);
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
 				bitmap.compress(CompressFormat.JPEG, 100, bos);
 				byte[] byteData = bos.toByteArray();
 				FileUtil.saveFile2SDcard(filePath, byteData);
 				bitmap.recycle();
 				bitmap = null;
 			} catch (OutOfMemoryError e) {
                e.printStackTrace();
            } catch (Exception e) {
 				e.printStackTrace();
 			}

 		}
 	}
 	
 	/**
 	 * 
 	 * 旋转图片
 	 * 
 	 * @param filePath
 	 * @param degree
 	 */
 	public static boolean rotatePhoto(String filePath, int degree) {
 	    boolean result = true;
        if (degree != 0) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                int relwidth = bitmap.getWidth();
                int relheight = bitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.postRotate(degree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, relwidth, relheight, matrix, true);
                saveImage2SDcard(filePath, bitmap, 100);
                bitmap.recycle();
                bitmap = null;
                
            } catch (OutOfMemoryError e) {
                result = false;
                e.printStackTrace();
            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
 	
 	/**
 	 * 
 	 * 计算图片�?��被压缩的比例
 	 * 
 	 * @param options
 	 * @param reqWidth
 	 * @param reqHeight
 	 * @return
 	 */
 	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
         final int height = options.outHeight;
         final int width = options.outWidth;
         int inSampleSize = 1;

         if (height > reqHeight || width > reqWidth) {
              final int heightRatio = Math.round((float) height / (float) reqHeight);
              final int widthRatio = Math.round((float) width / (float) reqWidth);
              inSampleSize = Math.max(heightRatio, widthRatio);
         }
             
         return inSampleSize;
     }
 	
 	
 	public static int calculateMinInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
             final int heightRatio = Math.round((float) height / (float) reqHeight);
             final int widthRatio = Math.round((float) width / (float) reqWidth);
             inSampleSize = Math.min(heightRatio, widthRatio);
        }
            
        return inSampleSize;
    }
 	
 	
 	/**
 	 * 
 	 * 从源路径生成�?��缩略图到目标路径
 	 * 如果图片有旋转，会自动将图片旋转�? 	 * 没有对图片是否存在做效验
 	 * 
 	 * @param src  源路�? 	 * @param dst  目标路径
 	 * @param w    缩略图宽
 	 * @param h    缩略图高
 	 * @param square 是否裁为正方形，此�?为true时才会严格按照设置的宽高裁剪，否则只是等比例计算裁剪
 	 */
 	public static boolean createThumb(String src, String dst, int w, int h, boolean square) {
 	    boolean result = true;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, options);
        if (square) {
            options.inSampleSize = ImageUtil.calculateMinInSampleSize(options, w, h);
        } else {
            options.inSampleSize = ImageUtil.calculateInSampleSize(options, w, h);
        }

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(src, options);

        if (bitmap == null) {
            return false;
        } else {
            if (square) {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h);
            }

            boolean flag = saveImage2SDcard(dst, bitmap, 100);
            
            if (!flag) {
                return false;
            }
            
            bitmap.recycle();
            bitmap = null;
        }

        int degree = getExifOrientation(src);
        if (degree > 0) {
            return rotatePhoto(dst, degree);
        } else {
            return result;
        }
 	}

    /**
     * 
     * drawable �?Bitmap
     * 
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bmp = bd.getBitmap();
        return bmp;
    }

    /**
     * bitmap �?drawable
     * 
     * @param bmp
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bmp) {
        Drawable drawable = new BitmapDrawable(bmp);
        return drawable;
    }

    /**
     * 
     * byte转Bitmap
     * 
     * @param bytes
     * @return
     */
    public static Bitmap createBmpFromBytes(byte[] bytes) {
        Bitmap bmp = null;

        if (bytes.length > 0) {
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        return bmp;
    }

    /**
     * 从路径得到一个图�?     */
    public static Bitmap createBmpFromPath(String path, int sampSize) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = sampSize;
        Bitmap mCateBitmap = BitmapFactory.decodeFile(path, opts);
        return mCateBitmap;

    }

    /**
     * byte转drawable
     * 
     * @param bytes
     * @return
     */
    public static Drawable createDrawableFromBytes(byte[] bytes) {
        Drawable drawable = null;
        drawable = bitmap2Drawable(createBmpFromBytes(bytes));
        return drawable;
    }

    /**
     * 保存图片至SD�?     * 
     * @param file
     * @param bmp
     * @return
     */
    public static boolean saveImage2SDcard(String file, Bitmap bmp, String type) {
        boolean flag = false;

        if (!SDcardUtil.checkSdCardEnable()) {
            return false;
        }

        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file), BUFF_SIZE);

            if (type.equals("png")) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            } else {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }

            bos.flush();
            bos.close();
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 保存图片至SD�?     * 
     * @param file
     * @param bmp
     * @return
     */
    public static boolean saveImage2SDcard(String file, Bitmap bmp, int quality) {
        boolean flag = false;
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file), BUFF_SIZE);
            if (FileUtil.getExtensionName(file).equalsIgnoreCase("jpg")) {
                bmp.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            } else if (FileUtil.getExtensionName(file).equalsIgnoreCase("png")) {
                bmp.compress(Bitmap.CompressFormat.PNG, quality, bos);
            }
            bos.flush();
            bos.close();
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!bmp.isRecycled()) {
            bmp.recycle();
        }
        return flag;
    }

    /**
     * 
     * 从网络下载图片，当图片过大时，自动缩减图片，以避免内存溢出问�?
     * 
     * @param url
     *            图片url地址
     * @param width
     *            安全的图片宽�?     * @param height
     *            安全的图片高�?     * @return
     * @throws IOException
     */
    public static Bitmap downloadImage(String url, int width, int height) throws IOException {
        Bitmap bmp = null;
        if (url.equals("")) {
            return bmp;
        }

        InputStream is = getInputStream(url);
        if (is != null) {
            /**
             * 避免每次获取图片请求两次网络 modified by ligao
             */
            // 获取图片数据
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int ch;
            byte[] buf = new byte[BUFF_SIZE];
            while ((ch = is.read(buf)) != -1) {
                bos.write(buf, 0, ch);
            }

            byte[] bytes = bos.toByteArray();
            bos.close();
            is.close();

            // 创建 Bitmap 对象
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inScaled = false;
            BitmapFactory.decodeStream(new ByteArrayInputStream(bytes), null, options);
            int max = calculateInSampleSize(options, width, height);
            if (max == 0) {
                bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } else {
                options.inSampleSize = max;
                options.inJustDecodeBounds = false;
                options.inInputShareable = true;
                options.inPurgeable = true;
                bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            }

            bytes = null;

        }

        return bmp;
    }

    /**
     * 
     * 获取网络url输入�?     * 
     * @param url
     *            网络url地址
     * @return
     */
    private static InputStream getInputStream(String url) {
        URL uri;
        InputStream is = null;
        try {
            uri = new URL(url);
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) uri.openConnection();
                connection.setDoOutput(false);
                connection.setRequestMethod("GET");
                connection.setUseCaches(true);
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
                try {
                    is = connection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return is;
    }

    /**
     * 将Bitmap转换到int数组
     * 
     * @param bitmap
     * @return
     */
    public static int[] bitmap2IntARGB(Bitmap bitmap) {
        int[] arryInt = null;
        try {
            int i = bitmap.getWidth();
            int j = bitmap.getHeight();
            arryInt = new int[i * j];
            bitmap.getPixels(arryInt, 0, i, 0, 0, i, j);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arryInt;
    }

}
