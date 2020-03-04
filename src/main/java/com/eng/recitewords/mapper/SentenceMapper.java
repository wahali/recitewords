package com.eng.recitewords.mapper;

import com.eng.recitewords.entity.Sentence;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceMapper {
    public List<Sentence> getSentenceByWord(String word);
    public List<Sentence> getSentenceByWordId(String wordId);
}
