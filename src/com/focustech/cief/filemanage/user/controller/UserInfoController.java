package com.focustech.cief.filemanage.user.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.dataserver.common.utils.DownloadUtil;
import com.focustech.cief.filemanage.user.model.UserInfo;
import com.focustech.cief.filemanage.user.service.UserInfoService;
import com.focustech.common.utils.DateUtils;

/**
 * 
 * *
 * 
 * @author lihaijun
 * 
 * */
@Controller
@RequestMapping(value = "/fs/user")
public class UserInfoController {
	@Autowired
	private UserInfoService<UserInfo> userInfoService;
	
	private static final String[] xlsColumnAry = new String[]{"序号", "AppName", "手机号", "邮箱", "注册时间"};
	/**
	 * 
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		modelMap.put("list", userInfoService.list());
    	return "/user/list";
    }
	/**
	 * 
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/list/export", method = RequestMethod.GET)
	public void export(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resource resource = new ClassPathResource("/context/user/list/export");
		File dirFile = resource.getFile();
		if(dirFile.exists()){
			try {
				List<UserInfo> list = userInfoService.list();
				String xlsFileName = "注册用户列表_" + DateUtils.getCurDate("yyyyMMddHHmmss")+ ".xls";
				String xlsPath = dirFile.getPath() + xlsFileName;
				resultSetToExcel(xlsPath, "列表", list);
				DownloadUtil.download(xlsPath, xlsFileName, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * *
	 * @param filePath
	 * @param sheetName
	 * @param list
	 * @throws Exception
	 */
	private void resultSetToExcel(String filePath, String sheetName, List<UserInfo> list) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth((short)1, (short) (10 * 256));
		sheet.setColumnWidth((short)2, (short) (30 * 256));
		sheet.setColumnWidth((short)3, (short) (30 * 256));
		sheet.setColumnWidth((short)4, (short) (30 * 256));
		sheet.setColumnWidth((short)5, (short) (30 * 256));
		workbook.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);
		HSSFRow row = sheet.createRow((short) 0);
		row.setHeight((short) (2 * 256));
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = null;
		/*HSSFFont font = workbook.createFont();
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); */
		
		int nColumn = xlsColumnAry.length;
		// 写入各个字段的名称
		for (int i = 0; i < nColumn; i++) {
			cell = row.createCell((short) i);
			cellStyle = cell.getCellStyle();
			//cellStyle.setFont(font); 
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(xlsColumnAry[i]);
		}
		// 写入各条记录，每条记录对应Excel中的一行
		for (int j = 1; j <= list.size(); j++) {
			UserInfo userInfo = list.get(j - 1);
			row = sheet.createRow((short) j);
			row.setHeight((short) (1 * 256));
			cell = row.createCell((short) 0);
			cellStyle = cell.getCellStyle();
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(j);
			cell = row.createCell((short) 1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(userInfo.getName());
			cell = row.createCell((short) 2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(userInfo.getMobile());
			cell = row.createCell((short) 3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(userInfo.getEmail());
			cell = row.createCell((short) 4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(DateUtils.formatDate(userInfo.getAddTime(), DateUtils.DEFAULT_FORMATE_ALL));
		}
		FileOutputStream fOut = new FileOutputStream(filePath);
		workbook.write(fOut);
		fOut.flush();
		fOut.close();
	}
}
