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
import com.thinkgem.jeesite.modules.edu.entity.Abroadhome;
import com.thinkgem.jeesite.modules.edu.dao.AbroadhomeDao;

/**
 * 个人问答Service
 * @author lala
 * @version 2015-04-07
 */
@Component
@Transactional(readOnly = true)
public class AbroadhomeService extends BaseService {

	@Autowired
	private AbroadhomeDao abroadhomeDao;
	
	public Abroadhome get(String id) {
		return abroadhomeDao.get(id);
	}
	
	public Page<Abroadhome> find(Page<Abroadhome> page, Abroadhome abroadhome) {
		DetachedCriteria dc = abroadhomeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(abroadhome.getTitle())){
			dc.add(Restrictions.like("name", "%"+abroadhome.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(Abroadhome.FIELD_DEL_FLAG, Abroadhome.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return abroadhomeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Abroadhome abroadhome) {
		abroadhomeDao.save(abroadhome);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		abroadhomeDao.deleteById(id);
	}
	
}
