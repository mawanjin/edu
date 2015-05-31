/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Parameter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.School;
import com.thinkgem.jeesite.modules.edu.dao.SchoolDao;

import java.util.List;

/**
 * 学校Service
 * @author lala
 * @version 2015-04-01
 */
@Component
@Transactional(readOnly = true)
public class SchoolService extends BaseService {

	@Autowired
	private SchoolDao schoolDao;
	
	public School get(String id) {
		return schoolDao.get(id);
	}

	public List<School> findByCountry(String id){
		return schoolDao.find("from School where country.id=:p1 and status=1 and del_flag=0 order by createDate desc ",new Parameter(id));
	}
	
	public Page<School> find(Page<School> page, School school) {
		DetachedCriteria dc = schoolDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(school.getName())){
			dc.add(Restrictions.like("name", "%"+school.getName()+"%"));
		}
		dc.add(Restrictions.eq(School.FIELD_DEL_FLAG, School.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));

		return schoolDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(School school) {
		schoolDao.save(school);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		schoolDao.deleteById(id);
	}

	public List<School> findAll() {
		return schoolDao.findAll();
	}

	public List<School> findAllSchool(){
		return schoolDao.find("from School where status=1 and del_flag=0 order by createDate desc");
	}
}
