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
import com.thinkgem.jeesite.modules.edu.entity.Customization;
import com.thinkgem.jeesite.modules.edu.service.CustomizationService;

/**
 * 私人定制模板Controller
 * @author lala
 * @version 2015-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/customization")
public class CustomizationController extends BaseController {

	@Autowired
	private CustomizationService customizationService;
	
	@ModelAttribute
	public Customization get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return customizationService.get(id);
		}else{
			return new Customization();
		}
	}
	
	@RequiresPermissions("edu:customization:view")
	@RequestMapping(value = {"list", ""})
	public String list(Customization customization, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			customization.setCreateBy(user);
		}
        Page<Customization> page = customizationService.find(new Page<Customization>(request, response), customization); 
        model.addAttribute("page", page);
		return "modules/" + "edu/customizationList";
	}

	@RequiresPermissions("edu:customization:view")
	@RequestMapping(value = "form")
	public String form(Customization customization, Model model) {
		model.addAttribute("customization", customization);
		return "modules/" + "edu/customizationForm";
	}

	@RequiresPermissions("edu:customization:edit")
	@RequestMapping(value = "save")
	public String save(Customization customization, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customization)){
			return form(customization, model);
		}
		customizationService.save(customization);
		addMessage(redirectAttributes, "保存私人定制模板'" + customization.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/customization/?repage";
	}
	
	@RequiresPermissions("edu:customization:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		customizationService.delete(id);
		addMessage(redirectAttributes, "删除私人定制模板成功");
		return "redirect:"+Global.getAdminPath()+"/edu/customization/?repage";
	}

	@RequiresPermissions("edu:customization:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Customization customization = customizationService.get(id);
		customization.setStatus(status);
		customizationService.save(customization);
		if(status==1)
		addMessage(redirectAttributes, "发布私人定制模板成功");
		else
		addMessage(redirectAttributes, "取消发布私人定制模板成功");

		return "redirect:"+Global.getAdminPath()+"/edu/customization/?repage";
	}

}
