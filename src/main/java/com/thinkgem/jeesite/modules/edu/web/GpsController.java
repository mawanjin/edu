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
import com.thinkgem.jeesite.modules.edu.entity.Gps;
import com.thinkgem.jeesite.modules.edu.service.GpsService;

/**
 * gpsController
 * @author lala
 * @version 2015-04-12
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/gps")
public class GpsController extends BaseController {

	@Autowired
	private GpsService gpsService;

	@Autowired
	private EuserService euserService;
	
	@ModelAttribute
	public Gps get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return gpsService.get(id);
		}else{
			return new Gps();
		}
	}
	
	@RequiresPermissions("edu:gps:view")
	@RequestMapping(value = {"list", ""})
	public String list(Gps gps, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			gps.setCreateBy(user);
		}
        Page<Gps> page = gpsService.find(new Page<Gps>(request, response), gps); 
        model.addAttribute("page", page);
		return "modules/" + "edu/gpsList";
	}

	@RequiresPermissions("edu:gps:view")
	@RequestMapping(value = "form")
	public String form(Gps gps, Model model) {

		model.addAttribute("gps", gps);
		model.addAttribute("users", euserService.findAll());
		return "modules/" + "edu/gpsForm";
	}

	@RequiresPermissions("edu:gps:edit")
	@RequestMapping(value = "save")
	public String save(Gps gps, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gps)){
			return form(gps, model);
		}
		gpsService.save(gps);
		addMessage(redirectAttributes, "保存gps成功");
		return "redirect:"+Global.getAdminPath()+"/edu/gps/?repage";
	}
	
	@RequiresPermissions("edu:gps:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		gpsService.delete(id);
		addMessage(redirectAttributes, "删除gps成功");
		return "redirect:"+Global.getAdminPath()+"/edu/gps/?repage";
	}

}
