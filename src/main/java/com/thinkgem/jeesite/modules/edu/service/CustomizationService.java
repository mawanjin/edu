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
import com.thinkgem.jeesite.modules.edu.entity.Customization;
import com.thinkgem.jeesite.modules.edu.dao.CustomizationDao;

import java.util.List;

/**
 * 私人定制模板Service
 * @author lala
 * @version 2015-04-15
 */
@Component
@Transactional(readOnly = true)
public class CustomizationService extends BaseService {

	@Autowired
	private CustomizationDao customizationDao;
	
	public Customization get(String id) {
		return customizationDao.get(id);
	}
	
	public Page<Customization> find(Page<Customization> page, Customization customization) {
		DetachedCriteria dc = customizationDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(customization.getName())){
			dc.add(Restrictions.like("name", "%"+customization.getName()+"%"));
		}
		dc.add(Restrictions.eq(Customization.FIELD_DEL_FLAG, Customization.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return customizationDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Customization customization) {
		customizationDao.save(customization);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		customizationDao.deleteById(id);
	}

	public List<Customization> findAll() {
		return customizationDao.find("from Customization where status=1 and del_flag=0 order by createDate desc");
	}
}
