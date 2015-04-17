/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.entity.QuestionCategory;
import com.thinkgem.jeesite.modules.edu.service.QuestionCategoryService;
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
import com.thinkgem.jeesite.modules.edu.entity.PersonalQuestion;
import com.thinkgem.jeesite.modules.edu.service.PersonalQuestionService;

/**
 * 个人问答Controller
 * @author lala
 * @version 2015-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/personalQuestion")
public class PersonalQuestionController extends BaseController {

	@Autowired
	private PersonalQuestionService personalQuestionService;

	@Autowired
	private QuestionCategoryService questionCategoryService;
	
	@ModelAttribute
	public PersonalQuestion get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return personalQuestionService.get(id);
		}else{
			return new PersonalQuestion();
		}
	}
	
	@RequiresPermissions("edu:personalQuestion:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonalQuestion personalQuestion, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			personalQuestion.setCreateBy(user);
		}
        Page<PersonalQuestion> page = personalQuestionService.find(new Page<PersonalQuestion>(request, response), personalQuestion); 
        model.addAttribute("page", page);
		return "modules/" + "edu/personalQuestionList";
	}

	@RequiresPermissions("edu:personalQuestion:view")
	@RequestMapping(value = "form")
	public String form(PersonalQuestion personalQuestion, Model model) {
		model.addAttribute("personalQuestion", personalQuestion);
		model.addAttribute("categorys",questionCategoryService.findAll());

		return "modules/" + "edu/personalQuestionForm";
	}

	@RequiresPermissions("edu:personalQuestion:edit")
	@RequestMapping(value = "save")
	public String save(PersonalQuestion personalQuestion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personalQuestion)){
			return form(personalQuestion, model);
		}
		personalQuestion.setContent(StringEscapeUtils.unescapeHtml4(personalQuestion.getContent()));
		personalQuestionService.save(personalQuestion);
		addMessage(redirectAttributes, "保存个人问答'" + personalQuestion.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/personalQuestion/?repage";
	}
	
	@RequiresPermissions("edu:personalQuestion:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		personalQuestionService.delete(id);
		addMessage(redirectAttributes, "删除个人问答成功");
		return "redirect:"+Global.getAdminPath()+"/edu/personalQuestion/?repage";
	}

	@RequiresPermissions("edu:personalQuestion:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		PersonalQuestion personalQuestion = personalQuestionService.get(id);
		personalQuestion.setStatus(status);
		personalQuestionService.save(personalQuestion);
		if(status==1)
		addMessage(redirectAttributes, "发布个人问答成功");
		else
		addMessage(redirectAttributes, "取消发布个人问答成功");

		return "redirect:"+Global.getAdminPath()+"/edu/personalQuestion/?repage";
	}

}
