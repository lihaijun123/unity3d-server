package com.focustech.cief.filemanage.app.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.app.dao.AppInfoDetailDao;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class AppBackstageDetailDaoImpl extends OssHibernateDaoSupport<AppInfoDetail> implements AppInfoDetailDao<AppInfoDetail>{

	@Override
	public AppInfoDetail selectByAppId(String appId) {
		Criteria c = getCurrentSession().createCriteria(AppInfoDetail.class);
		c.add(Restrictions.eq("appId", appId));
		return (AppInfoDetail) c.uniqueResult();
	}

	@Override
	public List<AppInfoDetail> list(Integer systemType) {
		Criteria c = getCurrentSession().createCriteria(AppInfoDetail.class);
		c.add(Restrictions.eq("status", 8)).add(Restrictions.eq("systemType", systemType));
		return c.list();
	}

	@Override
	public List<AppInfoDetail> selectByAppSn(Long appSn) {
		Criteria c = getCurrentSession().createCriteria(AppInfoDetail.class);
		c.add(Restrictions.eq("status", 8)).add(Restrictions.eq("app.sn", appSn));
		return c.list();
	}

	@Override
	public List<AppInfoDetail> listAll() {
		Criteria c = getCurrentSession().createCriteria(AppInfoDetail.class);
		//c.addOrder(Order.desc("updateTime"));
		return c.list();
	}

	@Override
	public Map<Long, Long> getAppFile(int num, int sizeLimit) {
		String sql = "select "
				   + " d.sn, "
				   + " d.app_file_sn "
				   + " from  "
				   + " biz_app_backstage_detail d "
				   + " where 1=1 "
				   + " and  d.app_size <= ? "
				   + " and d.app_file_sn is not null "
				   + " order by  "
				   + " d.update_time desc  limit 0, ?";
		List<Object[]> list = getCurrentSession().createSQLQuery(sql).setLong(0, sizeLimit).setInteger(1, num).list();
		Map<Long, Long> data = new HashMap<Long, Long>();
		for (Object[] d : list) {
			data.put(TCUtil.lv(d[0]), TCUtil.lv(d[1]));
		}
		return data;
	}

}
