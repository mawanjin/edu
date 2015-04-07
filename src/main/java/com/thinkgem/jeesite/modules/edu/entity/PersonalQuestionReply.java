package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by abcd on 15/3/24.
 */
@Entity
@Table(name = "edu_personal_question_reply")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonalQuestionReply extends IdEntity<PersonalQuestionReply> {
    private Euser euser;
    private String content;
    private Byte status;
    private PersonalQuestion personalQuestion;


    @ManyToOne
    @JoinColumn(name = "personal_question")
    public PersonalQuestion getPersonalQuestion() {
        return personalQuestion;
    }

    public void setPersonalQuestion(PersonalQuestion personalQuestion) {
        this.personalQuestion = personalQuestion;
    }

    @ManyToOne
    @JoinColumn(name = "name")
    public Euser getEuser() {
        return euser;
    }

    public void setEuser(Euser euser) {
        this.euser = euser;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
