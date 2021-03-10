package com.eng.recitewords.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Gam
 * @version 1.0
 * @date 2021/1/27 10:58
 */
public class Question {
    private String questionId;
    private String userId;
    private String releaseTime;
    private String title;
    private String content;
    private String qType;
    private String writerName;
    private String qCheck;
    private int hot;
    private String suggestion;

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return qType;
    }

    public void setType(String type) {
        this.qType = type;
    }

    public String getqCheck() {
        return qCheck;
    }

    public void setqCheck(String qCheck) {
        this.qCheck = qCheck;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
