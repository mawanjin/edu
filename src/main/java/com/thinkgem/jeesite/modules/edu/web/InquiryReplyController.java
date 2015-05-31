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
import com.thinkgem.jeesite.modules.edu.entity.InquiryReply;
import com.thinkgem.jeesite.modules.edu.service.InquiryReplyService;

/**
 * 留言回复Controller
 * @author lala
 * @version 2015-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/inquiryReply")
public class InquiryReplyController extends BaseController {

	@Autowired
	private InquiryReplyService inquiryReplyService;
	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public InquiryReply get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return inquiryReplyService.get(id);
		}else{
			return new InquiryReply();
		}
	}
	
	@RequiresPermissions("edu:inquiryReply:view")
	@RequestMapping(value = {"list", ""})
	public String list(InquiryReply inquiryReply,int type, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			inquiryReply.setCreateBy(user);
		}
        Page<InquiryReply> page = inquiryReplyService.find(new Page<InquiryReply>(request, response), inquiryReply); 
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("inquiry", inquiryReply.getInquiry().getId());


		return "modules/" + "edu/inquiryReplyList";
	}

	@RequiresPermissions("edu:inquiryReply:view")
	@RequestMapping(value = "form")
	public String form(InquiryReply inquiryReply,int type, Model model) {
		model.addAttribute("inquiryReply", inquiryReply);
		model.addAttribute("type", type);
		model.addAttribute("users", euserService.findAll());

		return "modules/" + "edu/inquiryReplyForm";
	}

	@RequiresPermissions("edu:inquiryReply:edit")
	@RequestMapping(value = "save")
	public String save(InquiryReply inquiryReply,int type, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, inquiryReply)){
			return form(inquiryReply, type, model);
		}

		inquiryReplyService.save(inquiryReply);
		addMessage(redirectAttributes, "保存留言回复成功");
		return "redirect:"+Global.getAdminPath()+"/edu/inquiryReply/?repage&type="+type+"&inquiry.id="+inquiryReply.getInquiry().getId();
	}
	
	@RequiresPermissions("edu:inquiryReply:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		inquiryReplyService.delete(id);
		addMessage(redirectAttributes, "删除留言回复成功");
		return "redirect:"+Global.getAdminPath()+"/edu/inquiryReply/?repage";
	}

	@RequiresPermissions("edu:inquiryReply:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		InquiryReply inquiryReply = inquiryReplyService.get(id);
		inquiryReply.setStatus(status);
		inquiryReplyService.save(inquiryReply);
		if(status==1)
		addMessage(redirectAttributes, "发布留言回复成功");
		else
		addMessage(redirectAttributes, "取消发布留言回复成功");

		return "redirect:"+Global.getAdminPath()+"/edu/inquiryReply/?repage";
	}

}
