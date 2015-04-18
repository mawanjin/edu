/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Convenience;
import com.thinkgem.jeesite.modules.edu.entity.Emergency;
import com.thinkgem.jeesite.modules.edu.service.ConvenienceService;
import com.thinkgem.jeesite.modules.edu.service.EmergencyService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ConvenienceDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.EmergencyDto;
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
@RequestMapping(value = "${frontPath}/edu/emergency")
public class FrontEmergencyController extends BaseController {

	@Autowired
	private EmergencyService emergencyService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public EmergencyDto get(@RequestParam(required=true) String id) {
		EmergencyDto rs = new EmergencyDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, emergencyService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<EmergencyDto> list(Convenience c, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Emergency> emergencies = emergencyService.findAll();
		List<EmergencyDto> rs = new ArrayList<EmergencyDto>(0);

		if(emergencies!=null&&emergencies.size()>0){

			for(Emergency emergency :emergencies){
				EmergencyDto emergencyDto = new EmergencyDto();
				try {
					PropertyUtils.copyProperties(emergencyDto,emergency);
					rs.add(emergencyDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(emergencyDto.getImg())){
					emergencyDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+emergencyDto.getImg());
				}
			}
		}
		return rs;

	}


}
