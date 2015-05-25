/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.edu.web.portal;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.edu.entity.Euser;
import com.thinkgem.jeesite.modules.edu.entity.Gps;
import com.thinkgem.jeesite.modules.edu.service.GpsService;
import com.thinkgem.jeesite.modules.edu.web.portal.dto.BaiduGpsDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

	@ResponseBody
	@RequestMapping(value = {"locate"})
	public String locate(String uid,String address){
        Gps gps = new Gps();
        Euser user = new Euser();
        user.setId(uid);
        gps.setUser(user);
        gps.setLocation(address);
        gpsService.save(gps);
        return address;
	}

//    @ResponseBody
//	@RequestMapping(value = {"locate"})
//	public String locate(String uid){
//		BufferedReader bufferedReader=null;
//		HttpClient httpClient = HttpClients.createDefault();
//		HttpGet request = new HttpGet("http://api.map.baidu.com/location/ip?ak=GZ5zloRNsGhe3Kh7NWAwisgn");
//		try {
//
//			HttpResponse response = httpClient.execute(request);
//			StringBuilder entityStringBuilder=new StringBuilder();
//			bufferedReader=new BufferedReader
//					(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 8*1024);
//			String line=null;
//			while ((line=bufferedReader.readLine())!=null) {
//				entityStringBuilder.append(line+"/n");
//			}
//
//			BaiduGpsDto baiduGpsDto =JsonMapper.getInstance().fromJson(entityStringBuilder.toString(), BaiduGpsDto.class);
//
//			//保存到数据库
//			if(baiduGpsDto!=null||baiduGpsDto.getContent()!=null&& StringUtils.isNotEmpty(baiduGpsDto.getContent().getAddress())){
//				Gps gps = new Gps();
//				Euser user = new Euser();
//				user.setId(uid);
//				gps.setUser(user);
//				gps.setLocation(baiduGpsDto.getContent().getAddress());
//				gpsService.save(gps);
//			}
//
//			return baiduGpsDto.getContent().getAddress();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
}
