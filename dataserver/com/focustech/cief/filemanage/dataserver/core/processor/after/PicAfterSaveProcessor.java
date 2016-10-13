package com.focustech.cief.filemanage.dataserver.core.processor.after;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import com.focustech.cief.filemanage.dataserver.common.utils.DateUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.ValueFormatUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToCFS;
import com.focustech.cief.filemanage.dataserver.core.write.WriteFileToCompanyFS;
import com.focustech.cief.filemanage.dataserver.exception.FileAfterProcessorException;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.PicInfo;
import com.focustech.common.utils.TCUtil;
import com.focustech.core.Assert;
/**
 * 后置处理器-图片处理
 * *
 * @author lihaijun
 *
 */
public class PicAfterSaveProcessor extends AbstractFileAfterSaveProcessor {

	/**压缩的数量*/
	private final static String COMPRESS_COUNT = "compressCount";
	/**压缩的尺寸*/
	private final static String COMPRESS_SIZE = "compressSize";
	/**压缩的图片访问ID前缀*/
	private final static String COMPRESS_RETURN_ID_PREFIX = "compressReturnId_";
	/**压缩的图片访问url前缀*/
	private final static String COMPRESS_RETURN_URL_PREFIX = "compressReturnUrl_";
	/**图片名称前缀*/
	private final static String IMAGE_PREFIX_NAME = "-compress-";

	private final static String CUT_IMAGE_PREFIX_NAME = "-cut-";

	private final static String CUT_SIZE = "cutSize";
	/**裁剪的图片访问ID前缀*/
	private final static String CUT_RETURN_ID_PREFIX = "cutReturnId";
	/**裁剪的图片访问url前缀*/
	private final static String CUT_RETURN_URL_PREFIX = "cutReturnUrl";

	@Override
	public Map<String, String> doAfterSave(AbstractFile abstractFile, Map<String, Object> paramMap) throws FileAfterProcessorException {
		Assert.notNull(abstractFile);
		Assert.notNull(paramMap);
		super.doAfterSave(abstractFile, paramMap);
		Map<String, String> returnMap = null;
		//url参数
		String urlParam = TCUtil.sv(paramMap.get(FileParam.URL_PARAM));
		JSONObject jsonObj = JSONObject.fromObject(urlParam);
		if(jsonObj.containsKey(COMPRESS_COUNT) && jsonObj.containsKey(COMPRESS_SIZE)){
			int compressCount = Integer.parseInt(jsonObj.getString(COMPRESS_COUNT));
			String[] compressSizeAry = jsonObj.getString(COMPRESS_SIZE).split(",");
			if(compressCount != compressSizeAry.length){
				throw new RuntimeException("压缩的图片的张数和对应的尺寸个数不匹配");
			}
			//保存图片信息
			returnMap = saveCompressPicInfo(abstractFile, compressCount, compressSizeAry, paramMap);
		}
		if(jsonObj.containsKey(CUT_SIZE)){
			returnMap = saveCutPicInfo(abstractFile, jsonObj.getString(CUT_SIZE), paramMap);
		}
		if(!returnMap.isEmpty()){
			//抽取里面key值放进数组里面
			extractReturnMapKeyAry(returnMap);
		}
		/*else {
			throw new RuntimeException("图片配置参数是图片压缩参数，此异常是提醒，格式应该为{compressCount:xx,compressSize:xx}");
		}*/
		return returnMap;
	}


	/**
	 * 保存压缩图片信息
	 * 构建图片对象和保存到本地磁盘
	 * *
	 * @param physicalAddr
	 * @param physicalSubFoldAddr
	 * @param visitSubFoldAddr
	 * @param compressCount
	 * @param compressSizeAry
	 * @param paramMap
	 * @param serverId
	 */
	private Map<String, String> saveCompressPicInfo(AbstractFile abstractFile, int compressCount, String[] compressSizeAry, Map<String, Object> paramMap){
		Map<String, String> returnMap = new HashMap<String, String>();
		Map<String, String> extrParamMap = FilePathUtil.extractParameter(abstractFile);
		//物理路径
		String filePhysicalPath = extrParamMap.get(FileParam.FILE_PHYSICAL_PATH);
		//物理子目录路径
		String subBlockPhysicalPath = extrParamMap.get(FileParam.SUB_BLOCK_PHYSICAL_PATH);
		PicInfo picInfo = null;
		//源图片
		File OrinImageFile = new File(filePhysicalPath);
		ByteArrayOutputStream nOt = null;
		int width = 0;
		int height = 0;
		for(int i = 0; i < compressCount; i ++){
			nOt = new ByteArrayOutputStream();
			String compreSize = compressSizeAry[i];
			try {
				if(compreSize.contains("f")){
					//按比例压缩
					float scale = TCUtil.fv(compreSize.replace("f", ""));
					PicInfo orginPic = (PicInfo)abstractFile;
					width = TCUtil.iv(Math.round(orginPic.getWidth() * scale));
					height = TCUtil.iv(Math.round(orginPic.getHeight() * scale));
					Thumbnails.of(OrinImageFile).scale(scale).toOutputStream(nOt);
				} else if(compreSize.contains("*")){
					//按指定的尺寸压缩
					width = Integer.parseInt(compreSize.split(FileParam.PIC_EXE_JOIN_STR)[0]);
					height = Integer.parseInt(compreSize.split(FileParam.PIC_EXE_JOIN_STR)[1]);
					Thumbnails.of(OrinImageFile).size(width, height).toOutputStream(nOt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				//保存到本地
				String picName = getPicName(width, height, IMAGE_PREFIX_NAME);
				byte[] imgOutData = nOt.toByteArray();
				if(imgOutData != null && imgOutData.length > 0){
					float size = ValueFormatUtil.fv((float)imgOutData.length / 1024);
					FileUtils.writeByteArrayToFile(new File(subBlockPhysicalPath + picName), imgOutData);
					String displayName = abstractFile.getName();
					if(displayName.contains(".")){
						displayName = displayName.split("\\.")[0];
					}
					picInfo = new PicInfo();
					picInfo.setName(displayName+ "-压缩图片-" + width + "-" + height + "." + abstractFile.getExt());
					picInfo.setExt(FileParam.PIC_EXE_JPG);
					picInfo.setSize(size);
					picInfo.setWidth(width);
					picInfo.setHeight(height);
					picInfo.setBlockName(abstractFile.getBlockName());
					picInfo.setLocalName(picName);
					picInfo.setVisitAddr(picName);
					picInfo.setCreateTime(DateUtil.getCurrentDateTime());
					picInfo.setServerSn(abstractFile.getServerSn());
					//保存到数据库
					picInfo.setServerNode(abstractFile.getServerNode());
					String sn = String.valueOf(fileService.saveChildFile(picInfo));
					String visitUrl = picInfo.getVisitAddr();
					//返回信息
					returnMap.put(COMPRESS_RETURN_ID_PREFIX + i, sn);
					returnMap.put(COMPRESS_RETURN_URL_PREFIX + i, visitUrl);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnMap;
	}
	/**
	 * 保存图片裁剪信息
	 * 构建图片对象和保存到本地磁盘
	 * *
	 * @param physicalAddr
	 * @param physicalSubFoldAddr
	 * @param visitSubFoldAddr
	 * @param compressCount
	 * @param compressSizeAry
	 * @param paramMap
	 * @param serverId
	 */
	private Map<String, String> saveCutPicInfo(AbstractFile abstractFile, String cutSizeStr, Map<String, Object> paramMap){
		Map<String, String> returnMap = new HashMap<String, String>();
		int wp = Integer.parseInt(cutSizeStr.split(FileParam.PIC_EXE_JOIN_STR)[0]);
		int hp = Integer.parseInt(cutSizeStr.split(FileParam.PIC_EXE_JOIN_STR)[1]);
		if(wp <= 0 || hp <= 0){
			throw new RuntimeException("图片剪裁参数不正确");
		}
		try {
			//ReadFileFromWhere fileFromWhere = new ReadFileFromCompanyFS();
			//InputStream inputStream = fileFromWhere.read(abstractFile.getLocalName());
			Map<String, String> extrParamMap = FilePathUtil.extractParameter(abstractFile);
			//物理路径
			String filePhysicalPath = extrParamMap.get(FileParam.FILE_PHYSICAL_PATH);
			String filePhysicalSubPath = extrParamMap.get(FileParam.SUB_BLOCK_PHYSICAL_PATH);
			//源图片
			File OrinImageFile = new File(filePhysicalPath);
			InputStream inputStream = new FileInputStream(OrinImageFile);
			try {
				//压缩图片 iphone 5 尺寸是320 * 568
				PicInfo pic = (PicInfo)abstractFile;
				int w = pic.getWidth();
				int h = pic.getHeight();
				float scale = getScale(wp, hp, w, h);
				ByteArrayOutputStream imgOut = new ByteArrayOutputStream();
				try {
					if(scale != 0){
						Thumbnails.of(inputStream).scale(scale).toOutputStream(imgOut);
					}
					ByteArrayInputStream compressImgIs = new ByteArrayInputStream(imgOut.toByteArray());
					try {
						ByteArrayOutputStream compressImgOut = new ByteArrayOutputStream();
						try {
							Thumbnails.of(compressImgIs).sourceRegion(Positions.CENTER, wp, hp).size(wp, hp).keepAspectRatio(false).toOutputStream(compressImgOut);
							//保存文件
							String picName = getPicName(wp, hp, CUT_IMAGE_PREFIX_NAME);
							byte[] imgOutData = compressImgOut.toByteArray();
							File file = new File(filePhysicalSubPath + picName);
							OutputStream out = new FileOutputStream(file);
							try {
								out.write(imgOutData);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								if(out != null){
									out.close();
								}
							}
							float size = ValueFormatUtil.fv((float)imgOutData.length / 1024);
							String displayName = abstractFile.getName();
							if(displayName.contains(".")){
								displayName = displayName.split("\\.")[0];
							}
							PicInfo picInfo = new PicInfo();
							picInfo.setName(displayName+ "-裁剪图片-" + wp + "-" + hp + "." + abstractFile.getExt());
							picInfo.setExt(FileParam.PIC_EXE_JPG);
							picInfo.setSize(size);
							picInfo.setWidth(wp);
							picInfo.setHeight(hp);
							picInfo.setBlockName(abstractFile.getBlockName());
							picInfo.setLocalName(picName);
							picInfo.setVisitAddr(picName);
							picInfo.setCreateTime(DateUtil.getCurrentDateTime());
							picInfo.setServerSn(abstractFile.getServerSn());
							//保存到数据库
							String sn = String.valueOf(fileService.save(picInfo));
							String visitUrl = picInfo.getVisitAddr();
							//返回信息
							returnMap.put(CUT_RETURN_ID_PREFIX, sn);
							returnMap.put(CUT_RETURN_URL_PREFIX, visitUrl);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if(compressImgOut != null){
								compressImgOut.close();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(compressImgIs != null){
							compressImgIs.close();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(imgOut != null){
						imgOut.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(inputStream != null){
					inputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 获取图片等比例压缩或放大系数
	 * *
	 */
	private float getScale(int wp, int hp, int w, int h) {
		float scale = 0f;
		int v = 0;
		int v_1 = 0;
		if(w < wp || h < hp){
			//先等比例放大
			if(w < wp && h >= hp){
				//等比例放大宽度
				v = w;
				v_1 = wp;
			} else if (w >= wp && h < hp){
				//等比例放大高度
				v = h;
				v_1 = hp;
			} else {
				//按最小的值等比例放大
				if(w < h){
					//按宽度缩放320
					v = w;
					v_1 = wp;
				} else {
					//按高度缩放568
					v = h;
					v_1 = hp;
				}
			}
		} else {
			//按高度等比例缩小
			v = h;
			v_1 = hp;
		}
		if(v != 0 && v_1 != 0){
			BigDecimal b1 = new BigDecimal(Float.toString(v_1));
			BigDecimal b2 = new BigDecimal(Float.toString(v));
			scale = b1.divide(b2, 1, BigDecimal.ROUND_UP).floatValue();
		}
		return scale;
	}

	/**
	 * *
	 * @param string
	 * @param paramMap
	 * @param vScreenshot
	 * @return
	 */
	private String getPicName(int width, int height, String tag){
		return getFileNamePrefix(fileServer, subBlockName) + tag + width + "-" + height + "." + FileParam.PIC_EXE_JPG;
	}

	@Override
	public String[] getReturnMapKeyAry() {
		return super.returnMapKeyAry;
	}
}
