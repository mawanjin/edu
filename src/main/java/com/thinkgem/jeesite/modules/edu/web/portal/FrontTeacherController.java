/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Major;
import com.thinkgem.jeesite.modules.edu.entity.School;
import com.thinkgem.jeesite.modules.edu.entity.Teacher;
import com.thinkgem.jeesite.modules.edu.service.MajorService;
import com.thinkgem.jeesite.modules.edu.service.SchoolService;
import com.thinkgem.jeesite.modules.edu.service.TeacherService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.SchoolDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.TeacherDto;
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
 * 学校Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/teacher")
public class FrontTeacherController extends BaseController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private MajorService majorService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public TeacherDto get(@RequestParam(required=false) String id) {
		TeacherDto dto = new TeacherDto();
		if (StringUtils.isNotBlank(id)){
			Teacher teacher = teacherService.get(id);
			try {
				PropertyUtils.copyProperties(dto,teacher);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = {"list", ""})
	public List<TeacherDto> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TeacherDto> rs = new ArrayList<TeacherDto>(0);
		List<Teacher> teachers = teacherService.findAll();

		if(teachers!=null&&teachers.size()>0){
			for(Teacher teacher :teachers){
				TeacherDto teacherDto = new TeacherDto();
				try {
					PropertyUtils.copyProperties(teacherDto,teacher);
					rs.add(teacherDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(teacherDto.getImg())){
					teacherDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+teacherDto.getImg());
				}
				if(teacher.getMajor()!=null){
					teacherDto.setMajorId(teacher.getMajor().getId());
					teacherDto.setMajorName(teacher.getMajor().getName());
				}

			}
		}

		return rs;
	}


	@ResponseBody
	@RequestMapping(value = {"majorList"})
	public List<Major> listMajor() {
		List<Major> majors = majorService.findAll();
		return majors;
	}

}
