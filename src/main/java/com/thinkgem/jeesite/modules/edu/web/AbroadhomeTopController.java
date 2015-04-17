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
import com.thinkgem.jeesite.modules.edu.entity.AbroadhomeTop;
import com.thinkgem.jeesite.modules.edu.service.AbroadhomeTopService;

/**
 * 海外之前图片Controller
 * @author lala
 * @version 2015-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/abroadhomeTop")
public class AbroadhomeTopController extends BaseController {

	@Autowired
	private AbroadhomeTopService abroadhomeTopService;
	
	@ModelAttribute
	public AbroadhomeTop get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return abroadhomeTopService.get(id);
		}else{
			return new AbroadhomeTop();
		}
	}
	
	@RequiresPermissions("edu:abroadhomeTop:view")
	@RequestMapping(value = {"list", ""})
	public String list(AbroadhomeTop abroadhomeTop, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			abroadhomeTop.setCreateBy(user);
		}
        Page<AbroadhomeTop> page = abroadhomeTopService.find(new Page<AbroadhomeTop>(request, response), abroadhomeTop); 
        model.addAttribute("page", page);
		return "modules/" + "edu/abroadhomeTopList";
	}

	@RequiresPermissions("edu:abroadhomeTop:view")
	@RequestMapping(value = "form")
	public String form(AbroadhomeTop abroadhomeTop, Model model) {
		model.addAttribute("abroadhomeTop", abroadhomeTop);
		return "modules/" + "edu/abroadhomeTopForm";
	}

	@RequiresPermissions("edu:abroadhomeTop:edit")
	@RequestMapping(value = "save")
	public String save(AbroadhomeTop abroadhomeTop, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, abroadhomeTop)){
			return form(abroadhomeTop, model);
		}
		abroadhomeTopService.save(abroadhomeTop);
		addMessage(redirectAttributes, "保存海外之前图片成功");
		return "redirect:"+Global.getAdminPath()+"/edu/abroadhomeTop/?repage";
	}
	
	@RequiresPermissions("edu:abroadhomeTop:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		abroadhomeTopService.delete(id);
		addMessage(redirectAttributes, "删除海外之前图片成功");
		return "redirect:"+Global.getAdminPath()+"/edu/abroadhomeTop/?repage";
	}

	@RequiresPermissions("edu:abroadhomeTop:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		AbroadhomeTop abroadhomeTop = abroadhomeTopService.get(id);
		abroadhomeTop.setStatus(status);
		abroadhomeTopService.save(abroadhomeTop);
		if(status==1)
		addMessage(redirectAttributes, "发布海外之前图片成功");
		else
		addMessage(redirectAttributes, "取消发布海外之前图片成功");

		return "redirect:"+Global.getAdminPath()+"/edu/abroadhomeTop/?repage";
	}

}
