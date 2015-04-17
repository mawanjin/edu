/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.entity.School;
import com.thinkgem.jeesite.modules.edu.entity.UserRelation;
import com.thinkgem.jeesite.modules.edu.service.SchoolService;
import com.thinkgem.jeesite.modules.edu.service.UserRelationService;
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
import com.thinkgem.jeesite.modules.edu.entity.Euser;
import com.thinkgem.jeesite.modules.edu.service.EuserService;

import java.util.List;

/**
 * 用户信息Controller
 * @author lala
 * @version 2015-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/euser")
public class EuserController extends BaseController {

	@Autowired
	UserRelationService userRelationService;
	@Autowired
	private EuserService euserService;

	@Autowired
	private SchoolService schoolService;
	
	@ModelAttribute
	public Euser get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return euserService.get(id);
		}else{
			return new Euser();
		}
	}
	
	@RequiresPermissions("edu:euser:view")
	@RequestMapping(value = {"list", ""})
	public String list(Euser euser, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			euser.setCreateBy(user);
		}
        Page<Euser> page = euserService.find(new Page<Euser>(request, response), euser); 
        model.addAttribute("page", page);
		return "modules/" + "edu/euserList";
	}

	@RequiresPermissions("edu:euser:view")
	@RequestMapping(value = "form")
	public String form(Euser euser, Model model) {

		euser.setType(new Byte("0"));
		model.addAttribute("euser", euser);
		model.addAttribute("schools", schoolService.findAll());

		return "modules/" + "edu/euserForm";
	}

	@RequiresPermissions("edu:euser:edit")
	@RequestMapping(value = "save")
	public String save(Euser euser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, euser)){
			return form(euser, model);
		}
		if(StringUtils.isEmpty(euser.getSchool().getId()))euser.setSchool(null);
		euserService.save(euser);
		addMessage(redirectAttributes, "保存用户信息'" + euser.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/euser/?repage";
	}
	
	@RequiresPermissions("edu:euser:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		euserService.delete(id);
		addMessage(redirectAttributes, "删除用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/edu/euser/?repage";
	}

}
