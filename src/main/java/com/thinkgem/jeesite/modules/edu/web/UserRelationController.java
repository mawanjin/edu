/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.entity.Euser;
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
import com.thinkgem.jeesite.modules.edu.entity.UserRelation;
import com.thinkgem.jeesite.modules.edu.service.UserRelationService;

/**
 * 用户关系Controller
 * @author lala
 * @version 2015-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/userRelation")
public class UserRelationController extends BaseController {

	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public UserRelation get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return userRelationService.get(id);
		}else{
			return new UserRelation();
		}
	}
	
	@RequiresPermissions("edu:userRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserRelation userRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserRelation> page = userRelationService.find(new Page<UserRelation>(request, response), userRelation);
		model.addAttribute("user",euserService.get(userRelation.getEduUserByParentId().getId()));
        model.addAttribute("uid", userRelation.getEduUserByParentId().getId());
        model.addAttribute("page", page);
		return "modules/" + "edu/userRelationList";
	}

	@RequiresPermissions("edu:userRelation:view")
	@RequestMapping(value = "form")
	public String form(UserRelation userRelation, Model model) {
		model.addAttribute("userRelation", userRelation);
		Page<Euser> page = euserService.findUserWithoutRelation(new Page<Euser>(1, 100),userRelation.getEduUserByParentId().getId());
		model.addAttribute("euser",new Euser());
		model.addAttribute("page", page);
		model.addAttribute("user",euserService.get(userRelation.getEduUserByParentId().getId()));

		return "modules/" + "edu/userRelationForm";
	}

	@RequiresPermissions("edu:userRelation:view")
	@RequestMapping(value = "form/filter")
	public String form(Euser euser,UserRelation userRelation,HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("euser", euser);
		Page<Euser> page = euserService.findUserWithoutRelation(new Page<Euser>(request, response),userRelation.getEduUserByParentId().getId());
		model.addAttribute("user",euserService.get(userRelation.getEduUserByParentId().getId()));
		model.addAttribute("page", page);
		return "modules/" + "edu/userRelationForm";
	}

	@RequiresPermissions("edu:userRelation:edit")
	@RequestMapping(value = "save")
	public String save(UserRelation userRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userRelation)){
			return form(userRelation, model);
		}
		userRelationService.save(userRelation);
		addMessage(redirectAttributes, "保存用户关系成功");
		return "redirect:"+Global.getAdminPath()+"/edu/userRelation/form?eduUserByParentId.id="+userRelation.getEduUserByParentId().getId();
//		return "redirect:"+Global.getAdminPath()+"/edu/userRelation/?repage";
	}
	
	@RequiresPermissions("edu:userRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(UserRelation userRelation,String id, RedirectAttributes redirectAttributes) {
		userRelationService.delete(id);
		addMessage(redirectAttributes, "删除用户关系成功");
		return "redirect:"+Global.getAdminPath()+"/edu/userRelation/?eduUserByParentId.id="+userRelation.getEduUserByParentId().getId();
//		return "redirect:"+Global.getAdminPath()+"/edu/userRelation/?repage";
	}

}
