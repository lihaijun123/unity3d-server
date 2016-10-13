package com.focustech.cief.filemanage.dataserver.common.utils;

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
import java.util.ArrayList;
import java.util.List;

import com.focustech.core.utils.StringUtils;

/**
 * *
 * 文件工具类
 * @author lihaijun
 *
 */
public class FileUtil {
	/**
	 *
	 *
	 * @param filePathAndName
	 */
	public static void delFile(String filePathAndName) {
		try {
			File file = new File(filePathAndName);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * *
	 * @param filePath
	 */
	public static void delMultFile(String filePath) {
		File file = new File(filePath);
		try{
			if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(filePath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝文件
	 * *
	 * @param sourceFilePath
	 * @param targetFilePath
	 */
	public static void copyFile(String sourceFilePath, String targetFilePath){
		File sourceFile = new File(sourceFilePath);
		File targetFile = new File(targetFilePath);
		copyFile(sourceFile, targetFile);
	}

	public static void copyDirectory(String sourceDir, String targetDir){
		//新建目标目录
		(new File(targetDir)).mkdir();
		//获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for(int i = 0, j = file.length; i < j; i ++){
			if(file[i].isFile()){
				//源文件
				File sourceFile = file[i];
				//目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if(file[i].isDirectory()){
				String dir_1 = sourceDir + File.separator + file[i].getName();
				String dir_2 = targetDir + File.separator + file[i].getName();
				copyDirectory(sourceDir, targetDir);
			}
		}

	}

	/**
	 * 拷贝文件
	 * *
	 * @param sourceFile
	 * @param targetFile
	 */
	public static void copyFile(File sourceFile, File targetFile){
		//输入流
		FileInputStream input = null;
		BufferedInputStream inBuff = null;
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			input = new FileInputStream(sourceFile);
			inBuff = new BufferedInputStream(input);
			//输出流
			output = new FileOutputStream(targetFile);
			outBuff = new BufferedOutputStream(output);
			//缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while((len = inBuff.read(b)) != -1){
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(inBuff != null){
				try {
					inBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outBuff != null){
				try {
					outBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 *	是否包含过来的文件格式
	 * *
	 * @param attachFormatStr
	 * @return
	 */
	public static boolean isContainFilterFileExt(String fileExt, String attachFormatStr, String[] defaultAttachFormatAry){
		String[] extAry = defaultAttachFormatAry;
		if(!StringUtils.isEmpty(attachFormatStr)){
			try {
				extAry = attachFormatStr.split(",");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		boolean flag = false;
		if(null != defaultAttachFormatAry){
			for (String ext : extAry) {
				if(fileExt.equals(ext)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 *
	 * *
	 *
	 * @param file
	 * @return
	 */
	public static List<String> readList(File file) {
		BufferedReader br = null;
		List<String> data = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			for (String str = null; (str = br.readLine()) != null;) {
				data.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
	/**
	 *
	 * *
	 *
	 * @param file
	 * @param data
	 */
	public static void rewrite(File file, String data) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args){
		FileUtil.delFile("F:\\ve_fileupload_workplace\\ve_fileupload1\\WebRoot\\fileUpload\\2011-10-28\\0dc5e347-f05d-4cd5-96f5-2e3cc0be1413.rar");
	}
}
