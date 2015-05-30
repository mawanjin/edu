/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.Teacher;
import com.thinkgem.jeesite.modules.edu.dao.TeacherDao;

import java.util.List;

/**
 * 名师Service
 * @author lala
 * @version 2015-04-01
 */
@Component
@Transactional(readOnly = true)
public class TeacherService extends BaseService {

	@Autowired
	private TeacherDao teacherDao;
	
	public Teacher get(String id) {
		return teacherDao.get(id);
	}
	
	public Page<Teacher> find(Page<Teacher> page, Teacher teacher) {
		DetachedCriteria dc = teacherDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(teacher.getName())){
			dc.add(Restrictions.like("name", "%"+teacher.getName()+"%"));
		}
		dc.add(Restrictions.eq(Teacher.FIELD_DEL_FLAG, Teacher.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return teacherDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Teacher teacher) {
		teacherDao.save(teacher);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		teacherDao.deleteById(id);
	}

	public List<Teacher> findAll() {
		return teacherDao.find("from Teacher where status=1 and del_flag=0 order by createDate desc");
	}
}
