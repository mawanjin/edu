/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.entity.Euser;
import com.thinkgem.jeesite.modules.edu.service.EuserService;
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
import com.thinkgem.jeesite.modules.edu.entity.Report;
import com.thinkgem.jeesite.modules.edu.service.ReportService;

import java.util.List;

/**
 * 日常报告Controller
 * @author lala
 * @version 2015-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/report")
public class ReportController extends BaseController {

	@Autowired
	private ReportService reportService;

    @Autowired
    private EuserService euserService;
	
	@ModelAttribute
	public Report get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return reportService.get(id);
		}else{
			return new Report();
		}
	}
	
	@RequiresPermissions("edu:report:view")
	@RequestMapping(value = {"list", ""})
	public String list(Report report, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			report.setCreateBy(user);
		}
        Page<Report> page = reportService.find(new Page<Report>(request, response), report); 
        model.addAttribute("page", page);
		return "modules/" + "edu/reportList";
	}

	@RequiresPermissions("edu:report:view")
	@RequestMapping(value = "form")
	public String form(Report report, Model model) {
        List<Euser> users = euserService.findAllHouseHolder();
		model.addAttribute("users", users);
		model.addAttribute("report", report);
		return "modules/" + "edu/reportForm";
	}

	@RequiresPermissions("edu:report:edit")
	@RequestMapping(value = "save")
	public String save(Report report, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, report)){
			return form(report, model);
		}

		report.setContent(StringEscapeUtils.unescapeHtml4(report.getContent()));
		reportService.save(report);
		addMessage(redirectAttributes, "保存日常报告'" + report.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/report/?repage";
	}
	
	@RequiresPermissions("edu:report:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		reportService.delete(id);
		addMessage(redirectAttributes, "删除日常报告成功");
		return "redirect:"+Global.getAdminPath()+"/edu/report/?repage";
	}

	@RequiresPermissions("edu:report:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Report report = reportService.get(id);
		report.setStatus(status);
		reportService.save(report);
		if(status==1)
		addMessage(redirectAttributes, "发布日常报告成功");
		else
		addMessage(redirectAttributes, "取消发布日常报告成功");

		return "redirect:"+Global.getAdminPath()+"/edu/report/?repage";
	}

}
