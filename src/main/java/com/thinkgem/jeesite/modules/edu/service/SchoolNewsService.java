/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.SchoolNews;
import com.thinkgem.jeesite.modules.edu.dao.SchoolNewsDao;

import java.util.List;

/**
 * 学校新闻Service
 * @author lala
 * @version 2015-04-26
 */
@Component
@Transactional(readOnly = true)
public class SchoolNewsService extends BaseService {

	@Autowired
	private SchoolNewsDao schoolNewsDao;
	
	public SchoolNews get(String id) {
		return schoolNewsDao.get(id);
	}
	
	public Page<SchoolNews> find(Page<SchoolNews> page, SchoolNews schoolNews) {
		DetachedCriteria dc = schoolNewsDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(schoolNews.getTitle())){
			dc.add(Restrictions.like("title", "%"+schoolNews.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(SchoolNews.FIELD_DEL_FLAG, SchoolNews.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return schoolNewsDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(SchoolNews schoolNews) {
		schoolNewsDao.clear();
		schoolNewsDao.save(schoolNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		schoolNewsDao.deleteById(id);
	}

	/**
	 * 取消数据库中所有已置顶TOP的记录
	 */
	@Transactional(readOnly = false)
	public void removeTop(SchoolNews schoolNews){
		schoolNewsDao.clear();
		schoolNewsDao.update("update SchoolNews set top='0' where top='1' and id!='"+schoolNews.getId()+"'");
	}

	public List<SchoolNews> findBySchool(String schoolId) {
		return schoolNewsDao.find("from SchoolNews where school.id=:p1 and status=1 and del_flag=0",new Parameter(new String[]{schoolId}));
	}
}
