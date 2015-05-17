/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.entity.School;
import com.thinkgem.jeesite.modules.edu.service.SchoolService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.SchoolDto;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 学校Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/school")
public class FrontSchoolController extends BaseController {

	@Autowired
	private SchoolService schoolService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public SchoolDto get(@RequestParam(required=false) String id) {
		SchoolDto dto = new SchoolDto();
		if (StringUtils.isNotBlank(id)){
			School school = schoolService.get(id);
			try {
				PropertyUtils.copyProperties(dto,school);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = {"list", ""})
	public List<SchoolDto> list(School school, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SchoolDto> rs = new ArrayList<SchoolDto>(0);
		List<School> schools = schoolService.findAllSchool();

		if(schools!=null&&schools.size()>0){
			for(School school1 :schools){
				SchoolDto schoolDto = new SchoolDto();
				try {
					PropertyUtils.copyProperties(schoolDto,school1);
					if(StringUtils.isNotEmpty(schoolDto.getContent())){
						String domain = "http://"+request.getServerName();
						schoolDto.setContent(schoolDto.getContent().replaceAll("\\/userfiles",domain+"\\/userfiles"));
					}
					rs.add(schoolDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(schoolDto.getImg())){
					schoolDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+schoolDto.getImg());
				}

				if(StringUtils.isNotEmpty(schoolDto.getBadge())){
					schoolDto.setBadge("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/admin/sys/fileupload/get?getthumb=" + schoolDto.getBadge());
				}
			}
		}

		return rs;
	}


	/**
	 *
	 * 国家固定死就三个国家，前台页面写死。
	 *
	 * @param id 67be4291d55d4341ae74ca803372b97d 美国 3a589f5d09a24e3b8d23ace5336aed98澳大利亚 cd08ec3109e4488b8c8b7f4d6237ea63英国
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"getByCountryId"})
	public List<SchoolDto> getByCountryId(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SchoolDto> rs = new ArrayList<SchoolDto>(0);
		List<School> schools = schoolService.findByCountry(id);

		if(schools!=null&&schools.size()>0){
			for(School school1 :schools){
				SchoolDto schoolDto = new SchoolDto();
				try {
					PropertyUtils.copyProperties(schoolDto,school1);
					rs.add(schoolDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(schoolDto.getImg())){
					schoolDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+schoolDto.getImg());
				}

				if(StringUtils.isNotEmpty(schoolDto.getBadge())){
					schoolDto.setBadge("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/admin/sys/fileupload/get?getthumb=" + schoolDto.getBadge());
				}
			}
		}

		return rs;
	}

}
