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
import com.thinkgem.jeesite.modules.edu.entity.AbroadhomeTop;
import com.thinkgem.jeesite.modules.edu.dao.AbroadhomeTopDao;

/**
 * 海外之前图片Service
 * @author lala
 * @version 2015-04-07
 */
@Component
@Transactional(readOnly = true)
public class AbroadhomeTopService extends BaseService {

	@Autowired
	private AbroadhomeTopDao abroadhomeTopDao;
	
	public AbroadhomeTop get(String id) {
		return abroadhomeTopDao.get(id);
	}
	
	public Page<AbroadhomeTop> find(Page<AbroadhomeTop> page, AbroadhomeTop abroadhomeTop) {
		DetachedCriteria dc = abroadhomeTopDao.createDetachedCriteria();

		dc.add(Restrictions.eq(AbroadhomeTop.FIELD_DEL_FLAG, AbroadhomeTop.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return abroadhomeTopDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(AbroadhomeTop abroadhomeTop) {
		abroadhomeTopDao.save(abroadhomeTop);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		abroadhomeTopDao.deleteById(id);
	}
	
}
