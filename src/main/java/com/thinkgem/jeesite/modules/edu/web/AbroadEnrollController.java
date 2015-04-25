/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.AbroadhomeService;
import com.thinkgem.jeesite.modules.edu.service.EuserService;
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
import com.thinkgem.jeesite.modules.edu.entity.AbroadEnroll;
import com.thinkgem.jeesite.modules.edu.service.AbroadEnrollService;

/**
 * 海外之家报名Controller
 * @author lala
 * @version 2015-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/abroadEnroll")
public class AbroadEnrollController extends BaseController {

	@Autowired
	private AbroadEnrollService abroadEnrollService;

	@Autowired
	private AbroadhomeService abroadhomeService;
	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public AbroadEnroll get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return abroadEnrollService.get(id);
		}else{
			return new AbroadEnroll();
		}
	}
	
	@RequiresPermissions("edu:abroadEnroll:view")
	@RequestMapping(value = {"list", ""})
	public String list(AbroadEnroll abroadEnroll, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			abroadEnroll.setCreateBy(user);
		}
        Page<AbroadEnroll> page = abroadEnrollService.find(new Page<AbroadEnroll>(request, response), abroadEnroll); 
        model.addAttribute("page", page);
		return "modules/" + "edu/abroadEnrollList";
	}

	@RequiresPermissions("edu:abroadEnroll:view")
	@RequestMapping(value = "form")
	public String form(AbroadEnroll abroadEnroll, Model model) {
		model.addAttribute("abroadEnroll", abroadEnroll);
		model.addAttribute("abroads", abroadhomeService.findAll());
		model.addAttribute("users", euserService.findAll());

		return "modules/" + "edu/abroadEnrollForm";
	}

	@RequiresPermissions("edu:abroadEnroll:edit")
	@RequestMapping(value = "save")
	public String save(AbroadEnroll abroadEnroll, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, abroadEnroll)){
			return form(abroadEnroll, model);
		}
		abroadEnrollService.save(abroadEnroll);
		addMessage(redirectAttributes, "保存海外之家报名成功");
		return "redirect:"+Global.getAdminPath()+"/edu/abroadEnroll/?repage";
	}
	
	@RequiresPermissions("edu:abroadEnroll:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		abroadEnrollService.delete(id);
		addMessage(redirectAttributes, "删除海外之家报名成功");
		return "redirect:"+Global.getAdminPath()+"/edu/abroadEnroll/?repage";
	}

	@RequiresPermissions("edu:abroadEnroll:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		AbroadEnroll abroadEnroll = abroadEnrollService.get(id);
		abroadEnroll.setStatus(status);
		abroadEnrollService.save(abroadEnroll);
		if(status==1)
			addMessage(redirectAttributes, "发布海外之家报名成功");
		else
			addMessage(redirectAttributes, "取消发布海外之家报名成功");

		return "redirect:"+Global.getAdminPath()+"/edu/abroadEnroll/?repage";
	}

}
