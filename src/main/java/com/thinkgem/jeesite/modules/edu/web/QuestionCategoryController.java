/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.edu.entity.QuestionCategory;
import com.thinkgem.jeesite.modules.edu.service.QuestionCategoryService;

/**
 * 问答类型Controller
 * @author lala
 * @version 2015-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/questionCategory")
public class QuestionCategoryController extends BaseController {

	@Autowired
	private QuestionCategoryService questionCategoryService;
	
	@ModelAttribute
	public QuestionCategory get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return questionCategoryService.get(id);
		}else{
			return new QuestionCategory();
		}
	}
	
	@RequiresPermissions("edu:questionCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionCategory questionCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			questionCategory.setCreateBy(user);
		}
        Page<QuestionCategory> page = questionCategoryService.find(new Page<QuestionCategory>(request, response), questionCategory); 
        model.addAttribute("page", page);
		return "modules/" + "edu/questionCategoryList";
	}

	@RequiresPermissions("edu:questionCategory:view")
	@RequestMapping(value = "form")
	public String form(QuestionCategory questionCategory, Model model) {
		model.addAttribute("questionCategory", questionCategory);
		return "modules/" + "edu/questionCategoryForm";
	}

	@RequiresPermissions("edu:questionCategory:edit")
	@RequestMapping(value = "save")
	public String save(QuestionCategory questionCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, questionCategory)){
			return form(questionCategory, model);
		}
		questionCategoryService.save(questionCategory);
		addMessage(redirectAttributes, "保存问答类型'" + questionCategory.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/questionCategory/?repage";
	}
	
	@RequiresPermissions("edu:questionCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		questionCategoryService.delete(id);
		addMessage(redirectAttributes, "删除问答类型成功");
		return "redirect:"+Global.getAdminPath()+"/edu/questionCategory/?repage";
	}

}
