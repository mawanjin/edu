/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.EuserService;
import com.thinkgem.jeesite.modules.edu.service.PersonalQuestionService;
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
import com.thinkgem.jeesite.modules.edu.entity.PersonalQuestionReply;
import com.thinkgem.jeesite.modules.edu.service.PersonalQuestionReplyService;

/**
 * 个人问答回复Controller
 * @author lala
 * @version 2015-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/personalQuestionReply")
public class PersonalQuestionReplyController extends BaseController {

	@Autowired
	private PersonalQuestionReplyService personalQuestionReplyService;

	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public PersonalQuestionReply get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return personalQuestionReplyService.get(id);
		}else{
			return new PersonalQuestionReply();
		}
	}
	
	@RequiresPermissions("edu:personalQuestionReply:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonalQuestionReply personalQuestionReply, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			personalQuestionReply.setCreateBy(user);
		}
        Page<PersonalQuestionReply> page = personalQuestionReplyService.find(new Page<PersonalQuestionReply>(request, response), personalQuestionReply);

        model.addAttribute("page", page);
        model.addAttribute("questionId", personalQuestionReply.getPersonalQuestion().getId());
		return "modules/" + "edu/personalQuestionReplyList";
	}

	@RequiresPermissions("edu:personalQuestionReply:view")
	@RequestMapping(value = "form")
	public String form(PersonalQuestionReply personalQuestionReply, Model model) {
		model.addAttribute("personalQuestionReply", personalQuestionReply);
		model.addAttribute("users",euserService.findAll());
		return "modules/" + "edu/personalQuestionReplyForm";
	}

	@RequiresPermissions("edu:personalQuestionReply:edit")
	@RequestMapping(value = "save")
	public String save(PersonalQuestionReply personalQuestionReply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personalQuestionReply)){
			return form(personalQuestionReply, model);
		}
		personalQuestionReplyService.save(personalQuestionReply);
		addMessage(redirectAttributes, "保存个人问答回复成功");
		return "redirect:"+Global.getAdminPath()+"/edu/personalQuestionReply/?repage&personalQuestion.id="+personalQuestionReply.getPersonalQuestion().getId();
	}
	
	@RequiresPermissions("edu:personalQuestionReply:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, String questionId,RedirectAttributes redirectAttributes) {
		personalQuestionReplyService.delete(id);
		addMessage(redirectAttributes, "删除个人问答回复成功");
		return "redirect:"+Global.getAdminPath()+"/edu/personalQuestionReply/?repage&personalQuestion.id="+questionId;
	}

	@RequiresPermissions("edu:personalQuestionReply:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		PersonalQuestionReply personalQuestionReply = personalQuestionReplyService.get(id);
		personalQuestionReply.setStatus(status);
		personalQuestionReplyService.save(personalQuestionReply);
		if(status==1)
		addMessage(redirectAttributes, "发布个人问答回复成功");
		else
		addMessage(redirectAttributes, "取消发布个人问答回复成功");

		return "redirect:"+Global.getAdminPath()+"/edu/personalQuestionReply/?repage";
	}

}
