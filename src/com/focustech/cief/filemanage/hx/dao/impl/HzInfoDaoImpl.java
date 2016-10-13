package com.focustech.cief.filemanage.hx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.hx.dao.HxInfoDao;
import com.focustech.cief.filemanage.hx.model.HxInfo;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Repository
public class HzInfoDaoImpl extends OssHibernateDaoSupport<HxInfo> implements HxInfoDao<HxInfo> {

	@Override
	public List<HxInfo> list(long xmlInfoSn) {
		String sql =
			 " select " +
			 " _hx_info.sn as hxSn, " +
			 " _hx_info.name as hxName,   " +
			 " _xml_detail.name as xmlDetailName,   " +
			 " _hx_info.parent_sn as hxParentSn   " +
			 "  from    " +
			 " hx_info _hx_info " +
			 " left join  xml_detail _xml_detail " +
			 " on _hx_info.xml_id = _xml_detail.sn " +
			 " left join xml_info _xml_info   " +
			 " on _xml_detail.xml_info_sn = _xml_info.sn ";
			if(xmlInfoSn > 0){
				sql += " and _xml_info.sn = ? ";
			}
			sql += " order by _hx_info.add_time  desc ";

		List<HxInfo> hxList = new ArrayList<HxInfo>();
		SQLQuery createSQLQuery = getCurrentSession().createSQLQuery(sql);
		if(xmlInfoSn > 0){
			createSQLQuery.setLong(0, xmlInfoSn);
		}
		List<Object[]> list = createSQLQuery.list();
		Object[] objAry = null;
		if(!list.isEmpty()){
			for(Object[] obj : list){
				HxInfo hxInfo = new HxInfo();
				hxInfo.setSn(TCUtil.lv(obj[0]));
				hxInfo.setName(TCUtil.sv(obj[1]));
				hxInfo.setXmlDetailName(TCUtil.sv(obj[2]));
				hxInfo.setParentSn(TCUtil.lv(obj[3]));
				hxList.add(hxInfo);
			}
		}
		return hxList;
	}

	@Override
	public List<HxInfo> listChild(long parentSn) {
		Criteria criteria = getCurrentSession().createCriteria(HxInfo.class);
		criteria.add(Restrictions.eq("parentSn", parentSn));
		return criteria.list();
	}

	@Override
	public void delteChilds(long parentSn) {
		String hql = "delete " + HxInfo.class.getName() + " _hx_info where _hx_info.parentSn=?";
		getCurrentSession().createQuery(hql).setLong(0, parentSn).executeUpdate();

	}

	@Override
	public List<HxInfo> selectByXmlId(long xmlInfoSn) {
		Criteria criteria = getCurrentSession().createCriteria(HxInfo.class);
		criteria.add(Restrictions.eq("xmlId", xmlInfoSn));
		return criteria.list();
	}

}
