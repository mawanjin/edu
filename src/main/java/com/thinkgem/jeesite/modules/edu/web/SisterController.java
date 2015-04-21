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
import com.thinkgem.jeesite.modules.edu.entity.Sister;
import com.thinkgem.jeesite.modules.edu.service.SisterService;

/**
 * 知心姐姐Controller
 * @author lala
 * @version 2015-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/sister")
public class SisterController extends BaseController {

	@Autowired
	private SisterService sisterService;
	
	@ModelAttribute
	public Sister get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return sisterService.get(id);
		}else{
			return new Sister();
		}
	}
	
	@RequiresPermissions("edu:sister:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sister sister, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			sister.setCreateBy(user);
		}
        Page<Sister> page = sisterService.find(new Page<Sister>(request, response), sister); 
        model.addAttribute("page", page);
		return "modules/" + "edu/sisterList";
	}

	@RequiresPermissions("edu:sister:view")
	@RequestMapping(value = "form")
	public String form(Sister sister, Model model) {
		model.addAttribute("sister", sister);
		return "modules/" + "edu/sisterForm";
	}

	@RequiresPermissions("edu:sister:edit")
	@RequestMapping(value = "save")
	public String save(Sister sister, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sister)){
			return form(sister, model);
		}
		sister.setContent(StringEscapeUtils.unescapeHtml4(sister.getContent()));
		sisterService.save(sister);
		addMessage(redirectAttributes, "保存知心姐姐'" + sister.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/sister/?repage";
	}
	
	@RequiresPermissions("edu:sister:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		sisterService.delete(id);
		addMessage(redirectAttributes, "删除知心姐姐成功");
		return "redirect:"+Global.getAdminPath()+"/edu/sister/?repage";
	}

	@RequiresPermissions("edu:sister:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Sister sister = sisterService.get(id);
		sister.setStatus(status);
		sisterService.save(sister);
		if(status==1)
		addMessage(redirectAttributes, "发布知心姐姐成功");
		else
		addMessage(redirectAttributes, "取消发布知心姐姐成功");

		return "redirect:"+Global.getAdminPath()+"/edu/sister/?repage";
	}

}
