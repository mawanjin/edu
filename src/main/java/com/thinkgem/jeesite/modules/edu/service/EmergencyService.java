/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.edu.entity.Convenience;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.Emergency;
import com.thinkgem.jeesite.modules.edu.dao.EmergencyDao;

import java.util.List;

/**
 * 重要提示Service
 * @author lala
 * @version 2015-03-30
 */
@Component
@Transactional(readOnly = true)
public class EmergencyService extends BaseService {

	@Autowired
	private EmergencyDao emergencyDao;
	
	public Emergency get(String id) {
		return emergencyDao.get(id);
	}
	
	public Page<Emergency> find(Page<Emergency> page, Emergency emergency) {
		DetachedCriteria dc = emergencyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(emergency.getTitle())){
			dc.add(Restrictions.like("title", "%"+emergency.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(Emergency.FIELD_DEL_FLAG, Emergency.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return emergencyDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Emergency emergency) {
		emergencyDao.save(emergency);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		emergencyDao.deleteById(id);
	}

	public List<Emergency> findAll() {
		return emergencyDao.find("from Emergency where status=1 and del_flag=0 order by createDate desc");
	}
	@Transactional(readOnly = false)
	public void clearTop(Emergency emergency) {
		emergencyDao.update("update Emergency set top=0 where top=1 and id!=:p1",new Parameter(emergency.getId()));
	}
}
