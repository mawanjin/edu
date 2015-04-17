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
import com.thinkgem.jeesite.modules.edu.entity.Convenience;
import com.thinkgem.jeesite.modules.edu.dao.ConvenienceDao;

/**
 * 生活便利Service
 * @author lala
 * @version 2015-03-30
 */
@Component
@Transactional(readOnly = true)
public class ConvenienceService extends BaseService {

	@Autowired
	private ConvenienceDao convenienceDao;
	
	public Convenience get(String id) {
		return convenienceDao.get(id);
	}
	
	public Page<Convenience> find(Page<Convenience> page, Convenience convenience) {
		DetachedCriteria dc = convenienceDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(convenience.getTitle())){
			dc.add(Restrictions.like("title", "%"+convenience.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(Convenience.FIELD_DEL_FLAG, Convenience.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return convenienceDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Convenience convenience) {
		convenienceDao.save(convenience);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		convenienceDao.deleteById(id);
	}
	
}
