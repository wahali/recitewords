package com.eng.recitewords.service;

import com.eng.recitewords.entity.Words;
import com.eng.recitewords.mapper.WordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WordsService {

    @Autowired
    private WordsMapper wordsMapper;
    public List<Words> selectAllWords(){
        List<Words> wordsList = wordsMapper.selectAllWords();
        return wordsList;
    }

    public Words selectWordById(String wordId){
        Words words = wordsMapper.selectWordById(wordId);
        return words;
    }

    public void insertWord(String wordId,String englishWord,String pa,String chineseWord){
        Words words = new Words();
        words.setWordId(wordId);
        words.setEnglishWord(englishWord);
        words.setPa(pa);
        words.setChineseWord(chineseWord);
        wordsMapper.insertWord(words);
    }

    public void deleteWord(String wordId){
        wordsMapper.deleteWord(wordId);
    }

    public void updateWord(String wordId,String englishWord,String pa,String chineseWord,String englishInstance1,String chineseInstance1,String englishInstance2,String chineseInstance2,int collect,String pron){
        Words word = new Words();
        word.setWordId(wordId);
        word.setEnglishWord(englishWord);
        word.setPa(pa);
        word.setChineseWord(chineseWord);
        word.setEnglishInstance1(englishInstance1);
        word.setChineseInstance1(chineseInstance1);
        word.setEnglishInstance2(englishInstance2);
        word.setChineseInstance2(chineseInstance2);
        word.setCollect(collect);
        word.setPron(pron);
        wordsMapper.updateWord(word);
    }

    public List<String> selectWordsByUId(String userId) {
        List<String> wordIdList = wordsMapper.selectWordsByUId(userId);
        return wordIdList;
    }

    public List<Words> selectByWId(List<String> wordIdList) {
        List<Words> wordsErrorList = new ArrayList<>();
        for(int i=0; i < wordIdList.size(); i++) {
            Words word = wordsMapper.selectByWId(wordIdList.get(i));
            wordsErrorList.add(word);
        }
        return wordsErrorList;
    }

    public void insertValue(String wordId, String userId) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("wordId",wordId);
        wordsMapper.insertValue(map);
    }

    public Words selectOneByWId(String wordId) {
        Words word = wordsMapper.selectOneByWId(wordId);
        return word;
    }

    public void deleteByWId(String wordId) {
        wordsMapper.deleteByWId(wordId);
    }

    public int selectErrorWordByWId(String wordId) {
        int count = wordsMapper.selectErrorWordByWId(wordId);
        return count;
    }

}
