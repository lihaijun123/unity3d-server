package com.focustech.cief.filemanage.dataserver.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.focustech.cief.filemanage.dataserver.core.conf.VideoScreenshot;

/**
 * 视频音频处理(window)
 * *
 * @author lihaijun
 *
 */
public class FFmpegWindow extends FFmpeg{

	/**
	 * 获取视频截图
	 * *
	 * @param orinFilePath
	 */
	public void buildVideoScreenshot(VideoScreenshot videoScreenshot) {
        List<String> commend = new ArrayList<String>();
        //ffmpeg硬盘路径
        commend.add(getToolLocalPath());
        //输入文件
        commend.add("-i");
        //待截图文件硬盘路径
        commend.add(videoScreenshot.getOrinFilePath());
        //覆盖输入文件
        commend.add("-y");
        //强迫采用格式
        commend.add("-f");
        commend.add("image2");
        //搜索到指定时间
        commend.add("-ss");
        commend.add(videoScreenshot.getFromTime());
        //设置记录时间
        commend.add("-vframes");
        commend.add("1");
        commend.add("-s");
        commend.add(videoScreenshot.getPicSize());
        //截图的文件保存路径
        commend.add(videoScreenshot.getDisFilePath());
        InputStream in = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.redirectErrorStream(true);
            System.out.println("视频截图开始...");
            Process process = builder.start();
            in = process.getInputStream();
            byte[] re = new byte[1024];
            System.out.print("正在进行截图，请稍候");
            while(in.read(re) != -1){
            	System.out.print(".");
            }
            int exitVal = process.waitFor();
            System.out.println("");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        	if(null != in){
        		try {
					in.close();
					System.out.println("视频截图完成...");
				} catch (IOException e) {
					System.out.println("视频截图失败！");
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
		try {
			//传入的"ffmpeg的路径" "命令参数" "音频文件的路径"
			//创建操作系统进程。
			ProcessBuilder pb = new ProcessBuilder(getToolLocalPath(), "-i", videoScreenshot.getOrinFilePath());
			pb.redirectErrorStream(true);
			//使用此进程生成器的属性启动一个新进程。新进程将调用由 command() 给出的命令和参数。
			Process proc = pb.start();
			//获取进程的输入流
			BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = stdout.readLine()) != null) {
				sb.append(line);
			}
			proc.waitFor();
			stdout.close();
		} catch (Exception e) {
			e.printStackTrace();
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
		return FFMPEG_WINDOWS;
	}

	@Override
	public String getToolLocalPath() {
		return "E:\\audioObtain\\ffmpeg";
	}

}
