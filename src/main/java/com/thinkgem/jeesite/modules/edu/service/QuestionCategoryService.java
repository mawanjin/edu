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
import com.thinkgem.jeesite.modules.edu.entity.QuestionCategory;
import com.thinkgem.jeesite.modules.edu.dao.QuestionCategoryDao;

import java.util.List;

/**
 * 问答类型Service
 * @author lala
 * @version 2015-04-02
 */
@Component
@Transactional(readOnly = true)
public class QuestionCategoryService extends BaseService {

	@Autowired
	private QuestionCategoryDao questionCategoryDao;
	
	public QuestionCategory get(String id) {
		return questionCategoryDao.get(id);
	}
	
	public Page<QuestionCategory> find(Page<QuestionCategory> page, QuestionCategory questionCategory) {
		DetachedCriteria dc = questionCategoryDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(questionCategory.getName())){
			dc.add(Restrictions.like("name", "%"+questionCategory.getName()+"%"));
		}
		dc.add(Restrictions.eq(QuestionCategory.FIELD_DEL_FLAG, QuestionCategory.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return questionCategoryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(QuestionCategory questionCategory) {
		questionCategoryDao.save(questionCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		questionCategoryDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<QuestionCategory> findAll(){
		return questionCategoryDao.findAll();
	}
	
}
