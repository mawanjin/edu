/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.dao.ActivityDao;

import java.util.List;

/**
 * 主题活动Service
 * @author lala
 * @version 2015-03-24
 */
@Component
@Transactional(readOnly = true)
public class ActivityService extends BaseService {

	@Autowired
	private ActivityDao activityDao;
	
	public Activity get(String id) {
		return activityDao.get(id);
	}
	
	public Page<Activity> find(Page<Activity> page, Activity activity) {
		DetachedCriteria dc = activityDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(activity.getTitle())){
			dc.add(Restrictions.like("title", "%"+activity.getTitle()+"%"));
		}
		dc.add(Restrictions.eq(Activity.FIELD_DEL_FLAG, Activity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return activityDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Activity activity) {
		activityDao.save(activity);
	}

	/**
	 * 取消数据库中所有已置顶TOP的记录
	 */
	@Transactional(readOnly = false)
	public void removeTop(Activity activity){
		activityDao.update("update Activity set top='0' where top='1' and id!='"+activity.getId()+"'");
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		activityDao.deleteById(id);
	}

	public List<Activity> findAll(){
		return activityDao.find("from Activity where status=1 and del_flag=0 order by top desc");
	}
}
