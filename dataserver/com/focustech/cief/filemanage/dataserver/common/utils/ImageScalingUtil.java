package com.focustech.cief.filemanage.dataserver.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.focustech.cief.filemanage.dataserver.core.imgcompress.FocusPhoto;

/**
 *
 * 图片处理的时候 不是说你传过来一个图片的宽度是60px 那么压缩工具不一定可以把图片的宽度变为60px， 这个要看图片的比例和压缩算法来看
 * *
 * @author lihaijun
 *
 */
public class ImageScalingUtil {

    /**
     * 采用FocusPhoto方式压缩图片
     *
     * @param original
     * @return
     */
    public static FocusPhoto conversionImage(byte[] original, int width, int height) {
        FocusPhoto newPhoto = null;
        try {
            FocusPhoto f = new FocusPhoto(original, "");
            newPhoto = f.conversionImageDimensionByType(width, height);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        return newPhoto;
    }

    /**
     * *
     * @param OrinImageFile
     * @return
     */
    public static byte[] buildImageByteAry(File OrinImageFile){
    	FileInputStream fileInputStream = null;
    	try {
			fileInputStream = new FileInputStream(OrinImageFile);
			return buildImageByteAry(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(null != fileInputStream){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
    }

    public static byte[] buildImageByteAry(InputStream fileInputStream){
    	try {
			byte[] image = new byte[fileInputStream.available()];
			int length = 0;
			byte[] imageAry = new byte[fileInputStream.available()];
			for (int lastLength = 0; (length = fileInputStream.read(image)) > 0; lastLength = lastLength + length) {
				System.arraycopy(image, 0, imageAry, lastLength, length);
			}
			return imageAry;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }

}
