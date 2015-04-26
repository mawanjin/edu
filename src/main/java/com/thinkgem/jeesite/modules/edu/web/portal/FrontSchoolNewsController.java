/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.entity.SchoolNews;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;
import com.thinkgem.jeesite.modules.edu.service.SchoolNewsService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.SchoolNewsDto;
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
@RequestMapping(value = "${frontPath}/edu/schoolNews")
public class FrontSchoolNewsController extends BaseController {

	@Autowired
	private SchoolNewsService schoolNewsService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public SchoolNewsDto get(@RequestParam(required=true) String id) {
		SchoolNewsDto rs = new SchoolNewsDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, schoolNewsService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<SchoolNewsDto> list(String schoolId, HttpServletRequest request, HttpServletResponse response, Model model) {

		List<SchoolNews> schoolNewses = schoolNewsService.findBySchool(schoolId);
		List<SchoolNewsDto> rs = new ArrayList<SchoolNewsDto>(0);

		if(schoolNewses!=null&&schoolNewses.size()>0){

			for(SchoolNews schoolNews :schoolNewses){
				SchoolNewsDto schoolNewsDto = new SchoolNewsDto();
				try {
					PropertyUtils.copyProperties(schoolNewsDto,schoolNews);
					rs.add(schoolNewsDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(schoolNewsDto.getImg())){
					schoolNewsDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+schoolNewsDto.getImg());
				}
			}
		}
		return rs;
	}

}
