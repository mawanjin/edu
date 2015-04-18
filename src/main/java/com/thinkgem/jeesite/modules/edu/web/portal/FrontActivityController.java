/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.ActivityDto;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
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
 * 主题活动Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/activity")
public class FrontActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;
	

	@ResponseBody
	@RequestMapping(value = {"get"})
	public ActivityDto get(@RequestParam(required=true) String id) {
		ActivityDto rs = new ActivityDto();
		if (StringUtils.isNotBlank(id)){
			try {
				PropertyUtils.copyProperties(rs, activityService.get(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<ActivityDto> list(Activity activity, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Activity> activities = activityService.findAll();
		List<ActivityDto> rs = new ArrayList<ActivityDto>(0);

		if(activities!=null&&activities.size()>0){

			for(Activity activity1 :activities){
				ActivityDto activityDto = new ActivityDto();
				try {
					PropertyUtils.copyProperties(activityDto,activity1);
					rs.add(activityDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(activityDto.getImg())){
					activityDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+activityDto.getImg());
				}
			}
		}
		return rs;

	}





}
