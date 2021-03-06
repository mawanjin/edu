/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.AbroadEnroll;
import com.thinkgem.jeesite.modules.edu.entity.Abroadhome;
import com.thinkgem.jeesite.modules.edu.service.AbroadEnrollService;
import com.thinkgem.jeesite.modules.edu.service.AbroadhomeService;
import com.thinkgem.jeesite.modules.edu.service.EuserService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.AbroadDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 海外之家Controller
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/abroad")
public class FrontAbroadController extends BaseController {

	@Autowired
	private AbroadhomeService abroadhomeService;

	@Autowired
	private AbroadEnrollService abroadEnrollService;

	@Autowired
	private EuserService euserService;

	@ResponseBody
	@RequestMapping(value = {"get"})
	public AbroadDto get(@RequestParam(required=true) String abroadhome,@RequestParam(required=false) String uid) {
		AbroadDto rs = new AbroadDto();
		if (StringUtils.isNotBlank(abroadhome)){
			try {
				PropertyUtils.copyProperties(rs, abroadhomeService.get(abroadhome));
				rs.setEnrolled(abroadEnrollService.isEnrolled(uid, abroadhome));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public List<AbroadDto> list(@RequestParam(required=false) String uid,HttpServletRequest request, HttpServletResponse response, Model model) {

		//找到所有已报名的记录


		List<Abroadhome> abroadhomes = abroadhomeService.findAll();
		List<AbroadDto> rs = new ArrayList<AbroadDto>(0);

		if(abroadhomes!=null&&abroadhomes.size()>0){

			for(Abroadhome abroadhome :abroadhomes){

				AbroadDto abroadDto = new AbroadDto();
				try {
					PropertyUtils.copyProperties(abroadDto,abroadhome);
					if(StringUtils.isNotEmpty(uid))
						abroadDto.setEnrolled(abroadEnrollService.isEnrolled(uid, abroadhome.getId()));
					if(StringUtils.isNotEmpty(abroadDto.getContent())){
                        String domain = "http://"+request.getServerName()+":"+request.getServerPort();
						abroadDto.setContent(abroadDto.getContent().replaceAll("\\/userfiles",domain+"\\/userfiles"));
					}

					rs.add(abroadDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotEmpty(abroadhome.getImg())){
					abroadDto.setImg("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/sys/fileupload/get?getthumb="+abroadhome.getImg());
				}
			}
		}
		return rs;
	}

	@RequestMapping(value = {"enroll/save"})
	@ResponseBody
	public boolean enroll(String userId,String abroadId,HttpServletRequest request, HttpServletResponse response, Model model) {
		//先查找是否已报名
		if(abroadEnrollService.isEnrolled(userId,abroadId)){
			return true;
		}else{
			AbroadEnroll abroadEnroll = new AbroadEnroll();
			abroadEnroll.setEuser(euserService.get(userId));
			abroadEnroll.setAbroadhome(abroadhomeService.get(abroadId));
			abroadEnrollService.save(abroadEnroll);
			return true;
		}
	}
}
