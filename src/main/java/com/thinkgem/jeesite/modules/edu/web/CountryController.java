/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.edu.entity.Country;
import com.thinkgem.jeesite.modules.edu.service.CountryService;

/**
 * 国家Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/country")
public class CountryController extends BaseController {

	@Autowired
	private CountryService countryService;
	
	@ModelAttribute
	public Country get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return countryService.get(id);
		}else{
			return new Country();
		}
	}
	
	@RequiresPermissions("edu:country:view")
	@RequestMapping(value = {"list", ""})
	public String list(Country country, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			country.setCreateBy(user);
		}
        Page<Country> page = countryService.find(new Page<Country>(request, response), country); 
        model.addAttribute("page", page);
		return "modules/" + "edu/countryList";
	}

	@RequiresPermissions("edu:country:view")
	@RequestMapping(value = "form")
	public String form(Country country, Model model) {
		if(country==null)country=new Country();
		country.setHot(new Byte("0"));
		model.addAttribute("country", country);
		return "modules/" + "edu/countryForm";
	}

	@RequiresPermissions("edu:country:edit")
	@RequestMapping(value = "save")
	public String save(Country country, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, country)){
			return form(country, model);
		}
		countryService.save(country);
		addMessage(redirectAttributes, "保存国家'" + country.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/country/?repage";
	}
	
	@RequiresPermissions("edu:country:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		countryService.delete(id);
		addMessage(redirectAttributes, "删除国家成功");
		return "redirect:"+Global.getAdminPath()+"/edu/country/?repage";
	}

}
