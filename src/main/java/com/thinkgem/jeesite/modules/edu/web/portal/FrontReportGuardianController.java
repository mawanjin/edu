/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.entity.ReportGuardian;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;
import com.thinkgem.jeesite.modules.edu.service.ReportGuardianService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ReportGuardianDto;
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
 * 主题活动Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/reportGuardian")
public class FrontReportGuardianController extends BaseController {

	@Autowired
	private ReportGuardianService reportGuardianService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public ReportGuardianDto get(@RequestParam(required=true) String id) {
		ReportGuardianDto rs = new ReportGuardianDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, reportGuardianService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<ReportGuardianDto> list(String uid, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ReportGuardian> reportGuardians = reportGuardianService.findByUid(uid);
		List<ReportGuardianDto> rs = new ArrayList<ReportGuardianDto>(0);

		if(reportGuardians!=null&&reportGuardians.size()>0){

			for(ReportGuardian reportGuardian :reportGuardians){
				ReportGuardianDto reportGuardianDto = new ReportGuardianDto();
				try {
					PropertyUtils.copyProperties(reportGuardianDto,reportGuardian);
					rs.add(reportGuardianDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return rs;

	}

}
