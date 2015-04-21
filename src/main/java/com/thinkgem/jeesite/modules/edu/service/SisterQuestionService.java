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
import com.thinkgem.jeesite.modules.edu.entity.SisterQuestion;
import com.thinkgem.jeesite.modules.edu.dao.SisterQuestionDao;

/**
 * 留言咨询Service
 * @author lala
 * @version 2015-04-22
 */
@Component
@Transactional(readOnly = true)
public class SisterQuestionService extends BaseService {

	@Autowired
	private SisterQuestionDao sisterQuestionDao;
	
	public SisterQuestion get(String id) {
		return sisterQuestionDao.get(id);
	}
	
	public Page<SisterQuestion> find(Page<SisterQuestion> page, SisterQuestion sisterQuestion) {
		DetachedCriteria dc = sisterQuestionDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(sisterQuestion.getTitle())){
			dc.add(Restrictions.like("name", "%"+sisterQuestion.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(SisterQuestion.FIELD_DEL_FLAG, SisterQuestion.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return sisterQuestionDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(SisterQuestion sisterQuestion) {
		sisterQuestionDao.save(sisterQuestion);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		sisterQuestionDao.deleteById(id);
	}
	
}
