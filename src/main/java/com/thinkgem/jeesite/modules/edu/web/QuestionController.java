/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.EuserService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.edu.entity.Question;
import com.thinkgem.jeesite.modules.edu.service.QuestionService;

/**
 * 咨询回复Controller
 * @author lala
 * @version 2015-04-29
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/question")
public class QuestionController extends BaseController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public Question get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return questionService.get(id);
		}else{
			return new Question();
		}
	}
	
	@RequiresPermissions("edu:question:view")
	@RequestMapping(value = {"list", ""})
	public String list(Question question, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			question.setCreateBy(user);
		}
        Page<Question> page = questionService.find(new Page<Question>(request, response), question); 
        model.addAttribute("page", page);
		return "modules/" + "edu/questionList";
	}

	@RequiresPermissions("edu:question:view")
	@RequestMapping(value = "form")
	public String form(Question question, Model model) {
		model.addAttribute("question", question);
		model.addAttribute("users", euserService.findAll());
		return "modules/" + "edu/questionForm";
	}



	@RequiresPermissions("edu:question:edit")
	@RequestMapping(value = "save")
	public String save(Question question, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, question)){
			return form(question, model);
		}

		question.setReply(StringEscapeUtils.unescapeHtml4(question.getReply()));

		questionService.save(question);
		addMessage(redirectAttributes, "保存咨询回复'" + question.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/question/?repage";
	}
	
	@RequiresPermissions("edu:question:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		questionService.delete(id);
		addMessage(redirectAttributes, "删除咨询回复成功");
		return "redirect:"+Global.getAdminPath()+"/edu/question/?repage";
	}
}
