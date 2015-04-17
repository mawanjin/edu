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
import com.thinkgem.jeesite.modules.edu.entity.Aboutus;
import com.thinkgem.jeesite.modules.edu.service.AboutusService;

/**
 * 关于我们Controller
 * @author lala
 * @version 2015-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/aboutus")
public class AboutusController extends BaseController {

	@Autowired
	private AboutusService aboutusService;
	
	@ModelAttribute
	public Aboutus get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return aboutusService.get(id);
		}else{
			return new Aboutus();
		}
	}
	
	@RequiresPermissions("edu:aboutus:view")
	@RequestMapping(value = {"list", ""})
	public String list(Aboutus aboutus, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			aboutus.setCreateBy(user);
		}
        Page<Aboutus> page = aboutusService.find(new Page<Aboutus>(request, response), aboutus); 
        model.addAttribute("page", page);
		return "modules/" + "edu/aboutusList";
	}

	@RequiresPermissions("edu:aboutus:view")
	@RequestMapping(value = "form")
	public String form(Aboutus aboutus, Model model) {
		model.addAttribute("aboutus", aboutus);
		return "modules/" + "edu/aboutusForm";
	}

	@RequiresPermissions("edu:aboutus:edit")
	@RequestMapping(value = "save")
	public String save(Aboutus aboutus, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aboutus)){
			return form(aboutus, model);
		}
		aboutus.setContent(StringEscapeUtils.unescapeHtml4(aboutus.getContent()));
		aboutusService.save(aboutus);
		addMessage(redirectAttributes, "保存关于我们成功");
		return "redirect:"+Global.getAdminPath()+"/edu/aboutus/?repage";
	}
	
	@RequiresPermissions("edu:aboutus:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		aboutusService.delete(id);
		addMessage(redirectAttributes, "删除关于我们成功");
		return "redirect:"+Global.getAdminPath()+"/edu/aboutus/?repage";
	}

	@RequiresPermissions("edu:aboutus:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Aboutus aboutus = aboutusService.get(id);
		aboutus.setStatus(status);
		aboutusService.save(aboutus);
		if(status==1)
		addMessage(redirectAttributes, "发布关于我们成功");
		else
		addMessage(redirectAttributes, "取消发布关于我们成功");

		return "redirect:"+Global.getAdminPath()+"/edu/aboutus/?repage";
	}

}
