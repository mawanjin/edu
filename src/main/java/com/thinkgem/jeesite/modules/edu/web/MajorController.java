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
import com.thinkgem.jeesite.modules.edu.entity.Major;
import com.thinkgem.jeesite.modules.edu.service.MajorService;

/**
 * 专业Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/major")
public class MajorController extends BaseController {

	@Autowired
	private MajorService majorService;
	
	@ModelAttribute
	public Major get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return majorService.get(id);
		}else{
			return new Major();
		}
	}
	
	@RequiresPermissions("edu:major:view")
	@RequestMapping(value = {"list", ""})
	public String list(Major major, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			major.setCreateBy(user);
		}
        Page<Major> page = majorService.find(new Page<Major>(request, response), major); 
        model.addAttribute("page", page);
		return "modules/" + "edu/majorList";
	}

	@RequiresPermissions("edu:major:view")
	@RequestMapping(value = "form")
	public String form(Major major, Model model) {
		model.addAttribute("major", major);
		return "modules/" + "edu/majorForm";
	}

	@RequiresPermissions("edu:major:edit")
	@RequestMapping(value = "save")
	public String save(Major major, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, major)){
			return form(major, model);
		}
		majorService.save(major);
		addMessage(redirectAttributes, "保存专业'" + major.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/major/?repage";
	}
	
	@RequiresPermissions("edu:major:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		majorService.delete(id);
		addMessage(redirectAttributes, "删除专业成功");
		return "redirect:"+Global.getAdminPath()+"/edu/major/?repage";
	}

}
