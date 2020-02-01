package com.eng.recitewords.entity;

public class Words {

    private String wordId;
    private String englishWord;
    private String pa;
    private String chineseWord;
    private String englishInstance1;
    private String chineseInstance1;
    private String englishInstance2;
    private String chineseInstance2;
    private int collect;
    private String pron;

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getChineseWord() {
        return chineseWord;
    }

    public void setChineseWord(String chineseWord) {
        this.chineseWord = chineseWord;
    }

    public String getEnglishInstance1() {
        return englishInstance1;
    }

    public void setEnglishInstance1(String englishInstance1) {
        this.englishInstance1 = englishInstance1;
    }

    public String getChineseInstance1() {
        return chineseInstance1;
    }

    public void setChineseInstance1(String chineseInstance1) {
        this.chineseInstance1 = chineseInstance1;
    }

    public String getEnglishInstance2() {
        return englishInstance2;
    }

    public void setEnglishInstance2(String englishInstance2) {
        this.englishInstance2 = englishInstance2;
    }

    public String getChineseInstance2() {
        return chineseInstance2;
    }

    public void setChineseInstance2(String chineseInstance2) {
        this.chineseInstance2 = chineseInstance2;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    @Override
    public String toString() {
        return "Words{" +
                "wordId='" + wordId + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", pa='" + pa + '\'' +
                ", chineseWord='" + chineseWord + '\'' +
                ", englishInstance1='" + englishInstance1 + '\'' +
                ", chineseInstance1='" + chineseInstance1 + '\'' +
                ", englishInstance2='" + englishInstance2 + '\'' +
                ", chineseInstance2='" + chineseInstance2 + '\'' +
                ", collect=" + collect +
                ", pron='" + pron + '\'' +
                '}';
    }
}
