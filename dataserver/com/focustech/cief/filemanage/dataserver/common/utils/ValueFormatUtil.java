package com.focustech.cief.filemanage.dataserver.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
/**
 * 数值转换和格式化
 *
 * *
 * @author lihaijun
 *
 */
public class ValueFormatUtil {
	/**
	 * *
	 * @param iValue
	 * @return
	 */
	public static String sv(int iValue){
		return String.valueOf(iValue);
	}

	/**
	 * *
	 * @param objValue
	 * @return
	 */
	public static  String sv(Object objValue){
		if(null == objValue){
			return "";
		}
		return String.valueOf(objValue);
	}

	/**
	 * *
	 * @param bValue
	 * @return
	 */
	public static  String sv(boolean bValue){
		return String.valueOf(bValue);
	}

	/**
	 * *
	 * @param sValue
	 * @return
	 */
	public static  int iv(String sValue){
		if("".equals(sValue)){
			return -1;
		}
		return Integer.parseInt(sValue);
	}

	public static float fv(String value){
		if("".equals(value)){
			return 0;
		}
		return Float.parseFloat(value);
	}

	public static float fv(Object value){
		String sv = sv(value);
		if("".equals(sv)){
			return 0;
		}
		return Float.parseFloat(sv);
	}

	public static double dv(Object value){
		String sv = sv(value);
		if("".equals(sv)){
			return 0;
		}
		return Double.parseDouble(sv);
	}

	/**
	 * *
	 * @param map
	 * @param key
	 * @return
	 */
	protected String sv(Map<String, String> map, String key){
		if(!map.containsKey(key)){
			throw new RuntimeException("Map里面不存在key: " + key);
		}
		Object objValue = map.get(key);
		if(null == objValue){
			return "";
		}
		return String.valueOf(objValue);
	}

	/**
	 * *
	 * @param value
	 * @return
	 */
	 public static String formatFloat(float value) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         nf.setMinimumFractionDigits(2);
         nf.setMaximumFractionDigits(2);
         return nf.format(Double.valueOf(sv(value))).replace(",", "");
     }

	 /**
	  * *
	  * @param value
	  * @return
	  */
	 public static float fv(float value){
		 return fv(formatFloat(value));
	 }

	 public static String formatDouble(double value) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         nf.setMinimumFractionDigits(2);
         nf.setMaximumFractionDigits(2);
         return nf.format(value).replace(",", "");
     }

	 public static double formatDouble(float value) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         nf.setMinimumFractionDigits(2);
         nf.setMaximumFractionDigits(2);
         return Double.parseDouble(nf.format(value).replace(",", ""));
     }

	 public static BigDecimal formatBigDeci(float value) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         nf.setMinimumFractionDigits(2);
         nf.setMaximumFractionDigits(2);
         return new BigDecimal(Double.parseDouble(nf.format(value).replace(",", "")));
     }

	 public static void main(String[] arg){
		 System.out.println(ValueFormatUtil.formatBigDeci(3333f/1024));
	 }
}
