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
import com.thinkgem.jeesite.modules.edu.entity.ReportGuardian;
import com.thinkgem.jeesite.modules.edu.dao.ReportGuardianDao;

import java.util.List;

/**
 * 监护人报告Service
 * @author lala
 * @version 2015-04-14
 */
@Component
@Transactional(readOnly = true)
public class ReportGuardianService extends BaseService {

	@Autowired
	private ReportGuardianDao reportGuardianDao;
	
	public ReportGuardian get(String id) {
		return reportGuardianDao.get(id);
	}
	
	public Page<ReportGuardian> find(Page<ReportGuardian> page, ReportGuardian reportGuardian) {
		DetachedCriteria dc = reportGuardianDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(reportGuardian.getTitle())){
			dc.add(Restrictions.like("title", "%"+reportGuardian.getTitle()+"%"));
		}

		if(reportGuardian.getUser()!=null){
			dc.add(Restrictions.eq("user.id", reportGuardian.getUser().getId()));
		}

		dc.add(Restrictions.eq(ReportGuardian.FIELD_DEL_FLAG, ReportGuardian.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return reportGuardianDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportGuardian reportGuardian) {
		reportGuardianDao.save(reportGuardian);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		reportGuardianDao.deleteById(id);
	}

	public List<ReportGuardian> findByUid(String uid) {
		return reportGuardianDao.find("from ReportGuardian where del_flag=0 and status=1 and user.id=:p1",new Parameter(uid));
	}
}
