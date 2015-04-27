/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.entity.Report;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;
import com.thinkgem.jeesite.modules.edu.service.ReportService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 日常报告Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/report")
public class FrontReportController extends BaseController {

	@Autowired
	private ReportService reportService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public Report get(@RequestParam(required=true) String id) {
		Report report = reportService.get(id);
		return report;
	}

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<Report> list(int type, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Report> reports = reportService.findByType((byte) type);
		return reports;
	}

}
