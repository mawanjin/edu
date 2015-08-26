/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import com.thinkgem.jeesite.common.persistence.Parameter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.Euser;
import com.thinkgem.jeesite.modules.edu.dao.EuserDao;

import java.util.List;

/**
 * 用户信息Service
 * @author lala
 * @version 2015-03-30
 */
@Component
@Transactional(readOnly = true)
public class EuserService extends BaseService {

	@Autowired
	private EuserDao euserDao;
	
	public Euser get(String id) {
		return euserDao.get(id);
	}
	
	public Page<Euser> find(Page<Euser> page, Euser euser) {
		DetachedCriteria dc = euserDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(euser.getName())){
			dc.add(Restrictions.like("name", "%"+euser.getName()+"%"));
		}

		if (euser.getType()!=null){
			dc.add(Restrictions.eq("type", euser.getType()));
		}
		dc.add(Restrictions.eq(Euser.FIELD_DEL_FLAG, Euser.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return euserDao.find(page, dc);
	}

	public Page<Euser> findUserWithoutRelation(Page<Euser> page,String uid) {
		return euserDao.find(page,"select distinct euser from Euser euser where euser.type='0' and euser.delFlag='0' and euser.id not in (select eduUserById.id from UserRelation where delFlag='0' and eduUserByParentId='"+uid+"' ) ");
	}

	@Transactional(readOnly = false)
	public void save(Euser euser) {
		euserDao.clear();
		euserDao.save(euser);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		euserDao.deleteById(id);
	}

	public List<Euser> findAll() {
        return euserDao.find("from Euser where delFlag = 0");
//		return euserDao.findAll();
	}

	public Euser login(String loginName, String password) {
        loginName = loginName.trim();
        password = password.trim();
		List<Euser> eusers = euserDao.find("from Euser where loginName=:p1 and password=:p2 and del_flag=0", new Parameter(new String[]{loginName, password}));
		if(eusers!=null&&eusers.size()>0)return eusers.get(0);
        //未找到,看是否用户存在
        eusers = euserDao.find("from Euser where loginName=:p1 and del_flag=0", new Parameter(new String[]{loginName}));
        if(eusers!=null&&eusers.size()>0){
            Euser euser = new Euser();
            euser.setLoginName("normal");
            return euser;
        }else{
            Euser euser = new Euser();
            euser.setLoginName("abnormal");
            return euser;
        }
	}

	public Euser findByLoginName(String loginName) {
		List<Euser> rs =  euserDao.find("from Euser where loginName=:p1 and del_flag=0 ", new Parameter(loginName));
		if(rs!=null&&rs.size()>0)return rs.get(0);
		return null;
	}
}
