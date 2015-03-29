/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.edu.entity.Activity;
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
import com.thinkgem.jeesite.modules.edu.entity.Indeximg;
import com.thinkgem.jeesite.modules.edu.service.IndeximgService;

/**
 * 首页顶部图片Controller
 * @author lala
 * @version 2015-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/edu/indeximg")
public class IndeximgController extends BaseController {

	@Autowired
	private IndeximgService indeximgService;
	
	@ModelAttribute
	public Indeximg get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return indeximgService.get(id);
		}else{
			return new Indeximg();
		}
	}
	
	@RequiresPermissions("edu:indeximg:view")
	@RequestMapping(value = {"list", ""})
	public String list(Indeximg indeximg, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			indeximg.setCreateBy(user);
		}
        Page<Indeximg> page = indeximgService.find(new Page<Indeximg>(request, response), indeximg); 
        model.addAttribute("page", page);
		return "modules/" + "edu/indeximgList";
	}

	@RequiresPermissions("edu:indeximg:view")
	@RequestMapping(value = "form")
	public String form(Indeximg indeximg, Model model) {
		model.addAttribute("indeximg", indeximg);
		return "modules/" + "edu/indeximgForm";
	}

	@RequiresPermissions("edu:indeximg:edit")
	@RequestMapping(value = "save")
	public String save(Indeximg indeximg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, indeximg)){
			return form(indeximg, model);
		}
		indeximgService.save(indeximg);
		addMessage(redirectAttributes, "保存首页顶部图片'" + indeximg.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/edu/indeximg/?repage";
	}
	
	@RequiresPermissions("edu:indeximg:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		indeximgService.delete(id);
		addMessage(redirectAttributes, "删除首页顶部图片成功");
		return "redirect:"+Global.getAdminPath()+"/edu/indeximg/?repage";
	}

	@RequiresPermissions("edu:indeximg:edit")
	@RequestMapping(value = "publish")
	public String publish(String id,Byte status, RedirectAttributes redirectAttributes) {
		Indeximg indeximg = indeximgService.get(id);
		indeximg.setStatus(status);
		indeximgService.save(indeximg);
		if(status==1)
			addMessage(redirectAttributes, "发布首页顶部图片成功");
		else
			addMessage(redirectAttributes, "取消发布首页顶部图片成功");

		return "redirect:"+Global.getAdminPath()+"/edu/indeximg/?repage";
	}


}
