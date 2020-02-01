package com.eng.recitewords.mapper;

import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserMapper {

    public User selectByEmail(String userEmail);

    public void register(User user);

    public void updatePassword(User user);

    public List<Words> selectPartWord(HashMap<String,Object> map);

//    public User selectUserById(String userId);

    public void updateUserInfo(User user);

}

