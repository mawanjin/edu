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
import com.thinkgem.jeesite.modules.edu.entity.CustomizationMessage;
import com.thinkgem.jeesite.modules.edu.service.CustomizationMessageService;

/**
 * 私人定制留言Controller
 * @author lala
 * @version 2015-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/customizationMessage")
public class CustomizationMessageController extends BaseController {

	@Autowired
	private CustomizationMessageService customizationMessageService;
	
	@ModelAttribute
	public CustomizationMessage get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return customizationMessageService.get(id);
		}else{
			return new CustomizationMessage();
		}
	}
	
	@RequiresPermissions("edu:customizationMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomizationMessage customizationMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			customizationMessage.setCreateBy(user);
		}
        Page<CustomizationMessage> page = customizationMessageService.find(new Page<CustomizationMessage>(request, response), customizationMessage); 
        model.addAttribute("page", page);
		return "modules/" + "edu/customizationMessageList";
	}

	@RequiresPermissions("edu:customizationMessage:view")
	@RequestMapping(value = "form")
	public String form(CustomizationMessage customizationMessage, Model model) {
		model.addAttribute("customizationMessage", customizationMessage);
		return "modules/" + "edu/customizationMessageForm";
	}

	@RequiresPermissions("edu:customizationMessage:edit")
	@RequestMapping(value = "save")
	public String save(CustomizationMessage customizationMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customizationMessage)){
			return form(customizationMessage, model);
		}
		customizationMessageService.save(customizationMessage);
		addMessage(redirectAttributes, "保存私人定制留言成功");
		return "redirect:"+Global.getAdminPath()+"/edu/customizationMessage/?repage";
	}
	
	@RequiresPermissions("edu:customizationMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		customizationMessageService.delete(id);
		addMessage(redirectAttributes, "删除私人定制留言成功");
		return "redirect:"+Global.getAdminPath()+"/edu/customizationMessage/?repage";
	}

}
