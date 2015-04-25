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
import com.thinkgem.jeesite.modules.edu.entity.AbroadEnroll;
import com.thinkgem.jeesite.modules.edu.dao.AbroadEnrollDao;

import java.util.List;

/**
 * 海外之家报名Service
 * @author lala
 * @version 2015-04-23
 */
@Component
@Transactional(readOnly = true)
public class AbroadEnrollService extends BaseService {

	@Autowired
	private AbroadEnrollDao abroadEnrollDao;
	
	public AbroadEnroll get(String id) {
		return abroadEnrollDao.get(id);
	}
	
	public Page<AbroadEnroll> find(Page<AbroadEnroll> page, AbroadEnroll abroadEnroll) {
		DetachedCriteria dc = abroadEnrollDao.createDetachedCriteria();
//		if (abroadEnroll.getEuser()!=null){
//			dc.add(Restrictions.like("euser", "%"+abroadEnroll.getEuser().getName()+"%"));
//		}
		dc.add(Restrictions.eq(AbroadEnroll.FIELD_DEL_FLAG, AbroadEnroll.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return abroadEnrollDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(AbroadEnroll abroadEnroll) {
		abroadEnrollDao.save(abroadEnroll);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		abroadEnrollDao.deleteById(id);
	}

	public boolean isEnrolled(String uid,String abroadHomeId) {
		List rs = abroadEnrollDao.find("from AbroadEnroll where euser.id=:p1 and abroadhome.id=:p2 and del_flag=0", new Parameter(new String[]{uid, abroadHomeId}));
		if(rs!=null&&rs.size()>0)return true;

		return false;
	}
}
