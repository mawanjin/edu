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
import com.thinkgem.jeesite.modules.edu.entity.Aboutus;
import com.thinkgem.jeesite.modules.edu.dao.AboutusDao;

/**
 * 关于我们Service
 * @author lala
 * @version 2015-04-15
 */
@Component
@Transactional(readOnly = true)
public class AboutusService extends BaseService {

	@Autowired
	private AboutusDao aboutusDao;
	
	public Aboutus get(String id) {
		return aboutusDao.get(id);
	}
	
	public Page<Aboutus> find(Page<Aboutus> page, Aboutus aboutus) {
		DetachedCriteria dc = aboutusDao.createDetachedCriteria();

		dc.add(Restrictions.eq(Aboutus.FIELD_DEL_FLAG, Aboutus.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return aboutusDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Aboutus aboutus) {
		aboutusDao.save(aboutus);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		aboutusDao.deleteById(id);
	}
	
}
