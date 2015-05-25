/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.edu.entity.Euser;
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

import java.util.List;

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
		if (gps.getUser()!=null&& org.apache.commons.lang3.StringUtils.isNotEmpty(gps.getUser().getName())){
			dc.add(Restrictions.like("user.name", "%"+gps.getUser().getName()+"%"));
		}
		dc.add(Restrictions.eq(Gps.FIELD_DEL_FLAG, Gps.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
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

	public List<Gps> findAll(String uid) {
        Gps gps = new Gps();
        Euser user = new Euser();
        user.setId(uid);
        gps.setUser(user);
        return find(new Page<Gps>(1, 50), gps).getList();
//		return gpsDao.find("from Gps where user.id=:p1 and del_flag =0 ",new Parameter(uid));
	}
}
