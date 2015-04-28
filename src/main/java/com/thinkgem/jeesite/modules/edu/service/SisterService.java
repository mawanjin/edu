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
import com.thinkgem.jeesite.modules.edu.entity.Sister;
import com.thinkgem.jeesite.modules.edu.dao.SisterDao;

import java.util.List;

/**
 * 知心姐姐Service
 * @author lala
 * @version 2015-04-21
 */
@Component
@Transactional(readOnly = true)
public class SisterService extends BaseService {

	@Autowired
	private SisterDao sisterDao;
	
	public Sister get(String id) {
		return sisterDao.get(id);
	}
	
	public Page<Sister> find(Page<Sister> page, Sister sister) {
		DetachedCriteria dc = sisterDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(sister.getName())){
			dc.add(Restrictions.like("name", "%"+sister.getName()+"%"));
		}
		dc.add(Restrictions.eq(Sister.FIELD_DEL_FLAG, Sister.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return sisterDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Sister sister) {
		sisterDao.save(sister);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		sisterDao.deleteById(id);
	}

	public List<Sister> findAll() {
		return sisterDao.find("from Sister where status=1 and del_flag=0 ");
	}
}
