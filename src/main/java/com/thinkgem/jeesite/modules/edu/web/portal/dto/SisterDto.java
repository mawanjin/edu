package com.thinkgem.jeesite.modules.edu.web.portal.dto;

/**
 * Created by wanjinma on 15/4/20.
 */
public class SisterDto {
    private String id;
    private String name;
    private String img;
    private Byte gendar;
    private String summary;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getGendar() {
        return gendar;
    }

    public void setGendar(Byte gendar) {
        this.gendar = gendar;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
