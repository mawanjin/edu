/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.*;
import com.thinkgem.jeesite.modules.edu.service.SisterQuestionService;
import com.thinkgem.jeesite.modules.edu.service.SisterService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.SisterDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 知心姐姐Controller
 * @author lala
 * @version 2015-04-01
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/sister")
public class FrontSisterController extends BaseController {

	@Autowired
	private SisterService sisterService;
	@Autowired
	private SisterQuestionService sisterQuestionService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public SisterDto get(@RequestParam(required=false) String id) {
		SisterDto dto = new SisterDto();
		if (StringUtils.isNotBlank(id)){
			Sister sister = sisterService.get(id);
			try {
				PropertyUtils.copyProperties(dto,sister);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = {"list", ""})
	public List<SisterDto> list(HttpServletRequest request) {
		List<SisterDto> rs = new ArrayList<SisterDto>(0);
		List<Sister> sisters = sisterService.findAll();

		if(sisters!=null&&sisters.size()>0){
			for(Sister sister :sisters){
				SisterDto sisterDto = new SisterDto();
				try {
					PropertyUtils.copyProperties(sisterDto,sister);
					rs.add(sisterDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(sisterDto.getImg())){
					sisterDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+sisterDto.getImg());
				}
			}
		}

		return rs;
	}

	@ResponseBody
	@RequestMapping(value = {"msg"})
	public boolean msg(String sisterId,String uid,String title,String msg,HttpServletRequest request) {
		SisterQuestion sisterQuestion = new SisterQuestion();
		Euser user = new Euser();
		user.setId(uid);
		sisterQuestion.setUser(user);

		Sister sister = new Sister();
		sister.setId(sisterId);
		sisterQuestion.setSister(sister);
		sisterQuestion.setTitle(title);
		sisterQuestion.setContent(msg);
		sisterQuestionService.save(sisterQuestion);
		return true;
	}



}
