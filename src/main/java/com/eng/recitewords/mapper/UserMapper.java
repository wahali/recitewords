package com.eng.recitewords.mapper;

import com.eng.recitewords.entity.Answer;
import com.eng.recitewords.entity.Question;
import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserMapper {

    public User selectById(String userId);

    public User selectByEmail(String userEmail);

    public User selectByUserName(String userName);

    public void register(User user);

    public void updatePassword(User user);

    public List<Words> selectPartWord(HashMap<String,Object> map);

//    public User selectUserByName(String userName);

    public void updateUserInfo(User user);

    public void updateUserLast(String userId, int index);

    public void updateBasics(String userId,String basics);

    public void newQuestion(Question question);

    public List<Question> allQuestions();

    public Question selectByQuestionId(String questionId);

    public List<Question> selectQuestionByUserId(String userId);

    public void deleteByQuestionId(String questionId);

    public void answerQuestion(Answer answer);

    public List<Answer> selectAnswerByQID(String questionId);

    public void addHot(String questionId,int hot);

}

