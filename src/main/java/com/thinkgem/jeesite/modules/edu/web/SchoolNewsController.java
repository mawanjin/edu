/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.service.SchoolService;
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
import com.thinkgem.jeesite.modules.edu.entity.SchoolNews;
import com.thinkgem.jeesite.modules.edu.service.SchoolNewsService;

/**
 * 学校新闻Controller
 * @author lala
 * @version 2015-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/schoolNews")
public class SchoolNewsController extends BaseController {

	@Autowired
	private SchoolNewsService schoolNewsService;

	@Autowired
	private SchoolService schoolService;
	
	@ModelAttribute
	public SchoolNews get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return schoolNewsService.get(id);
		}else{
			return new SchoolNews();
		}
	}
	
	@RequiresPermissions("edu:schoolNews:view")
	@RequestMapping(value = {"list", ""})
	public String list(SchoolNews schoolNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			schoolNews.setCreateBy(user);
		}
        Page<SchoolNews> page = schoolNewsService.find(new Page<SchoolNews>(request, response), schoolNews); 
        model.addAttribute("page", page);
		return "modules/" + "edu/schoolNewsList";
	}

	@RequiresPermissions("edu:schoolNews:view")
	@RequestMapping(value = "form")
	public String form(SchoolNews schoolNews, Model model) {
		model.addAttribute("schoolNews", schoolNews);
		model.addAttribute("schools", schoolService.findAll());

		return "modules/" + "edu/schoolNewsForm";
	}

	@RequiresPermissions("edu:schoolNews:edit")
	@RequestMapping(value = "save")
	public String save(SchoolNews schoolNews, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, schoolNews)){
			return form(schoolNews, model);
		}

		if(schoolNews.getTop().equals("1")){
			schoolNewsService.removeTop(schoolNews);
		}

		schoolNews.setContent(StringEscapeUtils.unescapeHtml4(schoolNews.getContent()));

		schoolNewsService.save(schoolNews);
		addMessage(redirectAttributes, "保存学校新闻'" + schoolNews.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/schoolNews/?repage";
	}
	
	@RequiresPermissions("edu:schoolNews:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		schoolNewsService.delete(id);
		addMessage(redirectAttributes, "删除学校新闻成功");
		return "redirect:"+Global.getAdminPath()+"/edu/schoolNews/?repage";
	}

	@RequiresPermissions("edu:schoolNews:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		SchoolNews schoolNews = schoolNewsService.get(id);
		schoolNews.setStatus(status);
		schoolNewsService.save(schoolNews);
		if(status==1)
		addMessage(redirectAttributes, "发布学校新闻成功");
		else
		addMessage(redirectAttributes, "取消发布学校新闻成功");

		return "redirect:"+Global.getAdminPath()+"/edu/schoolNews/?repage";
	}

}
