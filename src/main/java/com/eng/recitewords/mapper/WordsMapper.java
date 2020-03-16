package com.eng.recitewords.mapper;

import com.eng.recitewords.entity.Words;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface WordsMapper {

    public List<Words> selectAllWords();
    public void insertWord(Words word);
    public void deleteWord(String wordId);
    public void updateWord(Words word);
    public Words selectWordById(String wordId);
    public List<String> selectWordsByUId(String userId);

    public Words selectByWId(String wordId);

    public void insertValue(HashMap<String, Object> map);

    public Words selectOneByWId(String wordId);


    public void deleteByWId(String wordId);

    public int selectErrorWordByWId(String wordId);

    public List<Words> getChildWordListByWordId(String wordId);

    public List<Words> selectByEnglishWord(String englishWord);
}
