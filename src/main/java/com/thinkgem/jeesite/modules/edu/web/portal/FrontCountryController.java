/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Convenience;
import com.thinkgem.jeesite.modules.edu.entity.Country;
import com.thinkgem.jeesite.modules.edu.entity.Emergency;
import com.thinkgem.jeesite.modules.edu.service.CountryService;
import com.thinkgem.jeesite.modules.edu.service.EmergencyService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.CountryDto;
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
@RequestMapping(value = "${frontPath}/edu/country")
public class FrontCountryController extends BaseController {

	@Autowired
	private CountryService countryService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public CountryDto get(@RequestParam(required=true) String id) {
		CountryDto rs = new CountryDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, countryService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<CountryDto> list(Convenience c, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Country> countries = countryService.findAll();
		List<CountryDto> rs = new ArrayList<CountryDto>(0);

		if(countries!=null&&countries.size()>0){

			for(Country country :countries){
				CountryDto countryDto = new CountryDto();
				try {
					PropertyUtils.copyProperties(countryDto,country);
					rs.add(countryDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(countryDto.getImg())){
					countryDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+countryDto.getImg());
				}
			}
		}
		return rs;

	}


}
