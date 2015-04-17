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
import com.thinkgem.jeesite.modules.edu.entity.Abroadhome;
import com.thinkgem.jeesite.modules.edu.service.AbroadhomeService;

/**
 * 个人问答Controller
 * @author lala
 * @version 2015-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/abroadhome")
public class AbroadhomeController extends BaseController {

	@Autowired
	private AbroadhomeService abroadhomeService;
	
	@ModelAttribute
	public Abroadhome get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return abroadhomeService.get(id);
		}else{
			return new Abroadhome();
		}
	}
	
	@RequiresPermissions("edu:abroadhome:view")
	@RequestMapping(value = {"list", ""})
	public String list(Abroadhome abroadhome, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			abroadhome.setCreateBy(user);
		}
        Page<Abroadhome> page = abroadhomeService.find(new Page<Abroadhome>(request, response), abroadhome); 
        model.addAttribute("page", page);
		return "modules/" + "edu/abroadhomeList";
	}

	@RequiresPermissions("edu:abroadhome:view")
	@RequestMapping(value = "form")
	public String form(Abroadhome abroadhome, Model model) {
		model.addAttribute("abroadhome", abroadhome);
		return "modules/" + "edu/abroadhomeForm";
	}

	@RequiresPermissions("edu:abroadhome:edit")
	@RequestMapping(value = "save")
	public String save(Abroadhome abroadhome, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, abroadhome)){
			return form(abroadhome, model);
		}
		abroadhome.setContent(StringEscapeUtils.unescapeHtml4(abroadhome.getContent()));
		abroadhomeService.save(abroadhome);
		addMessage(redirectAttributes, "保存个人问答'" + abroadhome.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/abroadhome/?repage";
	}
	
	@RequiresPermissions("edu:abroadhome:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		abroadhomeService.delete(id);
		addMessage(redirectAttributes, "删除个人问答成功");
		return "redirect:"+Global.getAdminPath()+"/edu/abroadhome/?repage";
	}

	@RequiresPermissions("edu:abroadhome:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Abroadhome abroadhome = abroadhomeService.get(id);
		abroadhome.setStatus(status);
		abroadhomeService.save(abroadhome);
		if(status==1)
		addMessage(redirectAttributes, "发布个人问答成功");
		else
		addMessage(redirectAttributes, "取消发布个人问答成功");

		return "redirect:"+Global.getAdminPath()+"/edu/abroadhome/?repage";
	}

}
