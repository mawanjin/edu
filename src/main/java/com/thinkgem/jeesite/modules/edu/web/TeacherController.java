/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.MajorService;
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
import com.thinkgem.jeesite.modules.edu.entity.Teacher;
import com.thinkgem.jeesite.modules.edu.service.TeacherService;

/**
 * 名师Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/teacher")
public class TeacherController extends BaseController {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private MajorService majorService;

	@ModelAttribute
	public Teacher get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return teacherService.get(id);
		}else{
			return new Teacher();
		}
	}
	
	@RequiresPermissions("edu:teacher:view")
	@RequestMapping(value = {"list", ""})
	public String list(Teacher teacher, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			teacher.setCreateBy(user);
		}
        Page<Teacher> page = teacherService.find(new Page<Teacher>(request, response), teacher); 
        model.addAttribute("page", page);
		return "modules/" + "edu/teacherList";
	}

	@RequiresPermissions("edu:teacher:view")
	@RequestMapping(value = "form")
	public String form(Teacher teacher, Model model) {
		model.addAttribute("majors", majorService.findAll());
		model.addAttribute("teacher", teacher);
		return "modules/" + "edu/teacherForm";
	}

	@RequiresPermissions("edu:teacher:edit")
	@RequestMapping(value = "save")
	public String save(Teacher teacher, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, teacher)){
			return form(teacher, model);
		}

		teacher.setContent(StringEscapeUtils.unescapeHtml4(teacher.getContent()));
		teacherService.save(teacher);
		addMessage(redirectAttributes, "保存名师'" + teacher.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/teacher/?repage";
	}
	
	@RequiresPermissions("edu:teacher:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		teacherService.delete(id);
		addMessage(redirectAttributes, "删除名师成功");
		return "redirect:"+Global.getAdminPath()+"/edu/teacher/?repage";
	}

	@RequiresPermissions("edu:teacher:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Teacher teacher = teacherService.get(id);
		teacher.setStatus(status);
		teacherService.save(teacher);
		if(status==1)
		addMessage(redirectAttributes, "发布名师成功");
		else
		addMessage(redirectAttributes, "取消发布名师成功");

		return "redirect:"+Global.getAdminPath()+"/edu/teacher/?repage";
	}

}
