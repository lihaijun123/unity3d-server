package com.focustech.cief.filemanage.dataserver.core.imgcompress;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.focustech.cief.filemanage.dataserver.exception.PareseImageException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 *
 *
 * @author lihaijun
 *
 */
@SuppressWarnings("restriction")
public class FocusPhoto {
    public static final int PHOTO_100 = 100;
    public static final int PHOTO_160 = 160;
    private String photoName = "";
    private String photoType = "";
    private int photoHeight = 0;
    private int photoWidth = 0;
    private float photoSize = 0.0f;
    private boolean needPhoto100 = false;
    private boolean needPhoto160 = false;
    // 如果是Memory File，则通过此句柄保留
    private byte[] photoData = null;
    public static final String PHOTO_MEMORY_TYPE_MEMORY = "MEMORY";

    /**
     * Memory File
     *
     * @param photoData
     * @param photoName
     * @throws Exception
     */
    public FocusPhoto(byte[] photoBytes, String photoName) throws IOException, RuntimeException {
        init(photoBytes, photoName);
    }

    /**
     * Memory File
     *
     * @param input
     * @param photoName
     * @throws Exception
     */
    public FocusPhoto(InputStream input, String photoName) throws Exception {
        init(input, photoName);
    }

    /**
     * @return byte[]
     */
    public byte[] getPhotoData() throws Exception {
        return photoData;
    }

    /**
     * @return
     */
    public int getPhotoHeight() {
        return photoHeight;
    }

    /**
     * @return InputStream
     */
    public InputStream getPhotoInputStream() throws Exception {
        InputStream reInput = null;
        reInput = new ByteArrayInputStream(photoData);
        return reInput;
    }

    /**
     * @return
     */
    public String getPhotoName() {
        return photoName;
    }

    /**
     * @return
     */
    public float getPhotoSize() {
        return photoSize;
    }

    /**
     * @return
     */
    public String getPhotoType() {
        return photoType;
    }

    /**
     * @return
     */
    public int getPhotoWidth() {
        return photoWidth;
    }

    /**
     * @return
     */
    public boolean isNeedPhoto100() {
        return needPhoto100;
    }

    /**
     * @return
     */
    public boolean isNeedPhoto160() {
        return needPhoto160;
    }

    @Override
    public String toString() {
        return getPhotoName() + "\n" + getPhotoType() + "\n" + getPhotoWidth() + "\n" + getPhotoHeight() + "\n"
                + getPhotoSize() + "\n";
    }

    /**
     * @param photoData
     * @param photoName
     * @throws IOException
     * @throws Exception
     */
    private void init(byte[] photoBytes, String photoName) throws RuntimeException, IOException {
        InputStream input = null;
        try {
            setPhotoName(photoName);
            setPhotoData(photoBytes);
            input = new ByteArrayInputStream(photoBytes);
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setInput(input);
            imageInfo.setDetermineImageNumber(true);
            imageInfo.setCollectComments(true);
            if (imageInfo.check()) {
                setPhotoType(imageInfo.getFormatName().toLowerCase());
                setPhotoHeight(imageInfo.getHeight());
                setPhotoWidth(imageInfo.getWidth());
                setPhotoSize(input.available() / 1024.0f);
                initPhotoDimension();
            }
            else {
                throw new PareseImageException("图片格式出错！");
            }
            if (input != null) {
                input.close();
            }
        }
        catch (RuntimeException e) {
            if (input != null) {
                input.close();
            }
            throw e;
        }
    }

    /**
     * 不支持Oracle实现的输入流
     *
     * @param input
     * @param photoName
     * @throws Exception
     */
    private void init(InputStream input, String photoName) throws Exception {
        byte[] bytes = new byte[input.available()];
        input.read(bytes);
        setPhotoData(bytes);
        init(bytes, photoName);
    }

    private void initPhotoDimension() {
        if ((getPhotoHeight() <= PHOTO_100) && (getPhotoWidth() <= PHOTO_100)) {
            setNeedPhoto100(false);
            setNeedPhoto160(false);
        }
        else if ((getPhotoHeight() <= PHOTO_160) && (getPhotoWidth() <= PHOTO_160)) {
            setNeedPhoto100(true);
            setNeedPhoto160(false);
        }
        else {
            setNeedPhoto100(true);
            setNeedPhoto160(true);
        }
    }

    private void setNeedPhoto100(boolean needPhoto100) {
        this.needPhoto100 = needPhoto100;
    }

    private void setNeedPhoto160(boolean needPhoto160) {
        this.needPhoto160 = needPhoto160;
    }

    private void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    private void setPhotoHeight(int photoHeight) {
        this.photoHeight = photoHeight;
    }

    private void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    private void setPhotoSize(float photoSize) {
        this.photoSize = photoSize;
    }

    private void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    private void setPhotoWidth(int photoWidth) {
        this.photoWidth = photoWidth;
    }

    /**
     * @param type(类中的常量:PHOTO_100,PHOTO_160)
     * @return
     * @throws Exception
     */
    public FocusPhoto conversionImageDimensionByType(int width, int height) throws Exception {
        return conversionImageDimension(width, height);
    }

    /**
     * 用java转换图片.
     *
     * @param conversionMaxWidth
     * @param conversionMaxHeight
     * @return
     * @throws Exception
     */
    public FocusPhoto conversionImageDimension(int conversionMaxWidth, int conversionMaxHeight) throws Exception {
        // 开始时间
        int start = (int) System.currentTimeMillis();
        float scalex;
        float scaley;
        float scale;
        int imageWidth = getPhotoWidth();
        int imageHeight = getPhotoHeight();
        float widthHeightScale = (float) imageWidth / imageHeight;
        float conversionWidthHeightScale = (float) conversionMaxWidth / conversionMaxHeight;
        if (widthHeightScale > conversionWidthHeightScale) {
            scale = (float) conversionMaxWidth / imageWidth;
        }
        else {
            scale = (float) conversionMaxHeight / imageHeight;
        }
        scalex = scale * imageWidth;
        scaley = scale * imageHeight;
        FocusPhoto conversionPhoto = null;
        ByteArrayOutputStream dataOut = null;
        try {
            Image img = ImageIO.read(new ByteArrayInputStream(getPhotoData()));
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                throw new Exception("image format error::" + img.getWidth(null));
            }
            else {
                BufferedImage tag = new BufferedImage((int) scalex, (int) scaley, BufferedImage.TYPE_INT_RGB);
                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                tag.getGraphics().drawImage(
                        img.getScaledInstance((int) scalex, (int) scaley, Image.SCALE_AREA_AVERAGING), 0, 0, null);
                dataOut = new ByteArrayOutputStream();
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(dataOut);
                encoder.encode(tag);
                conversionPhoto = new FocusPhoto(dataOut.toByteArray(), getPhotoName());
                if (dataOut != null) {
                    dataOut.close();
                }
            }
        }
        catch (Exception e1) {
            if (dataOut != null) {
                dataOut.close();
            }
            throw e1;
        }
        // 结束时间
        int end = (int) System.currentTimeMillis();
        int count = end - start;
        return conversionPhoto;
    }
}
