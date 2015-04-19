/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.CountryService;
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
import com.thinkgem.jeesite.modules.edu.entity.School;
import com.thinkgem.jeesite.modules.edu.service.SchoolService;

/**
 * 学校Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/school")
public class SchoolController extends BaseController {

	@Autowired
	private SchoolService schoolService;
	@Autowired
	private CountryService countryService;
	
	@ModelAttribute
	public School get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return schoolService.get(id);
		}else{
			return new School();
		}
	}
	
	@RequiresPermissions("edu:school:view")
	@RequestMapping(value = {"list", ""})
	public String list(School school, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			school.setCreateBy(user);
		}
        Page<School> page = schoolService.find(new Page<School>(request, response), school); 
        model.addAttribute("page", page);
		return "modules/" + "edu/schoolList";
	}

	@RequiresPermissions("edu:school:view")
	@RequestMapping(value = "form")
	public String form(School school, Model model) {
		school.setStatus(new Byte("0"));
		school.setGrade(new Byte("0"));


		model.addAttribute("countries",countryService.findAllNormal());

		model.addAttribute("school", school);
		return "modules/" + "edu/schoolForm";
	}

	@RequiresPermissions("edu:school:edit")
	@RequestMapping(value = "save")
	public String save(School school, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, school)){
			return form(school, model);
		}
		school.setContent(StringEscapeUtils.unescapeHtml4(school.getContent()));
		schoolService.save(school);
		addMessage(redirectAttributes, "保存学校'" + school.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/school/?repage";
	}
	
	@RequiresPermissions("edu:school:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		schoolService.delete(id);
		addMessage(redirectAttributes, "删除学校成功");
		return "redirect:"+Global.getAdminPath()+"/edu/school/?repage";
	}

	@RequiresPermissions("edu:school:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		School school = schoolService.get(id);
		school.setStatus(status);
		schoolService.save(school);
		if(status==1)
		addMessage(redirectAttributes, "发布学校成功");
		else
		addMessage(redirectAttributes, "取消发布学校成功");

		return "redirect:"+Global.getAdminPath()+"/edu/school/?repage";
	}

}
