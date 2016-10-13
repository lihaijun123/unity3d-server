package com.focustech.cief.filemanage.dataserver.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.focustech.cief.filemanage.dataserver.core.conf.VideoScreenshot;

/**
 * 视频音频处理(linux)
 * *
 * @author lihaijun
 *
 */
public class FFmpegLinux extends FFmpeg{

	/**
	 * 获取视频截图
	 * *
	 * @param orinFilePath
	 */
	public void buildVideoScreenshot(VideoScreenshot videoScreenshot) {

		//String videoToJpg = "ffmpeg -i " + videoScreenshot.getOrinFilePath() + " -y -f image2 -ss " + videoScreenshot.getFromTime() + " -t 0.001 -s " + videoScreenshot.getPicSize() + " " + videoScreenshot.getDisFilePath();
		String videoToJpg = "ffmpeg -i " + videoScreenshot.getOrinFilePath() + " -y -f image2 -ss " + videoScreenshot.getFromTime() + " -vframes 1 -s " + videoScreenshot.getPicSize() + " " + videoScreenshot.getDisFilePath();
		InputStream inputStream = null;
		InputStreamReader inReader = null;
		BufferedReader bufferedReader = null;
      	try {
      		Runtime runtime = Runtime.getRuntime();
      		Process process = runtime.exec(videoToJpg);
      		inputStream = process.getErrorStream();
      		inReader = new InputStreamReader(inputStream);
      		bufferedReader = new BufferedReader(inReader);
      		System.out.println("视频截图开始...");
      		System.out.print("正在进行截图，请稍候");
      		String line = null;
      		System.out.println("<ERROR>");
      		while((line = bufferedReader.readLine()) != null){
      			System.out.println(line);
      		}
      		System.out.println("</ERROR>");
      		int exitVal = process.waitFor();
      		System.out.println("Process exitValue:" + exitVal);
      		System.out.print("截图完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(null != bufferedReader){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != inReader){
				try {
					inReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        	if(null != inputStream){
        		try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
    }

	/**
	 * 利用FFmpeg获取音频信息
	 * *
	 * @return 视频信息
	 */
	public StringBuffer getVideoInfo(VideoScreenshot videoScreenshot) {
		StringBuffer sb = new StringBuffer();
		//try {
			//传入的"ffmpeg的路径" "命令参数" "音频文件的路径"
			//创建操作系统进程。
			//ProcessBuilder pb = new ProcessBuilder("ffmpeg ", "-i", videoScreenshot.getOrinFilePath());
			//pb.redirectErrorStream(true);
			//使用此进程生成器的属性启动一个新进程。新进程将调用由 command() 给出的命令和参数。
			//Process proc = pb.start();
			//获取进程的输入流
			//BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			//String line;
			//while ((line = stdout.readLine()) != null) {
				//sb.append(line);
			//}
			//proc.waitFor();
			//stdout.close();

		//} catch (Exception e) {
			//System.out.println("播放时长信息获取失败");
			//e.printStackTrace();
		//}
		//System.out.println("播放时长信息：");
		//System.out.println(sb);
			//String videoToJpg = "ffmpeg -i " + videoScreenshot.getOrinFilePath() + " -y -f image2 -ss " + videoScreenshot.getFromTime() + " -t 0.001 -s " + videoScreenshot.getPicSize() + " " + videoScreenshot.getDisFilePath();
		String videoToTimelen = "ffmpeg -i " + videoScreenshot.getOrinFilePath();
		InputStream inputStream = null;
		InputStreamReader inReader = null;
		BufferedReader bufferedReader = null;
      	try {
      		Runtime runtime = Runtime.getRuntime();
      		Process process = runtime.exec(videoToTimelen);
      		inputStream = process.getErrorStream();
      		inReader = new InputStreamReader(inputStream);
      		bufferedReader = new BufferedReader(inReader);
      		System.out.println("播放时长信息获取开始...");
      		String line = null;
      		System.out.println("<ERROR>");
      		while((line = bufferedReader.readLine()) != null){
      			sb.append(line);
      			System.out.println(line);
      		}
      		System.out.println("</ERROR>");
      		int exitVal = process.waitFor();
      		System.out.println("Process exitValue:" + exitVal);
      		System.out.print("播放时长信息获取完成");
		} catch (Exception e) {
			System.out.println("播放时长信息获取失败");
			e.printStackTrace();
		}
		finally {
			if(null != bufferedReader){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != inReader){
				try {
					inReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        	if(null != inputStream){
        		try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
		return sb;
	}

	/**
	 * 获取播放时长
	 * *
	 * @param str
	 * @return
	 */
	public String dealVideoInfo(String str) {
		String msg = "";
		Matcher m = Pattern.compile("Duration: \\w+:\\w+:\\w+").matcher(str);
		while (m.find()) {
			msg = m.group();
			msg = msg.replace("Duration: ", "");
		}
		return msg;
	}

	@Override
	public String getPlatForm() {
		return FFMPEG_LINUX;
	}

	@Override
	public String getToolLocalPath() {
		return "E:\\eclipse-all-in-one2\\workspace\\cief-filemanage-deploy_dev\\audioObtain\\ffmpeg";
	}


}
