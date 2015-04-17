/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.edu.entity.Emergency;
import com.thinkgem.jeesite.modules.edu.service.EmergencyService;

/**
 * 重要提示Controller
 * @author lala
 * @version 2015-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/emergency")
public class EmergencyController extends BaseController {

	@Autowired
	private EmergencyService emergencyService;
	
	@ModelAttribute
	public Emergency get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return emergencyService.get(id);
		}else{
			return new Emergency();
		}
	}
	
	@RequiresPermissions("edu:emergency:view")
	@RequestMapping(value = {"list", ""})
	public String list(Emergency emergency, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			emergency.setCreateBy(user);
		}
        Page<Emergency> page = emergencyService.find(new Page<Emergency>(request, response), emergency); 
        model.addAttribute("page", page);
		return "modules/" + "edu/emergencyList";
	}

	@RequiresPermissions("edu:emergency:view")
	@RequestMapping(value = "form")
	public String form(Emergency emergency, Model model) {
		model.addAttribute("emergency", emergency);
		return "modules/" + "edu/emergencyForm";
	}

	@RequiresPermissions("edu:emergency:edit")
	@RequestMapping(value = "save")
	public String save(Emergency emergency, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, emergency)){
			return form(emergency, model);
		}
		emergency.setContent(StringEscapeUtils.unescapeHtml4(emergency.getContent()));
		emergencyService.save(emergency);
		addMessage(redirectAttributes, "保存重要提示'" + emergency.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/emergency/?repage";
	}
	
	@RequiresPermissions("edu:emergency:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		emergencyService.delete(id);
		addMessage(redirectAttributes, "删除重要提示成功");
		return "redirect:"+Global.getAdminPath()+"/edu/emergency/?repage";
	}

	@RequiresPermissions("edu:emergency:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Emergency emergency = emergencyService.get(id);
		emergency.setStatus(status);
		emergencyService.save(emergency);
		if(status==1)
		addMessage(redirectAttributes, "发布重要提示成功");
		else
		addMessage(redirectAttributes, "取消发布重要提示成功");

		return "redirect:"+Global.getAdminPath()+"/edu/emergency/?repage";
	}

}
