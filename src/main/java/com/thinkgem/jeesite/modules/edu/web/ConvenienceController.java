/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.edu.entity.Convenience;
import com.thinkgem.jeesite.modules.edu.service.ConvenienceService;

/**
 * 生活便利Controller
 * @author lala
 * @version 2015-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/convenience")
public class ConvenienceController extends BaseController {

	@Autowired
	private ConvenienceService convenienceService;
	
	@ModelAttribute
	public Convenience get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return convenienceService.get(id);
		}else{
			return new Convenience();
		}
	}
	
	@RequiresPermissions("edu:convenience:view")
	@RequestMapping(value = {"list", ""})
	public String list(Convenience convenience, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			convenience.setCreateBy(user);
		}
        Page<Convenience> page = convenienceService.find(new Page<Convenience>(request, response), convenience); 
        model.addAttribute("page", page);
		return "modules/" + "edu/convenienceList";
	}

	@RequiresPermissions("edu:convenience:view")
	@RequestMapping(value = "form")
	public String form(Convenience convenience, Model model) {
		model.addAttribute("convenience", convenience);
		return "modules/" + "edu/convenienceForm";
	}

	@RequiresPermissions("edu:convenience:edit")
	@RequestMapping(value = "save")
	public String save(Convenience convenience, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, convenience)){
			return form(convenience, model);
		}
		convenience.setContent(StringEscapeUtils.unescapeHtml4(convenience.getContent()));
		convenienceService.save(convenience);
		addMessage(redirectAttributes, "保存生活便利'" + convenience.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/convenience/?repage";
	}
	
	@RequiresPermissions("edu:convenience:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		convenienceService.delete(id);
		addMessage(redirectAttributes, "删除生活便利成功");
		return "redirect:"+Global.getAdminPath()+"/edu/convenience/?repage";
	}

	@RequiresPermissions("edu:convenience:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Convenience convenience = convenienceService.get(id);
		convenience.setStatus(status);
		convenienceService.save(convenience);
		if(status==1)
		addMessage(redirectAttributes, "发布生活便利成功");
		else
		addMessage(redirectAttributes, "取消发布生活便利成功");

		return "redirect:"+Global.getAdminPath()+"/edu/convenience/?repage";
	}

}
