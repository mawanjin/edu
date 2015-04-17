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
import com.thinkgem.jeesite.modules.edu.entity.Gps;
import com.thinkgem.jeesite.modules.edu.dao.GpsDao;

/**
 * gpsService
 * @author lala
 * @version 2015-04-12
 */
@Component
@Transactional(readOnly = true)
public class GpsService extends BaseService {

	@Autowired
	private GpsDao gpsDao;
	
	public Gps get(String id) {
		return gpsDao.get(id);
	}
	
	public Page<Gps> find(Page<Gps> page, Gps gps) {
		DetachedCriteria dc = gpsDao.createDetachedCriteria();
		if (gps.getUser()!=null){
			dc.add(Restrictions.like("user.name", "%"+gps.getUser().getName()+"%"));
		}
		dc.add(Restrictions.eq(Gps.FIELD_DEL_FLAG, Gps.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return gpsDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Gps gps) {
		gpsDao.clear();
		gpsDao.save(gps);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		gpsDao.deleteById(id);
	}
	
}
