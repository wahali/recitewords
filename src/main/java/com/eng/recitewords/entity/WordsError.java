package com.eng.recitewords.entity;

public class WordsError {

    private String userId;
    private String wordId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    @Override
    public String toString() {
        return "WordsError{" +
                "userId='" + userId + '\'' +
                ", wordId='" + wordId + '\'' +
                '}';
    }
}
