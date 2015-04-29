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
import com.thinkgem.jeesite.modules.edu.entity.Question;
import com.thinkgem.jeesite.modules.edu.dao.QuestionDao;

import java.util.List;

/**
 * 咨询回复Service
 *
 * @author lala
 * @version 2015-04-29
 */
@Component
@Transactional(readOnly = true)
public class QuestionService extends BaseService {

    @Autowired
    private QuestionDao questionDao;

    public Question get(String id) {
        return questionDao.get(id);
    }

    public Page<Question> find(Page<Question> page, Question question) {
        DetachedCriteria dc = questionDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(question.getTitle())) {
            dc.add(Restrictions.like("name", "%" + question.getTitle() + "%"));
        }
        dc.add(Restrictions.eq(Question.FIELD_DEL_FLAG, Question.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return questionDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(Question question) {
        questionDao.clear();
        questionDao.save(question);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        questionDao.deleteById(id);
    }

    public List<Question> findByUid(String uid) {
        return questionDao.find("from Question where euser.id=:p1 and del_flag=0 order by createDate desc", new Parameter(uid));
    }
}
