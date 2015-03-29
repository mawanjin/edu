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
import com.thinkgem.jeesite.modules.edu.entity.Indeximg;
import com.thinkgem.jeesite.modules.edu.dao.IndeximgDao;

/**
 * 首页顶部图片Service
 * @author lala
 * @version 2015-03-29
 */
@Component
@Transactional(readOnly = true)
public class IndeximgService extends BaseService {

	@Autowired
	private IndeximgDao indeximgDao;
	
	public Indeximg get(String id) {
		return indeximgDao.get(id);
	}
	
	public Page<Indeximg> find(Page<Indeximg> page, Indeximg indeximg) {
		DetachedCriteria dc = indeximgDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(indeximg.getName())){
			dc.add(Restrictions.like("name", "%"+indeximg.getName()+"%"));
		}
		dc.add(Restrictions.eq(Indeximg.FIELD_DEL_FLAG, Indeximg.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return indeximgDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Indeximg indeximg) {
		indeximgDao.save(indeximg);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		indeximgDao.deleteById(id);
	}
	
}
