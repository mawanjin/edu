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
import com.thinkgem.jeesite.modules.edu.entity.Major;
import com.thinkgem.jeesite.modules.edu.dao.MajorDao;

import java.util.List;

/**
 * 专业Service
 * @author lala
 * @version 2015-04-01
 */
@Component
@Transactional(readOnly = true)
public class MajorService extends BaseService {

	@Autowired
	private MajorDao majorDao;
	
	public Major get(String id) {
		return majorDao.get(id);
	}
	
	public Page<Major> find(Page<Major> page, Major major) {
		DetachedCriteria dc = majorDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(major.getName())){
			dc.add(Restrictions.like("name", "%"+major.getName()+"%"));
		}
		dc.add(Restrictions.eq(Major.FIELD_DEL_FLAG, Major.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return majorDao.find(page, dc);
	}

	public List<Major> findAll(){
		return majorDao.findAll();
	}
	
	@Transactional(readOnly = false)
	public void save(Major major) {
		majorDao.save(major);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		majorDao.deleteById(id);
	}
	
}
