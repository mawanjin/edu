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
import com.thinkgem.jeesite.modules.edu.entity.Suggestion;
import com.thinkgem.jeesite.modules.edu.service.SuggestionService;

/**
 * 意见反馈Controller
 * @author lala
 * @version 2015-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/suggestion")
public class SuggestionController extends BaseController {

	@Autowired
	private SuggestionService suggestionService;
	
	@ModelAttribute
	public Suggestion get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return suggestionService.get(id);
		}else{
			return new Suggestion();
		}
	}
	
	@RequiresPermissions("edu:suggestion:view")
	@RequestMapping(value = {"list", ""})
	public String list(Suggestion suggestion, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			suggestion.setCreateBy(user);
		}
        Page<Suggestion> page = suggestionService.find(new Page<Suggestion>(request, response), suggestion); 
        model.addAttribute("page", page);
		return "modules/" + "edu/suggestionList";
	}

	@RequiresPermissions("edu:suggestion:view")
	@RequestMapping(value = "form")
	public String form(Suggestion suggestion, Model model) {
		model.addAttribute("suggestion", suggestion);
		return "modules/" + "edu/suggestionForm";
	}

	@RequiresPermissions("edu:suggestion:edit")
	@RequestMapping(value = "save")
	public String save(Suggestion suggestion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, suggestion)){
			return form(suggestion, model);
		}
		suggestionService.save(suggestion);
		addMessage(redirectAttributes, "保存意见反馈'" + suggestion.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/suggestion/?repage";
	}
	
	@RequiresPermissions("edu:suggestion:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		suggestionService.delete(id);
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/edu/suggestion/?repage";
	}

}
