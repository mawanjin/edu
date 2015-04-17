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
import com.thinkgem.jeesite.modules.edu.entity.Inquiry;
import com.thinkgem.jeesite.modules.edu.dao.InquiryDao;

/**
 * 留言咨询Service
 * @author lala
 * @version 2015-04-09
 */
@Component
@Transactional(readOnly = true)
public class InquiryService extends BaseService {

	@Autowired
	private InquiryDao inquiryDao;
	
	public Inquiry get(String id) {
		return inquiryDao.get(id);
	}
	
	public Page<Inquiry> find(Page<Inquiry> page, Inquiry inquiry) {
		DetachedCriteria dc = inquiryDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(inquiry.getTitle())){
			dc.add(Restrictions.like("title", "%"+inquiry.getTitle()+"%"));
		}
		dc.add(Restrictions.eq("type", inquiry.getType()));
		dc.add(Restrictions.eq(Inquiry.FIELD_DEL_FLAG, Inquiry.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return inquiryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Inquiry inquiry) {
		inquiryDao.save(inquiry);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		inquiryDao.deleteById(id);
	}
	
}
