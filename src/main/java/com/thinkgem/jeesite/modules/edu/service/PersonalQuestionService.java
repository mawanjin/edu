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
import com.thinkgem.jeesite.modules.edu.entity.PersonalQuestion;
import com.thinkgem.jeesite.modules.edu.dao.PersonalQuestionDao;

/**
 * 个人问答Service
 * @author lala
 * @version 2015-04-07
 */
@Component
@Transactional(readOnly = true)
public class PersonalQuestionService extends BaseService {

	@Autowired
	private PersonalQuestionDao personalQuestionDao;
	
	public PersonalQuestion get(String id) {
		return personalQuestionDao.get(id);
	}
	
	public Page<PersonalQuestion> find(Page<PersonalQuestion> page, PersonalQuestion personalQuestion) {
		DetachedCriteria dc = personalQuestionDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(personalQuestion.getTitle())){
			dc.add(Restrictions.like("title", "%"+personalQuestion.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(PersonalQuestion.FIELD_DEL_FLAG, PersonalQuestion.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return personalQuestionDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonalQuestion personalQuestion) {
		personalQuestionDao.save(personalQuestion);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		personalQuestionDao.deleteById(id);
	}
	
}
