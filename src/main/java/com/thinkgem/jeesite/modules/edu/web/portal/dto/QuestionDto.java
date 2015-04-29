package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import com.thinkgem.jeesite.modules.edu.entity.Question;

import java.util.List;

/**
 * Created by wanjinma on 15/4/29.
 */
public class QuestionDto {
    private boolean rs;
    private List<Question> questions;

    public boolean isRs() {
        return rs;
    }

    public void setRs(boolean rs) {
        this.rs = rs;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
