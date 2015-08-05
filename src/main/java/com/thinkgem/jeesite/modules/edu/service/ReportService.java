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
import com.thinkgem.jeesite.modules.edu.entity.Report;
import com.thinkgem.jeesite.modules.edu.dao.ReportDao;

import java.util.List;

/**
 * 日常报告Service
 * @author lala
 * @version 2015-04-14
 */
@Component
@Transactional(readOnly = true)
public class ReportService extends BaseService {

	@Autowired
	private ReportDao reportDao;
	
	public Report get(String id) {
		return reportDao.get(id);
	}
	
	public Page<Report> find(Page<Report> page, Report report) {
		DetachedCriteria dc = reportDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(report.getTitle())){
			dc.add(Restrictions.like("title", "%"+report.getTitle()+"%"));
		}

		if (report.getType()!=null){
			dc.add(Restrictions.eq("type", report.getType()));
		}
		dc.add(Restrictions.eq(Report.FIELD_DEL_FLAG, Report.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return reportDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Report report) {
        reportDao.clear();
		reportDao.save(report);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		reportDao.deleteById(id);
	}

	public List<Report> findByType(Byte type,String uid) {
		return reportDao.find("from Report where type=:p1 and status=1 and del_flag=0 and euser.id=:p2 order by createDate desc",new Parameter(type,uid));
	}
}
