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
import com.thinkgem.jeesite.modules.edu.entity.UserRelation;
import com.thinkgem.jeesite.modules.edu.dao.UserRelationDao;

/**
 * 用户关系Service
 * @author lala
 * @version 2015-03-31
 */
@Component
@Transactional(readOnly = true)
public class UserRelationService extends BaseService {

	@Autowired
	private UserRelationDao userRelationDao;
	
	public UserRelation get(String id) {
		return userRelationDao.get(id);
	}
	
	public Page<UserRelation> find(Page<UserRelation> page, UserRelation userRelation) {
		DetachedCriteria dc = userRelationDao.createDetachedCriteria();
		if (userRelation.getEduUserById()!=null&&StringUtils.isNotEmpty(userRelation.getEduUserById().getName())){
//			dc.add(Restrictions.like("eduUserById.name", "%"+userRelation.getEduUserById().getName()+"%"));
			return userRelationDao.find(page,"from UserRelation where delFlag='0' and eduUserById.name like '%"+userRelation.getEduUserById().getName()+"%'  order by id desc ");
		}
//		dc.add(Restrictions.eq(UserRelation.FIELD_DEL_FLAG, UserRelation.DEL_FLAG_NORMAL));
//		dc.addOrder(Order.desc("id"));

		return userRelationDao.find(page,"from UserRelation where delFlag='0' order by id desc ");

//		return userRelationDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(UserRelation userRelation) {
		userRelationDao.save(userRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		userRelationDao.deleteById(id);
	}
	
}
