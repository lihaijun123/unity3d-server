package com.focustech.cief.filemanage.log.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.log.dao.StatScanImgLogDao;
import com.focustech.cief.filemanage.log.model.StatScanImgLog;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class StatScanImgLogDaoImpl extends OssHibernateDaoSupport<StatScanImgLog> implements StatScanImgLogDao<StatScanImgLog> {

	@Override
	public List<Map<String, String>> getChartData(String startDay, String endDay, Map<String, Object> extendParam) {
		String sql = " SELECT "
                   + " COUNT(sn) AS invoke_count, "
                   + " pic_name "
                   + " FROM stat_scan_img_log "
                   + " GROUP BY pic_name "
                   + " ORDER BY pic_name ASC";

		List list = getCurrentSession().createSQLQuery(sql).list();
		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
		for (Object object : list) {
			Map<String, String> map = new HashMap<String, String>();
			if(object instanceof Object[]){
				Object[] objAry = (Object[])object;
				map.put("invoke_count", TCUtil.sv(objAry[0]));
				map.put("pic_name", TCUtil.sv(objAry[1]));
				mapList.add(map);
			}
		}
		return mapList;
	}

}
