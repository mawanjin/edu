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
import com.thinkgem.jeesite.modules.edu.entity.InquiryReply;
import com.thinkgem.jeesite.modules.edu.dao.InquiryReplyDao;

/**
 * 留言回复Service
 * @author lala
 * @version 2015-04-09
 */
@Component
@Transactional(readOnly = true)
public class InquiryReplyService extends BaseService {

	@Autowired
	private InquiryReplyDao inquiryReplyDao;
	
	public InquiryReply get(String id) {
		return inquiryReplyDao.get(id);
	}
	
	public Page<InquiryReply> find(Page<InquiryReply> page, InquiryReply inquiryReply) {
		DetachedCriteria dc = inquiryReplyDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(inquiryReply.getName())){
//			dc.add(Restrictions.like("name", "%"+inquiryReply.getName()+"%"));
//		}
		dc.add(Restrictions.eq("inquiry.id", inquiryReply.getInquiry().getId()));
		dc.add(Restrictions.eq(InquiryReply.FIELD_DEL_FLAG, InquiryReply.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return inquiryReplyDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(InquiryReply inquiryReply) {
		inquiryReplyDao.save(inquiryReply);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		inquiryReplyDao.deleteById(id);
	}
	
}
