package com.focustech.cief.filemanage.dataserver.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.focustech.cief.filemanage.dataserver.dao.IBaseFileDao;
import com.focustech.cief.filemanage.dataserver.dao.OssHibernateDaoSupport;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
/**
 * *
 * @author lihaijun
 *
 */
@Repository
public class BaseFileDaoImpl extends OssHibernateDaoSupport<AbstractFile> implements IBaseFileDao<AbstractFile> {
	@Autowired
	private DataServerNode dataServerNode;
	@Override
	public AbstractFile getFile(String subFoldName, String fileName) {
		String hql = " FROM " + AbstractFile.class.getName() + " WHERE visitAddr LIKE '%" + subFoldName + "/" + fileName  + "'";
		AbstractFile abstractFile = (AbstractFile)getCurrentSession().createQuery(hql).uniqueResult();
		if(abstractFile != null){
			abstractFile.setServerNode(dataServerNode);
		}
		return abstractFile;
	}

	@Override
	public void clearSession() {
		getCurrentSession().clear();
	}

	@Override
	public long getFileSnByVisitAddr(String visitAddr) {
		Criteria c = getCurrentSession().createCriteria(AbstractFile.class.getName());
		c.add(Restrictions.eq("visitAddr", visitAddr));
		List list = c.list();
		if(!list.isEmpty()){
			return ((AbstractFile)list.get(0)).getSn();
		}
		throw new RuntimeException("没有查询到访问路径为：" + visitAddr + " 的文件");
	}

	@Override
	public Map<String, String> selectById(long id) {
		String sql = " SELECT "
				   + " file_data_.SN, "
				   + " file_data_.VISIT_ADDR, "
				   + " file_data_.LENGTH, "
				   + " file_data_.WIDTH, "
				   + " file_data_.HEIGHT, "
				   + " file_data_.TYPE, "
				   + " file_data_.NAME, "
				   + " file_data_.SIZE, "
				   + " file_data_.EXT, "
				   + " file_data_.PARENT_FILE_SN, "
				   + " parent_file_data_.NAME PARENT_FILE_NAME "
				   + " FROM com_file_data file_data_ "
				   + " LEFT JOIN com_file_data parent_file_data_ "
				   + " ON file_data_.PARENT_FILE_SN = parent_file_data_.SN "
				   + " WHERE file_data_.sn = :sn ";
		List<Object[]> list = (List<Object[]>)getCurrentSession().createSQLQuery(sql).setLong("sn", id).list();
		Object[] objAry = null;
		if(list.isEmpty()){
			objAry = new Object[]{"", "", "", "", "", "", "", "", "", "", ""};
		} else {
			objAry = list.get(0);
		}
		Map<String, String> rMap = new HashMap<String, String>();
		rMap.put("VISIT_ADDR", String.valueOf(objAry[1]));
		rMap.put("LENGTH", String.valueOf(objAry[2]));
		rMap.put("WIDTH", String.valueOf(objAry[3]));
		rMap.put("HEIGHT", String.valueOf(objAry[4]));
		rMap.put("TYPE", String.valueOf(objAry[5]));
		rMap.put("NAME", String.valueOf(objAry[6]));
		rMap.put("SIZE", String.valueOf(objAry[7]));
		rMap.put("EXT", String.valueOf(objAry[8]));
		rMap.put("PARENT_FILE_SN", String.valueOf(objAry[9]));
		rMap.put("PARENT_FILE_NAME", String.valueOf(objAry[10]));
		return rMap;
	}

	@Override
	public float countSize(String ids) {
		String sql = " SELECT "
			       + " sum(file_data_.SIZE) AS totalSize "
			       + " FROM com_file_data file_data_ "
			       + " WHERE file_data_.sn in (" + ids + ")";
		Object uniqueResult = getCurrentSession().createSQLQuery(sql).uniqueResult();
		if(null == uniqueResult){
			return 0;
		}
		return Float.valueOf(uniqueResult.toString());
	}

	@Override
	public List<Map<String, String>> selectAll() {
		String sql = " SELECT "
				+ " file_data_.SN, "
				+ " file_data_.VISIT_ADDR, "
				+ " file_data_.LENGTH, "
				+ " file_data_.WIDTH, "
				+ " file_data_.HEIGHT, "
				+ " file_data_.TYPE, "
				+ " file_data_.NAME, "
				+ " file_data_.SIZE, "
				+ " file_data_.EXT, "
				+ " file_data_.PARENT_FILE_SN, "
				+ " parent_file_data_.NAME PARENT_FILE_NAME "
				+ " FROM com_file_data file_data_ "
				+ " LEFT JOIN com_file_data parent_file_data_ "
				+ " ON file_data_.PARENT_FILE_SN = parent_file_data_.SN ";
		List<Map<String, String>> rvMapList = new ArrayList<Map<String,String>>();
		List<Object[]> list = (List<Object[]>) getCurrentSession().createSQLQuery(sql).list();
		for (Object[] objAry : list) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("SN", String.valueOf(objAry[0]));
			rMap.put("VISIT_ADDR", String.valueOf(objAry[1]));
			rMap.put("LENGTH", String.valueOf(objAry[2]));
			rMap.put("WIDTH", String.valueOf(objAry[3]));
			rMap.put("HEIGHT", String.valueOf(objAry[4]));
			rMap.put("TYPE", String.valueOf(objAry[5]));
			rMap.put("NAME", String.valueOf(objAry[6]));
			rMap.put("SIZE", String.valueOf(objAry[7]));
			rMap.put("EXT", String.valueOf(objAry[8]));
			rMap.put("PARENT_FILE_SN", String.valueOf(objAry[9]));
			rMap.put("PARENT_FILE_NAME", String.valueOf(objAry[10]));
			rvMapList.add(rMap);
		}
		return rvMapList;
	}


	@Override
	public AbstractFile fetchFile(String serverId, String blockId, String cfsFileName) {
		String hql = " FROM " + AbstractFile.class.getName()
		+ " WHERE serverSn = " + serverId
		+ " and blockName = '" + blockId + "'"
		+ " and visitAddr = '" + cfsFileName  + "'";
		AbstractFile abstractFile = (AbstractFile)getCurrentSession().createQuery(hql).uniqueResult();
		if(abstractFile != null){
			abstractFile.setServerNode(dataServerNode);
		}
		return abstractFile;
	}
	@Override
	public List<AbstractFile> list() {
		String hql = " FROM " + AbstractFile.class.getName();
		List<AbstractFile> list = (List<AbstractFile>)getCurrentSession().createQuery(hql).list();
		for (AbstractFile abstractFile : list) {
			abstractFile.setServerNode(dataServerNode);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getInitList(Long serverSn, int size, long fileLimitSize) {
		String sql = "select "
				   + " file_data_.SN, "
				   + " file_data_.VISIT_ADDR, "
				   + " file_data_.LENGTH, "
				   + " file_data_.WIDTH, "
				   + " file_data_.HEIGHT, "
				   + " file_data_.TYPE, "
				   + " file_data_.NAME, "
				   + " file_data_.SIZE, "
				   + " file_data_.EXT, "
				   + " file_data_.PARENT_FILE_SN, "
				   + " s.FILE_ROOT_PATH, "
				   + " s.ROOT_FOLD_NAME, "
				   + " file_data_.BLOCK_NAME, "
				   + " file_data_.LOCAL_NAME, "
				   + " parent_file_data_.NAME PARENT_FILE_NAME "
			       + " from com_file_data file_data_ "
			       + " left join com_file_server s "
			       + " on file_data_.SERVER_SN = s.SN "
			       + " LEFT JOIN com_file_data parent_file_data_ "
				   + " ON file_data_.PARENT_FILE_SN = parent_file_data_.SN "
			       + " where 1=1"
			       + " and file_data_.SIZE <= ? "
			       + " and file_data_.SERVER_SN=?"
			       + " order by "
			       + " file_data_.VISIT_FREQUENCY desc , file_data_.SN desc limit 0, ?";
		List<AbstractFile> files = new ArrayList<AbstractFile>();
		List<Object[]> list = (List<Object[]>) getCurrentSession().createSQLQuery(sql).setLong(0, fileLimitSize).setLong(1, serverSn).setInteger(2, size).list();
		/*AbstractFile file = null;
		for (Object[] objAry : list) {
			file = new OtherFile();
			file.setSn(TCUtil.lv(objAry[0]));
			file.setVisitAddr(String.valueOf(objAry[1]));
			file.setName(String.valueOf(objAry[6]));
			file.setSize(TCUtil.fv((objAry[7])));
			file.setExt(String.valueOf(objAry[8]));
			file.setBlockName(String.valueOf(objAry[12]));
			file.setLocalName(String.valueOf(objAry[13]));
			DataServerNode server = new DataServerNode();
			server.setFileRootPath(String.valueOf(objAry[10]));
			server.setRootFoldName(String.valueOf(objAry[11]));
			file.setServerNode(server);
			files.add(file);
		}*/
		List<Map<String, String>> ls = new ArrayList<Map<String,String>>();
		for (Object[] objAry : list) {
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("SN", TCUtil.sv(objAry[0]));
			rMap.put("VISIT_ADDR", TCUtil.sv(objAry[1]));
			rMap.put("NAME", TCUtil.sv(objAry[6]));
			rMap.put("SIZE", TCUtil.sv(objAry[7]));
			rMap.put("EXT", TCUtil.sv(objAry[8]));
			rMap.put("BLOCK_NAME", TCUtil.sv(objAry[12]));
			rMap.put("LOCAL_NAME", TCUtil.sv(objAry[13]));
			rMap.put("FILE_ROOT_PATH", TCUtil.sv(objAry[10]));
			rMap.put("ROOT_FOLD_NAME", TCUtil.sv(objAry[11]));
			rMap.put("LENGTH", String.valueOf(objAry[2]));
			rMap.put("WIDTH", String.valueOf(objAry[3]));
			rMap.put("HEIGHT", String.valueOf(objAry[4]));
			rMap.put("TYPE", String.valueOf(objAry[5]));
			rMap.put("NAME", String.valueOf(objAry[6]));
			rMap.put("PARENT_FILE_SN", String.valueOf(objAry[9]));
			rMap.put("PARENT_FILE_NAME", String.valueOf(objAry[14]));
			ls.add(rMap);
		}
		return ls;
	}

	@Override
	public List<AbstractFile> list(String fileName) {
		Criteria criteria = getCurrentSession().createCriteria(AbstractFile.class);
		criteria.add(Restrictions.eq("name", fileName)).addOrder(Order.desc("createTime"));
		return criteria.list();
	}

}
