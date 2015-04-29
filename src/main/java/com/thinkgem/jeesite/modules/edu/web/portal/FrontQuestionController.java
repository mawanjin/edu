/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.dao.QuestionDao;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.entity.Question;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;
import com.thinkgem.jeesite.modules.edu.service.QuestionService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.QuestionDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 留言咨询Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/question")
public class FrontQuestionController extends BaseController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = {"save"})
	@ResponseBody
	public boolean save(Question question, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			questionService.save(question);
		}catch (Exception e){
			return false;
		}
		return true;
	}

	@RequestMapping(value = {""})
	@ResponseBody
	public QuestionDto find(String uid, HttpServletRequest request, HttpServletResponse response, Model model) {
		QuestionDto questionDto = new QuestionDto();
		try{
			List<Question> questions = questionService.findByUid(uid);
			questionDto.setQuestions(questions);
			questionDto.setRs(true);

		}catch (Exception e){
			questionDto.setRs(false);
		}
		return questionDto;
	}





}
