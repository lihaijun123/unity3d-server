package com.focustech.cief.filemanage.dataserver.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.focustech.cief.filemanage.dataserver.common.encrypter.EncryptComponent;
import com.focustech.cief.filemanage.dataserver.common.urlstatic.FieldCoder;
import com.focustech.core.utils.StringUtils;

/**
 */
public class EncryptUtil implements FieldCoder {

    static final Log log = LogFactory.getLog(EncryptUtil.class);

    /**
     * 对加密字符串进行解密，返回long型 Create Time: 2005-12-13 23:12:52
     *
     * @author JimCheng/成俊杰
     */
    final public static Long decode(String idEncrypt) throws Exception {
        EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
        return encryptComponent.decode(idEncrypt);
    }

    /**
     * 解密后返回字符串
     *
     * @param str
     * @return
     */
    final public static String decode2Str(String idEncrypt) {
        if (NumberUtils.isNumber(idEncrypt)) {
            return idEncrypt;
        }
        EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
        Long decodeId = encryptComponent.decode(idEncrypt);
        return decodeId == null ? null : String.valueOf(decodeId);
    }

    /**
     * @param idEncrypt
     * @return
     * @throws Exception
     */
    final public static Long decodeImageID(String idEncrypt) throws Exception {
        EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
        return encryptComponent.decodeImageID(idEncrypt);
    }

    /**
     * @param idEncrypt
     * @return
     */
    final public static String decodeImageID2Str(String idEncrypt) {
        EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
        Long decodeId = encryptComponent.decodeImageID(idEncrypt);
        return decodeId == null ? null : String.valueOf(decodeId);
    }

    /**
     * @param str
     * @param splitStr
     * @return
     */
    public static String[] decodeMulti2Array(String str, String splitStr) {
        return (String[]) decodeMulti2List(str, splitStr).toArray();
    }

    /**
     * @param strs
     * @return
     */
    public static String[] decodeMulti2Array(String[] strs) {
        String[] result = null;
        if ((strs != null) && (strs.length != 0)) {
            result = new String[strs.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = decode2Str(strs[i]);
            }
        }
        return result;
    }

    /**
     * 解密ID 的List
     *
     * @param strs
     * @return
     */
    public static List<String> decodeMulti2List(List<String> strs) {
        List<String> decodeIdList = null;
        if ((strs != null) && (strs.size() > 0)) {
            decodeIdList = new ArrayList<String>();
            for (String encodeId : strs) {
                decodeIdList.add(decode2Str(encodeId));
            }
        }
        return decodeIdList;
    }

    /**
     * @param str
     * @param splitStr
     * @return
     */
    public static List<String> decodeMulti2List(String str, String splitStr) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(str)) {
            String[] strArray = StringUtils.split(str, splitStr);
            for (int i = 0; i < strArray.length; i++) {
                String decodeStr = decode2Str(strArray[i]);
                if ((decodeStr != null) && !"".equals(decodeStr)) {
                    result.add(decodeStr);
                }
            }
        }
        return result;
    }

    /**
     * @param str
     * @param splitStr
     * @return
     */
    public static String decodeMulti2Str(String str, String splitStr) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        else {
            StringBuffer sb = new StringBuffer();
            String[] strArray = StringUtils.split(str, splitStr);
            for (int i = 0; i < strArray.length; i++) {
                String decodeStr = decode2Str(strArray[i]);
                if ((decodeStr != null) && !"".equals(decodeStr)) {
                    sb.append(splitStr).append(decodeStr);
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
            return sb.toString();
        }
    }

    /**
     * @param long_id
     * @return
     */
    final public static String encode(Long long_id) {
        EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
        return encryptComponent.encode(long_id);
    }

    /**
     * @param string_id
     * @return
     */
    final public static String encode(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        else {
            EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
            return encryptComponent.encode(new Long(id));
        }
    }

    /**
     * @param id
     * @return
     */
    final static public String encodeImageID(Long id) {
        EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
        return encryptComponent.encodeImageID(id);
    }

    /**
     * @param string_id
     * @return
     */
    final public static String encodeImageID(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        else {
            EncryptComponent encryptComponent = (EncryptComponent) FocusContainerTool.lookup(EncryptComponent.class);
            return encryptComponent.encodeImageID(new Long(id));
        }
    }

    /**
     * @param strs
     * @return
     */
    public static String[] encodeMulti2Array(String[] strs) {
        String[] result = null;
        if ((strs != null) && (strs.length != 0)) {
            result = new String[strs.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = encode(strs[i]);
            }
        }
        return result;
    }

    /**
     * @param strIds
     * @param split
     * @return
     */
    public static String encodeMulti2Str(String strIds, String split) {
        if ((strIds == null) || "".equals(strIds)) {
            return strIds;
        }
        else {
            StringBuffer sb = new StringBuffer();
            String[] arrayIds = StringUtils.split(strIds, split);
            for (int i = 0; i < arrayIds.length; i++) {
                sb.append(split).append(encode(arrayIds[i]));
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
            return sb.toString();
        }
    }

    /**
     *
     */
    public String decode(String val, String defaultVal) {
        try {
            if (StringUtils.isBlank(val)) {
                return "";
            }
            else {
                return String.valueOf(decode(val));
            }
        }
        catch (Exception e) {
            return "";
        }
    }

    public String encode(String val, String defaultVal) {
        if (StringUtils.isBlank(val)) {
            return "";
        }
        else {
            return encode(new Long(val));
        }
    }

    /**
     * 批量邮件中的字符串加密算法
     *
     * @param str 待加密字符串
     * @return
     * @author zhangtingchang
     * @since 2010-2-8
     * @see com.focustech.utils.encrypt.MIC2005EncryptHandler2#encryptText(String)
     */
    public static String encodeText(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return "";//new MIC2005EncryptHandler2().encryptText(str);

    }

    /**
     * 批量邮件中的符串解密算法
     *
     * @param str 待解密字符串
     * @return
     * @author zhangtingchang
     * @since 2010-2-8
     * @see com.focustech.utils.encrypt.MIC2005EncryptHandler2#decryptText(String)
     */

    public static String decodeText(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String result = null;
        try {
            result = "";//new MIC2005EncryptHandler2().decryptText(str);
        }
        catch (Exception e) {
            log.error("oss decode error", e);
            result = null;
        }
        finally {
            return result;
        }
    }
}
