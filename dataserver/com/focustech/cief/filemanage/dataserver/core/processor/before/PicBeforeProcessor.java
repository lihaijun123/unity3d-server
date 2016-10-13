package com.focustech.cief.filemanage.dataserver.core.processor.before;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.focustech.cief.filemanage.dataserver.exception.FileBeforeProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.PicInfo;
/**
 * 前置处理器-图片装饰类
 * *
 * @author lihaijun
 *
 */
public class PicBeforeProcessor extends AbstractFileBeforeProcessor {
	/**宽度*/
	private int width;
	/**高度*/
	private int height;

	public PicBeforeProcessor(IFileBeforeProcessor fileComponent){
		super(fileComponent);
	}
	@Override
	public AbstractFile doBeforeSave() throws FileBeforeProcessorException {
		try {
			PicInfo picInfo = null;
			if(null != fileItem){
				picInfo = (PicInfo)super.doDefaultBeforeProcessor();
				picInfo.setHeight(height);
				picInfo.setWidth(width);
				//等等其他属性
			}
			return picInfo;
		} catch (Exception e) {
			throw new FileBeforeProcessorException("PicBeforeProcessor处理器处理了非图片格式的文件，请上传正确的图片。");
		}
	}

	/**
	 * *
	 * @return
	 * @throws IOException
	 */
	private BufferedImage getImageRead(){
		InputStream inputStream = null;
		BufferedImage imageRead = null;
		try{
			inputStream = fileItem.getInputStream();
			imageRead = ImageIO.read(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(null != inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return imageRead;
	}

	@Override
	protected String getNewFileName(String fileName, String extName) {
		BufferedImage imageRead;
		imageRead = getImageRead();
		width = imageRead.getWidth();
		height = imageRead.getHeight();
		String defualtCustomName = super.getFileNamePrefix(fileServer, subBlockName) + "-" + width + "-" + height + extName;
		return super.getDefaultNewFileName(fileName, extName, defualtCustomName);
	}
	@Override
	protected String getVisitAddr(String localName) {
		return super.getDefaultVisitAddr(localName);
	}

}
