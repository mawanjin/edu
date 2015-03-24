package com.thinkgem.jeesite.modules.sys.web;

import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.ui.Model;
import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * Created by abcd on 15/3/21.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/fileupload")
public class FileUploadController extends BaseController{

    @RequiresPermissions("sys:fileupload:view")
    @RequestMapping({"list", ""})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/sys/fileupload";
    }

    @RequestMapping("get")
    public void doGet(HttpServletRequest request,HttpServletResponse response){

        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            File file = new File(request.getSession().getServletContext().getRealPath("/static/images/upload/")+"/"+request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;

                ServletOutputStream op = null;
                try {
                    op = response.getOutputStream();
                    response.setContentType(getMimeType(file));
                    response.setContentLength((int) file.length());
                    response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                    byte[] bbuf = new byte[1024];
                    DataInputStream in = new DataInputStream(new FileInputStream(file));

                    while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                        op.write(bbuf, 0, bytes);
                    }

                    in.close();
                    op.flush();
                    op.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(request.getSession().getServletContext().getRealPath("/static/images/upload/")+"/"+ request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            }
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {

            File file = new File(request.getSession().getServletContext().getRealPath("/static/images/upload/")+"/"+request.getParameter("getthumb"));
            if (file.exists()) {

                String mimetype = getMimeType(file);
                if (mimetype.endsWith("png") || mimetype.endsWith("jpeg")|| mimetype.endsWith("jpg") || mimetype.endsWith("gif")) {
                    BufferedImage im = null;
                    try {
                        im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75);
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else if (mimetype.endsWith("jpg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } // TODO: check and report success
        } else {
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.write("call POST with multipart form data");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @RequestMapping("upload")
    public void doPost(HttpServletRequest request,HttpServletResponse response){
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        PrintWriter writer = null;
        JSONArray json = new JSONArray();
        try {
            writer = response.getWriter();
            response.setContentType("application/json");

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获得上传的文件（根据前台的name名称得到上传的文件）
            MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
            List<MultipartFile> files = multiValueMap.get("files[]");

            for(MultipartFile f : files){
                    FileUtils.createDirectory(request.getSession().getServletContext().getRealPath("/static/images/upload/"));
                    File file = new File(request.getSession().getServletContext().getRealPath("/static/images/upload/"), f.getOriginalFilename());
                    f.transferTo(file);
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", f.getOriginalFilename());
                    jsono.put("size", f.getSize());
                    jsono.put("url", "fileupload/get?getfile=" + f.getOriginalFilename());
                    jsono.put("thumbnail_url", "fileupload/get?getthumb=" + f.getOriginalFilename());
                    jsono.put("delete_url", "fileupload/get?delfile=" +f.getOriginalFilename());
                    jsono.put("delete_type", "GET");
                    json.put(jsono);
                    System.out.println(json.toString());
                //                    item.write(file);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.write(json.toString());
            writer.close();
        }
    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("jpg")){
                mimetype = "image/jpg";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("jpeg")){
                mimetype = "image/jpeg";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("gif")){
                mimetype = "image/gif";
            }else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
}
