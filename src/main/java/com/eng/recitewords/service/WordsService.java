package com.eng.recitewords.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eng.recitewords.entity.Words;
import com.eng.recitewords.mapper.WordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Words selectByEnglishWord(String englishWord){
        List<Words> wordsList = wordsMapper.selectByEnglishWord(englishWord);
        if(wordsList.size()>0)return wordsList.get(0);
        else return null;
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

    public List<Words> selectByFatherId(String wordId){
        return wordsMapper.getChildWordListByWordId(wordId);
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

    /**
     * 获得单词的派生树,派生的主函数
     * @param wordId
     * @return
     */
    public JSONArray getDeriveByWordId(String wordId){
        //加载子树wordsMap
        Map<String,Words> wordsMap = getWordsMapById(wordId);
        return getNodeJson(wordId,wordsMap);
    }


    //将对应的所有子树结点的单词全部加载到wordslist中
    public  Map<String, Words> getWordsMapById(String wordId){
        Map<String, Words> wordsMap = new HashMap<>();
        Queue<Words> wordsQueue = new LinkedList<>();
        List<Words> wordsList = selectByFatherId(wordId);
        for(int i = 0 ;i < wordsList.size();++i)wordsQueue.offer(wordsList.get(i));
        while(wordsQueue.size()!=0){
            Words first = wordsQueue.peek();
            assert first != null;
            wordsMap.put(first.getWordId(),first);
            wordsQueue.poll();
            wordsList = selectByFatherId(first.getWordId());
            if(wordsList!=null)for(int i = 0 ;i < wordsList.size();++i)wordsQueue.offer(wordsList.get(i));
        }
        System.out.println("wordsMap.size:"+wordsMap.size());
        return wordsMap;
}

    /**
     * 递归处理   数据库树结构数据->树形json
     * @param wordId
     * @param wordsMap
     * @return
     */
    public static JSONArray getNodeJson(String wordId, Map<String,Words> wordsMap){

        //当前层级当前node对象
        Words cur = wordsMap.get(wordId);
        //当前层级当前点下的所有子节点（实战中不要慢慢去查,一次加载到集合然后慢慢处理）
        List<Words> childList = getChildNodes(wordId,wordsMap);
        JSONArray childTree = new JSONArray();
        for (Words word : childList) {
            JSONObject o = new JSONObject();

//            o.put("englishWord", word.getEnglishWord());

            o.put("name", word.getEnglishWord()+" /"+word.getPa()+"/ "+word.getChineseWord());

//            o.put("pa", word.getPa());
//            o.put("name", word.getPa());
//            o.put("chineseWord", word.getChineseWord());
//            o.put("name", word.getChineseWord());
            JSONArray childs = getNodeJson(word.getWordId(),wordsMap);  //递归调用该方法
            if(!childs.isEmpty()) {
//                o.put("open",true);
                o.put("children",childs);
            }
            childTree.fluentAdd(o);
//            id++;
        }
        return childTree;
    }

    /**
     * 在指定的wordsMap中获取当前节点的所有子节点
     * @param wordId
     * @param wordsMap
     * @return
     */
    public static List<Words> getChildNodes(String wordId, Map<String,Words> wordsMap){
        List<Words> list = new ArrayList<>();
        for (String key : wordsMap.keySet() ) {
            if(wordsMap.get(key).getFatherId().equals(wordId)){
                list.add(wordsMap.get(key));
            }
        }
        return list;
    }



}
