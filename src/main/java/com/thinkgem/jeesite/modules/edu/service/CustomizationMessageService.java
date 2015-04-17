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
import com.thinkgem.jeesite.modules.edu.entity.CustomizationMessage;
import com.thinkgem.jeesite.modules.edu.dao.CustomizationMessageDao;

/**
 * 私人定制留言Service
 * @author lala
 * @version 2015-04-15
 */
@Component
@Transactional(readOnly = true)
public class CustomizationMessageService extends BaseService {

	@Autowired
	private CustomizationMessageDao customizationMessageDao;
	
	public CustomizationMessage get(String id) {
		return customizationMessageDao.get(id);
	}
	
	public Page<CustomizationMessage> find(Page<CustomizationMessage> page, CustomizationMessage customizationMessage) {
		DetachedCriteria dc = customizationMessageDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(customizationMessage.getMsg())){
			dc.add(Restrictions.like("msg", "%"+customizationMessage.getMsg()+"%"));
		}
		dc.add(Restrictions.eq(CustomizationMessage.FIELD_DEL_FLAG, CustomizationMessage.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return customizationMessageDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomizationMessage customizationMessage) {
		customizationMessageDao.save(customizationMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		customizationMessageDao.deleteById(id);
	}
	
}
