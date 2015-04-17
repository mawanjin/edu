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
import com.thinkgem.jeesite.modules.edu.entity.Country;
import com.thinkgem.jeesite.modules.edu.dao.CountryDao;

/**
 * 国家Service
 * @author lala
 * @version 2015-04-01
 */
@Component
@Transactional(readOnly = true)
public class CountryService extends BaseService {

	@Autowired
	private CountryDao countryDao;
	
	public Country get(String id) {
		return countryDao.get(id);
	}
	
	public Page<Country> find(Page<Country> page, Country country) {
		DetachedCriteria dc = countryDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(country.getName())){
			dc.add(Restrictions.like("name", "%"+country.getName()+"%"));
		}
		dc.add(Restrictions.eq(Country.FIELD_DEL_FLAG, Country.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return countryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Country country) {
		countryDao.save(country);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		countryDao.deleteById(id);
	}
	
}
