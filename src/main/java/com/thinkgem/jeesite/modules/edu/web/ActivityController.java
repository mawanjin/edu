/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.edu.entity.Activity;
import com.thinkgem.jeesite.modules.edu.service.ActivityService;

/**
 * 主题活动Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;
	
	@ModelAttribute
	public Activity get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return activityService.get(id);
		}else{
			return new Activity();
		}
	}
	
	@RequiresPermissions("edu:activity:view")
	@RequestMapping(value = {"list", ""})
	public String list(Activity activity, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			activity.setCreateBy(user);
		}
        Page<Activity> page = activityService.find(new Page<Activity>(request, response), activity); 
        model.addAttribute("page", page);
		return "modules/" + "edu/activityList";
	}

	@RequiresPermissions("edu:activity:view")
	@RequestMapping(value = "form")
	public String form(Activity activity, Model model) {
		model.addAttribute("activity", activity);
		return "modules/" + "edu/activityForm";
	}

	@RequiresPermissions("edu:activity:edit")
	@RequestMapping(value = "save")
	public String save(Activity activity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activity)){
			return form(activity, model);
		}
		if(activity.getTop().equals("1")){
			activityService.removeTop(activity);
		}
		activityService.save(activity);
		addMessage(redirectAttributes, "保存主题活动'" + activity.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/activity/?repage";
	}
	
	@RequiresPermissions("edu:activity:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		activityService.delete(id);
		addMessage(redirectAttributes, "删除主题活动成功");
		return "redirect:"+Global.getAdminPath()+"/edu/activity/?repage";
	}

	@RequiresPermissions("edu:activity:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Activity activity = activityService.get(id);
		activity.setStatus(status);
		activityService.save(activity);
		if(status==1)
			addMessage(redirectAttributes, "发布主题活动成功");
		else
			addMessage(redirectAttributes, "取消发布主题活动成功");

		return "redirect:"+Global.getAdminPath()+"/edu/activity/?repage";
	}



}
