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
import com.thinkgem.jeesite.modules.edu.entity.ReportGuardian;
import com.thinkgem.jeesite.modules.edu.service.ReportGuardianService;

/**
 * 监护人报告Controller
 * @author lala
 * @version 2015-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/reportGuardian")
public class ReportGuardianController extends BaseController {

	@Autowired
	private ReportGuardianService reportGuardianService;
	
	@ModelAttribute
	public ReportGuardian get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return reportGuardianService.get(id);
		}else{
			return new ReportGuardian();
		}
	}
	
	@RequiresPermissions("edu:reportGuardian:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportGuardian reportGuardian, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			reportGuardian.setCreateBy(user);
		}
        Page<ReportGuardian> page = reportGuardianService.find(new Page<ReportGuardian>(request, response), reportGuardian); 
        model.addAttribute("page", page);
        model.addAttribute("uid", reportGuardian.getUser().getId());

		return "modules/" + "edu/reportGuardianList";
	}

	@RequiresPermissions("edu:reportGuardian:view")
	@RequestMapping(value = "form")
	public String form(ReportGuardian reportGuardian, Model model) {
		model.addAttribute("reportGuardian", reportGuardian);
		model.addAttribute("uid", reportGuardian.getUser().getId());
		return "modules/" + "edu/reportGuardianForm";
	}

	@RequiresPermissions("edu:reportGuardian:edit")
	@RequestMapping(value = "save")
	public String save(ReportGuardian reportGuardian, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportGuardian)){
			return form(reportGuardian, model);
		}
		reportGuardian.setContent(StringEscapeUtils.unescapeHtml4(
				reportGuardian.getContent()));

		reportGuardianService.save(reportGuardian);
		addMessage(redirectAttributes, "保存监护人报告'" + reportGuardian.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/reportGuardian/?user.id="+reportGuardian.getUser().getId();
	}
	
	@RequiresPermissions("edu:reportGuardian:edit")
	@RequestMapping(value = "delete")
	public String delete(String id,ReportGuardian reportGuardian ,RedirectAttributes redirectAttributes) {
		reportGuardianService.delete(id);
		addMessage(redirectAttributes, "删除监护人报告成功");
		return "redirect:"+Global.getAdminPath()+"/edu/reportGuardian/?user.id="+reportGuardian.getUser().getId();
	}

	@RequiresPermissions("edu:reportGuardian:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		ReportGuardian reportGuardian = reportGuardianService.get(id);
		reportGuardian.setStatus(status);
		reportGuardianService.save(reportGuardian);
		if(status==1)
		addMessage(redirectAttributes, "发布监护人报告成功");
		else
		addMessage(redirectAttributes, "取消发布监护人报告成功");

		return "redirect:"+Global.getAdminPath()+"/edu/reportGuardian/?repage";
	}

}
