/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.EuserService;
import com.thinkgem.jeesite.modules.edu.service.SisterService;
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
import com.thinkgem.jeesite.modules.edu.entity.SisterQuestion;
import com.thinkgem.jeesite.modules.edu.service.SisterQuestionService;

/**
 * 留言咨询Controller
 * @author lala
 * @version 2015-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/sisterQuestion")
public class SisterQuestionController extends BaseController {

	@Autowired
	private SisterQuestionService sisterQuestionService;

	@Autowired
	private EuserService euserService;

	@Autowired
	private SisterService sisterService;
	
	@ModelAttribute
	public SisterQuestion get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return sisterQuestionService.get(id);
		}else{
			return new SisterQuestion();
		}
	}
	
	@RequiresPermissions("edu:sisterQuestion:view")
	@RequestMapping(value = {"list", ""})
	public String list(SisterQuestion sisterQuestion, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			sisterQuestion.setCreateBy(user);
		}
        Page<SisterQuestion> page = sisterQuestionService.find(new Page<SisterQuestion>(request, response), sisterQuestion); 
        model.addAttribute("page", page);
		return "modules/" + "edu/sisterQuestionList";
	}

	@RequiresPermissions("edu:sisterQuestion:view")
	@RequestMapping(value = "form")
	public String form(SisterQuestion sisterQuestion, Model model) {
		model.addAttribute("sisterQuestion", sisterQuestion);
		model.addAttribute("users",euserService.findAll());
		model.addAttribute("sisters",sisterService.findAll());

		return "modules/" + "edu/sisterQuestionForm";
	}

	@RequiresPermissions("edu:sisterQuestion:edit")
	@RequestMapping(value = "save")
	public String save(SisterQuestion sisterQuestion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sisterQuestion)){
			return form(sisterQuestion, model);
		}
		sisterQuestionService.save(sisterQuestion);
		addMessage(redirectAttributes, "保存留言咨询'" + sisterQuestion.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/sisterQuestion/?repage";
	}
	
	@RequiresPermissions("edu:sisterQuestion:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		sisterQuestionService.delete(id);
		addMessage(redirectAttributes, "删除留言咨询成功");
		return "redirect:"+Global.getAdminPath()+"/edu/sisterQuestion/?repage";
	}

	@RequiresPermissions("edu:sisterQuestion:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		SisterQuestion sisterQuestion = sisterQuestionService.get(id);
		sisterQuestion.setStatus(status);
		sisterQuestionService.save(sisterQuestion);
		if(status==1)
		addMessage(redirectAttributes, "发布留言咨询成功");
		else
		addMessage(redirectAttributes, "取消发布留言咨询成功");

		return "redirect:"+Global.getAdminPath()+"/edu/sisterQuestion/?repage";
	}

}
