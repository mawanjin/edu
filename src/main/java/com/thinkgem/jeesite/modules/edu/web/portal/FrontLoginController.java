/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Euser;
import com.thinkgem.jeesite.modules.edu.service.EuserService;
import com.thinkgem.jeesite.modules.edu.service.UserRelationService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.LoginDto;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录&注册Controller
 *
 * @author lala
 * @version 2015-03-24
 */
@Controller
@RequestMapping(value = "${frontPath}/edu/account")
public class FrontLoginController extends BaseController {

    @Autowired
    private EuserService euserService;
    @Autowired
    private UserRelationService userRelationService;

    @ResponseBody
    @RequestMapping(value = {"login"})
    public LoginDto get(@RequestParam(required = true) String loginName, @RequestParam(required = true) String password) {
        LoginDto loginDto = new LoginDto();
        Euser euser = euserService.login(loginName, password);

        if (euser != null) {
            loginDto.setEuser(euser);
            //监护人信息
            Euser guardian = userRelationService.findGuardian(euser.getId());
            loginDto.setGuardian(guardian);

            if (euser.getSchool() != null) {
                loginDto.setSchoolId(euser.getSchool().getId());
                loginDto.setSchoolName(euser.getSchool().getName());
            }
            loginDto.setRs(true);
        }

        return loginDto;
    }

    @ResponseBody
    @RequestMapping(value = "register")
    public RegisterDto register(Euser euser) {
        RegisterDto registerDto = new RegisterDto();
        try {
            euser.setType(new Byte("0"));
            if(euserService.findByLoginName(euser.getLoginName())!=null){
                registerDto.setRs(false);
                registerDto.setMsg("该帐号已注册");
                return registerDto;
            }
            euserService.save(euser);
            registerDto.setRs(true);
        } catch (Exception e) {
            e.printStackTrace();
            registerDto.setRs(false);
            registerDto.setMsg("注册失败请重试");
        }

        return registerDto;
    }

    @ResponseBody
    @RequestMapping(value = "getGuardian")
    public Euser getGuardian(String uid){
        return userRelationService.findGuardianByUid(uid);
    }

}
