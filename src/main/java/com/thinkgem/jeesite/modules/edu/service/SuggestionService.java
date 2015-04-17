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
import com.thinkgem.jeesite.modules.edu.entity.Suggestion;
import com.thinkgem.jeesite.modules.edu.dao.SuggestionDao;

/**
 * 意见反馈Service
 * @author lala
 * @version 2015-04-15
 */
@Component
@Transactional(readOnly = true)
public class SuggestionService extends BaseService {

	@Autowired
	private SuggestionDao suggestionDao;
	
	public Suggestion get(String id) {
		return suggestionDao.get(id);
	}
	
	public Page<Suggestion> find(Page<Suggestion> page, Suggestion suggestion) {
		DetachedCriteria dc = suggestionDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(suggestion.getTitle())){
			dc.add(Restrictions.like("title", "%"+suggestion.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(Suggestion.FIELD_DEL_FLAG, Suggestion.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return suggestionDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Suggestion suggestion) {
		suggestionDao.save(suggestion);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		suggestionDao.deleteById(id);
	}
	
}
