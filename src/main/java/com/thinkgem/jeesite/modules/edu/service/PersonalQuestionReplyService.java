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
import com.thinkgem.jeesite.modules.edu.entity.PersonalQuestionReply;
import com.thinkgem.jeesite.modules.edu.dao.PersonalQuestionReplyDao;

/**
 * 个人问答回复Service
 * @author lala
 * @version 2015-04-08
 */
@Component
@Transactional(readOnly = true)
public class PersonalQuestionReplyService extends BaseService {

	@Autowired
	private PersonalQuestionReplyDao personalQuestionReplyDao;
	
	public PersonalQuestionReply get(String id) {
		return personalQuestionReplyDao.get(id);
	}
	
	public Page<PersonalQuestionReply> find(Page<PersonalQuestionReply> page, PersonalQuestionReply personalQuestionReply) {
		DetachedCriteria dc = personalQuestionReplyDao.createDetachedCriteria();
//		if (personalQuestionReply.getEuser()!=null) {
//			dc.add(Restrictions.like("euser.name", "%"+personalQuestionReply.getEuser().getName()+"%"));
//		}

		dc.add(Restrictions.eq("personalQuestion.id", personalQuestionReply.getPersonalQuestion().getId()));
		dc.add(Restrictions.eq(PersonalQuestionReply.FIELD_DEL_FLAG, PersonalQuestionReply.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return personalQuestionReplyDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonalQuestionReply personalQuestionReply) {
		personalQuestionReplyDao.save(personalQuestionReply);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		personalQuestionReplyDao.deleteById(id);
	}
	
}
