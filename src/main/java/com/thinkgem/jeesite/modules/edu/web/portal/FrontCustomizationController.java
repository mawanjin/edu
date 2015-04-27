/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Customization;
import com.thinkgem.jeesite.modules.edu.entity.CustomizationMessage;
import com.thinkgem.jeesite.modules.edu.entity.Euser;
import com.thinkgem.jeesite.modules.edu.service.CustomizationMessageService;
import com.thinkgem.jeesite.modules.edu.service.CustomizationService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.CustomizationDto;
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
@RequestMapping(value = "${frontPath}/edu/customization")
public class FrontCustomizationController extends BaseController {

	@Autowired
	private CustomizationService customizationService;

	@Autowired
	private CustomizationMessageService customizationMessageService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public CustomizationDto get(@RequestParam(required=true) String id) {
		CustomizationDto rs = new CustomizationDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, customizationService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<CustomizationDto> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Customization> customizations = customizationService.findAll();
		List<CustomizationDto> rs = new ArrayList<CustomizationDto>(0);

		if(customizations!=null&&customizations.size()>0){

			for(Customization customization :customizations){
				CustomizationDto customizationDto = new CustomizationDto();
				try {
					PropertyUtils.copyProperties(customizationDto,customization);
					rs.add(customizationDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(customizationDto.getImg())){
					customizationDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+customizationDto.getImg());
				}
			}
		}
		return rs;

	}


	@ResponseBody
	@RequestMapping(value = {"msg"})
	public boolean msg(@RequestParam(required=true) String uid,String msg) {
		CustomizationMessage customizationMessage = new CustomizationMessage();
		Euser euser = new Euser();
		euser.setId(uid);
		customizationMessage.setUser(euser);
		customizationMessage.setMsg(msg);
		try{
			customizationMessageService.save(customizationMessage);
			return true;
		}catch (Exception e){}

		return false;
	}

}
