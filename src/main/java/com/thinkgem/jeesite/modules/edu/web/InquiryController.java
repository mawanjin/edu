/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.edu.entity.Inquiry;
import com.thinkgem.jeesite.modules.edu.service.InquiryService;

/**
 * 留言咨询Controller
 * @author lala
 * @version 2015-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/inquiry")
public class InquiryController extends BaseController {

	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public Inquiry get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return inquiryService.get(id);
		}else{
			return new Inquiry();
		}
	}
	
	@RequiresPermissions("edu:inquiry:view")
	@RequestMapping(value = {"list", ""})
	public String list(Inquiry inquiry, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			inquiry.setCreateBy(user);
		}
        Page<Inquiry> page = inquiryService.find(new Page<Inquiry>(request, response), inquiry); 
        model.addAttribute("page", page);
        model.addAttribute("type", inquiry.getType());
		return "modules/" + "edu/inquiryList";
	}

	@RequiresPermissions("edu:inquiry:view")
	@RequestMapping(value = "form")
	public String form(Inquiry inquiry, Model model) {
		model.addAttribute("inquiry", inquiry);
		model.addAttribute("users", euserService.findAll());
		return "modules/" + "edu/inquiryForm";
	}

	@RequiresPermissions("edu:inquiry:edit")
	@RequestMapping(value = "save")
	public String save(Inquiry inquiry, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, inquiry)){
			return form(inquiry, model);
		}
		inquiryService.save(inquiry);
		addMessage(redirectAttributes, "保存留言咨询'" + inquiry.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/inquiry/?repage";
	}
	
	@RequiresPermissions("edu:inquiry:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		inquiryService.delete(id);
		addMessage(redirectAttributes, "删除留言咨询成功");
		return "redirect:"+Global.getAdminPath()+"/edu/inquiry/?repage";
	}

	@RequiresPermissions("edu:inquiry:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Inquiry inquiry = inquiryService.get(id);
		inquiry.setStatus(status);
		inquiryService.save(inquiry);
		if(status==1)
		addMessage(redirectAttributes, "发布留言咨询成功");
		else
		addMessage(redirectAttributes, "取消发布留言咨询成功");

		return "redirect:"+Global.getAdminPath()+"/edu/inquiry/?repage";
	}

}
