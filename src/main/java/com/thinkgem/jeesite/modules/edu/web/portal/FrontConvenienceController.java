/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.entity.Convenience;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;
import com.thinkgem.jeesite.modules.edu.service.ConvenienceService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ConvenienceDto;
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
@RequestMapping(value = "${frontPath}/edu/convenience")
public class FrontConvenienceController extends BaseController {

	@Autowired
	private ConvenienceService convenienceService;
	

	@ResponseBody
	@RequestMapping(value = {"get"})
	public ConvenienceDto get(@RequestParam(required=true) String id) {
		ConvenienceDto rs = new ConvenienceDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, convenienceService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<ConvenienceDto> list(Convenience c, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Convenience> conveniences = convenienceService.findAll();
		List<ConvenienceDto> rs = new ArrayList<ConvenienceDto>(0);

		if(conveniences!=null&&conveniences.size()>0){

			for(Convenience convenience :conveniences){
				ConvenienceDto convenienceDto = new ConvenienceDto();
				try {
					PropertyUtils.copyProperties(convenienceDto,convenience);
					rs.add(convenienceDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(convenienceDto.getImg())){
					convenienceDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+convenienceDto.getImg());
				}
			}
		}
		return rs;

	}


}
