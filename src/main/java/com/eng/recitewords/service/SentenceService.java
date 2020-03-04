package com.eng.recitewords.service;

import com.eng.recitewords.entity.Sentence;
import com.eng.recitewords.entity.Words;
import com.eng.recitewords.mapper.SentenceMapper;
import com.eng.recitewords.mapper.WordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentenceService {
    @Autowired
    private WordsMapper wordsMapper;
    private SentenceMapper sentenceMapper;

    public List<Sentence> getSentenceByWord(String word){
        return sentenceMapper.getSentenceByWord("%"+word+"%");
    }

    public List<Sentence> getSentenceByWordId(String wordId){
        Words word = wordsMapper.selectWordById(wordId);
        return sentenceMapper.getSentenceByWordId("%"+word.getEnglishWord()+"%");
    }

}
