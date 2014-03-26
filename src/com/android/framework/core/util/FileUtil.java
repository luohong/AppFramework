package com.android.framework.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

import android.content.Context;
import android.os.Environment;

/**
 * 
 * 文件操作类
 * 
 * 缓存文件管理，去那吃，菜谱通用
 * 
 * 实现缓存文件更新，设置缓存过期时间
 * 
 * 图片缓存文件存入时做裁减
 * 
 * 缓存目录文件个数监控
 * 
 * 缓存目录使用空间监控
 * 
 * 
 * @author lizhiyong<lizhiyong@haodou.com>
 * 
 * $Id: FileUtil.java 4612 2014-01-19 09:33:36Z zhiyong $
 *
 */
public class FileUtil {
	
	private static final int BUFF_SIZE = 8192;
	
	/**
	 * 判断某个缓存文件在sdcard中是否存在
	 * 
	 * 如果缓存文件缓存时间已过期，删除老的缓存文件
	 * 
	 * @param file 文件完整路径
	 * @return
	 */
	public static boolean cacheFileExists(String path) {
		boolean flag = false;
		String noextpath = path.substring(0, path.indexOf("."));
		File file = new File(noextpath);
		if (file.exists()) {
			long currentTimeMilles = System.currentTimeMillis();
			long lastModifieMilles = file.lastModified();
			
			if ((currentTimeMilles - lastModifieMilles) > 604800000) {
				file.delete();
			} else {
				flag = true;
			}
		} else {
			File file1 = new File(path);
			if (file1.exists()) {
				long currentTimeMilles = System.currentTimeMillis();
				long lastModifieMilles = file1.lastModified();
				
				if ((currentTimeMilles - lastModifieMilles) > 604800000) {
					file1.delete();
				} else {
					flag = file1.renameTo(new File(noextpath));
				}
			}
		}
			
		return flag;
	}
	
	/**
	 * 判断某个网络url地址对应的图片在本地是否有缓存文件
	 * 
	 * @param path 	缓存文件所在目录
	 * @param url	图片url地址
	 * @return
	 */
	public static boolean imageCacheFileExists(String path, String url) {
		return cacheFileExists(getImageCacheFile(path, url));
	}
	
	/**
	 * 获取url对应图片缓存的全路径
	 * 
	 * @param path 	图片缓存目录
	 * @param url	图片url地址
	 * @return
	 */
	public static String getImageCacheFile(String path, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append(path);
		sb.append(Md5Util.MD5Encode(url));
		sb.append("." + getExtensionName(url));
		
		return sb.toString();
	}
	
	/**
	 *获取文件扩展名
	 */
	public static String getExtensionName(String filename) { 
	    if ((filename != null) && (filename.length() > 0)) { 
	        int dot = filename.lastIndexOf('.'); 
	        if ((dot > -1) && (dot < (filename.length() - 1))) { 
	            return filename.substring(dot + 1); 
	        } 
	    } 
	    return filename; 
	} 
	
	/**
	 * 
	 * 保存文件至SD卡
	 * 
	 * @param path
	 * @param bytes
	 * @return
	 */
	public static boolean saveFile2SDcard(String path, byte[] bytes) {

		parentFolder(path);
		
		File file = new File(path);
		
        boolean flag = false;
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(
			        new FileOutputStream(file), BUFF_SIZE);
			bos.write(bytes, 0, bytes.length);
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
	 * 文件或文件夹拷贝
	 * 如果是文件夹拷贝 目标文件必须也是文件夹
	 * 
	 * @param source 源文件
	 * @param dst 目标文件
	 * @return
	 */
	public static boolean copy(String src, String dst) {
		
		File srcFile = new File(src);
		if (!srcFile.exists()) { //源文件不存在
			return false;
		}
		
		//目标文件最后一个字符
		Character lastChar = dst.charAt(dst.length() - 1);
		
		if (srcFile.isDirectory()) { //整个文件夹拷贝
			
			if (lastChar != '\\' && lastChar != '/') { //如果目标是一个文件而不是目录，返回false
				return false;
			}
			
			boolean flag = false;
			
			parentFolder(dst);
			File dstFile = new File(dst);
			
			File[] files = srcFile.listFiles();
			
			for (File f : files) {
				String newSrcPath = f.getAbsolutePath();
				String newDstPath = dstFile.getAbsolutePath() + File.separator + f.getName();
				
				if (f.isDirectory()) {
					newSrcPath	+= File.separator;
					newDstPath += File.separator;
				}
				
				copy(newSrcPath, newDstPath);
				
				flag = true;
			}
			
			return flag;
			
		} else { //单个文价拷贝
			File dstFile = null;
			
			if (lastChar.equals('\\') || lastChar.equals('/')) { //目标地址是目录
				dst = dst + srcFile.getName();
			} 
			
			parentFolder(dst);
			dstFile = new File(dst);
			
	        InputStream is = null;
	        OutputStream op = null;
			try {
				is = new FileInputStream(srcFile);
				op = new FileOutputStream(dstFile);
				
		        BufferedInputStream bis = new BufferedInputStream(is);
		        BufferedOutputStream bos = new BufferedOutputStream(op);

		        byte[] bt = new byte[BUFF_SIZE];
		        int len = -1;
		        
				try {
					len = bis.read(bt);
			        while (len != -1) {
    			        	    bos.write(bt, 0, len);
    			        	    len = bis.read(bt);
			        }

			        bis.close();
			        bos.close();
			        
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
	}
	
	/**
	 * 判断某个文件所在的文件夹是否存在，不存在时直接创建
	 * 
	 * @param path
	 */
	public static void parentFolder(String path) {
		File file = new File(path);
		String parent = file.getParent();
		
		File parentFile = new File(parent + File.separator);
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
	}

    /**
     * 创建目录（如果不存在）。
     * @param dirPath 目录的路径
     * @return true表示创建，false表示该目录已经存在
     */
    public static boolean createDirIfMissed(String dirPath) {
        File dir = new File(dirPath);
        return !dir.exists() && dir.mkdirs();
    }
	
	
	/**
	 * 获取SD卡路径
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory(); // 获取跟目录
			String tmpPath = sdDir.getAbsolutePath();
			if (!tmpPath.endsWith(File.separator)) {
				tmpPath += File.separator;
			}
			return tmpPath;
		}
		
		return null;
	}
	

	/**
	 * 如果sdcard没有mounted，返回false
	 * 
	 * @param os
	 * @return
	 */
	public static boolean saveBytes(String filePath, byte[] data) {

		try {

			File file = new File(filePath);
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(data);
			outStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}

		return false;
	}
	
	/**
	 * 如果sdcard没有mounted，返回false
	 * 
	 * @param os
	 * @return
	 */
	public static boolean saveBytes(File file, byte[] data) {

		try {
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(data);
			outStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}

		return false;
	}
	
	/**
	 * 如果sdcard没有mounted，返回false
	 * 
	 * @param os
	 * @return
	 */
	public static byte[] getBytes(String filePath) {

		try {

			File file = new File(filePath);
			if (!file.exists() || !file.canRead()) {
			    return null;
			}
			FileInputStream inStream = new FileInputStream(file);
			byte[] bytes = new byte[inStream.available()];
			inStream.read(bytes);
			inStream.close();
			
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

	}
	
	
	/**
	 * 如果sdcard没有mounted，返回false
	 * 
	 * @param os
	 * @return
	 */
	public static boolean saveObject(String filePath, Object object) {
		try {
			File file = new File(filePath);
			FileOutputStream outStream = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(outStream);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return false;
	}
	
	/**
	 * 如果sdcard没有mounted，返回false
	 * 
	 * @param os
	 * @return
	 */
	public static boolean saveObject(File file, Object object) {

		try {
			FileOutputStream outStream = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(outStream);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return false;
	}
	
	/**
	 * 如果sdcard没有mounted，返回false
	 * 
	 * @param os
	 * @return
	 */
	public static Object getObject(String filePath) {

		try {

			File file = new File(filePath);
			if (!file.exists() || !file.canRead()) {
			    return null;
			}
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			ois.close();
			fis.close();
			
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	
	/**
	 * 创建临时文件
	 * @param fielPath
	 * @return
	 */
	public static File createTmpFile(Context context) {
		
		String rootPath = context.getFilesDir().getAbsolutePath();
		if (!rootPath.endsWith("/")) {
			rootPath += "/";
		}
		
		try {
			String tmpPath = rootPath + new Date().hashCode();
			File file = new File(tmpPath);
			file.createNewFile();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 删除文件
	 * @param file
	 */
	public static boolean delFile(String path) {
		if (path.equals("") || !SDcardUtil.checkSdCardEnable()) {
			return false;
		} else {
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				return file.delete();
			} else {
				return false;
			}
		}
	}

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param path 将要删除的文件目录
     * @return boolean 成功清除目录及子文件返回true；
     *                  若途中删除某一文件或清除目录失败，则终止清除工作并返回false.
     */
    public static boolean deleteDir(String path) {
        return deleteDir(new File(path));
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean 成功清除目录及子文件返回true；
     *                  若途中删除某一文件或清除目录失败，则终止清除工作并返回false.
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 递归清空目录下的所有文件及子目录下所有文件
     * @param path 将要清空的文件目录
     * @return boolean 成功清除目录及子文件返回true；
     *                  若途中清空某一文件或清除目录失败，则终止清除工作并返回false.
     */
    public static boolean clearDir(String path) {
        return clearDir(new File(path));
    }

    /**
     * 递归清空目录下的所有文件及子目录下所有文件
     * @param dir 将要清空的文件目录
     * @return boolean 成功清除目录及子文件返回true；
     *                  若途中清空某一文件或清除目录失败，则终止清除工作并返回false.
     */
    public static boolean clearDir(File dir) {
        if (dir == null) {
            return false;
        }

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
	 * 
	 * 获取某个目录下所有文件的大小之和
	 * @param path
	 * @return
	 */
	public static float getDirSize(String path, boolean isRoot) {
		File file = new File(path);
		
		float size = 0.f;
		if (file.exists()) {
			if (file.isDirectory()) {
				File []fs = file.listFiles();
				for (File childfile : fs) {
					if (childfile.isFile()) {
						size += childfile.length();
					} else {
						size += FileUtil.getDirSize(childfile.getAbsolutePath(), false);
					}
				}
			} else {
				if (!isRoot) {
					size += file.length();
				}
			}
		} 
		
		return size;
	}	
	
	/**
	 * 
	 * 在指定的目录下生成一个.nomedia文件，避免手机相册应用扫描此目录
	 * @param path
	 * @return
	 */
	public static boolean createNomediaFileInPath(String path) {
	    
	    if (!SDcardUtil.checkSdCardEnable()) {
	        return false;
	    }
	    
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(path).append(".nomedia");
		File nomedia = new File(sb.toString());
		if (!nomedia.exists()) {
			try {
				return nomedia.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}
	
	
	/**
	 * 
	 * 写入字符串内容到文本文件中
	 * 
	 * @param file     文件全路径名
	 * @param content  写入到文件中的内容
	 * @param append   是否为追加写入，true表示追加写入，false全新写入
	 * @throws IOException
	 */
	public static void putFileContent(String file, String content, boolean append) throws IOException {
	    
	    File fileName = new File(file);
	    String path = fileName.getParent() + File.separator;
	    File filePath = new File(path);
	    if (!filePath.exists() || !filePath.isDirectory()) {
	        filePath.mkdirs();
	    }
	    
	    if (!fileName.exists()) {
	        fileName.createNewFile();
	    }
	    
	    FileWriter writer = new FileWriter(file, append);
	    BufferedWriter bw = new BufferedWriter(writer);
	    bw.write(content);
	    bw.newLine();
	    bw.flush();
	    bw.close();
	    writer.close();
	}
	
	
	/**
	 * 
	 * 读取文本文件的全部内容
	 * 不要使用此方法读取大文件
	 * 
	 * @param filename  文件全路径名
	 * @return 
	 * @throws IOException
	 */
    @SuppressWarnings("resource")
    public static String getFileContent(String filename) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    
	    File file = new File(filename);
	    if (file.exists()) {
        	    BufferedReader reader = null;  
        	    
        	    reader = new BufferedReader(new FileReader(file)); 
        	    String line;
        	    while ((line = reader.readLine()) != null) {  
        	        sb.append(line);
        	    }
	    }
	    
	    return sb.toString();
	}
    
    
    /**
     * 
     * 移动文件 
     * 
     * @param srcFileName 源文件完整路径
     * @param destFileName 目标文件完整路径 
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destFileName) {
        
        File srcFile = new File(srcFileName);
        if(!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        
        File destFile = new File(destFileName);
        
        String path = destFile.getParent() + File.separator;
        File destDir = new File(path);
        if (!destDir.exists() || !destDir.isDirectory()) {
            destDir.mkdirs();
        }
        
        return srcFile.renameTo(new File(destFileName));
    }
    
    /**
     * 复制src文件到dst文件。如果dst文件不存在，则创建它
     * 
     * @author William.cheng
     * @version 创建时间：2011-12-27 下午5:32:48
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        copyStream(in, out);
    }
    

    private static void copyStream(InputStream in, OutputStream out)
            throws IOException {
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
}

