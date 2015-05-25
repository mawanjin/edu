/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.edu.entity.Euser;
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

import java.util.ArrayList;
import java.util.List;

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
			return userRelationDao.find(page,"from UserRelation where delFlag='0' and eduUserById.name like '%"+userRelation.getEduUserById().getName()+"%'  order by id desc ");
		}else if(userRelation.getEduUserByParentId()!=null){
			return userRelationDao.find(page,"from UserRelation where delFlag='0' and eduUserByParentId.id = '"+userRelation.getEduUserByParentId().getId()+"'  order by id desc ");
		}

		return userRelationDao.find(page,"from UserRelation where delFlag='0' order by id desc ");

	}
	
	@Transactional(readOnly = false)
	public void save(UserRelation userRelation) {
		userRelationDao.save(userRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		userRelationDao.deleteById(id);
	}

	/**
	 * 家长找到监护人
	 * @param uid 家长
	 * @return
	 */
	public Euser findGuardian(String uid) {
		List<UserRelation> students = userRelationDao.find("from UserRelation where eduUserByParentId.id=:p1 and delFlag='0'",new Parameter(uid));
		if(students==null||students.size()==0)return null;
		Euser student = students.get(0).getEduUserById();

		List<UserRelation> guardians = userRelationDao.find("from UserRelation where eduUserById.id=:p1 and eduUserByParentId.type=3 and delFlag='0'",new Parameter(student.getId()));
		if(guardians!=null&&guardians.size()>0){
			return guardians.get(0).getEduUserByParentId();
		}
		return null;
	}

	public Euser findGuardianByUid(String uid){
		List<UserRelation> guardian = userRelationDao.find("from UserRelation where eduUserById.id=:p1 and delFlag='0' and eduUserByParentId.type=3  ",new Parameter(uid));
		if(guardian==null||guardian.size()==0)return null;
		return guardian.get(0).getEduUserByParentId();
	}
}
