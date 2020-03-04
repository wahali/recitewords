package com.eng.recitewords.entity;


public class Sentence {

  private String sentenceId;
  private String englishInstance;
  private String chineseInstance;



  public String getSentenceId() {
    return sentenceId;
  }

  public void setSentenceId(String sentenceId) {
    this.sentenceId = sentenceId;
  }


  public String getEnglishInstance() {
    return englishInstance;
  }

  public void setEnglishInstance(String englishInstance) {
    this.englishInstance = englishInstance;
  }


  public String getChineseInstance() {
    return chineseInstance;
  }

  public void setChineseInstance(String chineseInstance) {
    this.chineseInstance = chineseInstance;
  }

  @Override
  public String toString() {
    return "Sentence{" +
            "sentenceId='" + sentenceId + '\'' +
            ", englishInstance='" + englishInstance + '\'' +
            ", chineseInstance='" + chineseInstance + '\'' +
            '}';
  }
}
