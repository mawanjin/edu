/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Gps;
import com.thinkgem.jeesite.modules.edu.entity.Suggestion;
import com.thinkgem.jeesite.modules.edu.service.GpsService;
import com.thinkgem.jeesite.modules.edu.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * GPS Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/gps")
public class FrontGpsController extends BaseController {

	@Autowired
	private GpsService gpsService;

	@ResponseBody
	@RequestMapping(value = {""})
	public List<Gps> list(String uid) {
		return gpsService.findAll(uid);
	}

}
