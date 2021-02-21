package com.eng.recitewords.mapper;

import com.eng.recitewords.entity.Admin;
import com.eng.recitewords.entity.Question;
import com.eng.recitewords.entity.User;
import com.eng.recitewords.entity.Words;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {

    public Admin selectByEmail(String adminEmail);
    public List<User> selectAllUser();
    public List<Admin> selectAllAdmin();
    public Admin selectAdminById(String adminId);
    public void updateInfo(Admin admin);
    public void addInfo(Admin admin);
    public void deleteInfo(String adminId);
    public void deleteUser(String userId);
    public List<Question> checkQuestion();
    public Question selectByQuestionId(String questionId);
    public void passQuestion(String questionId);
    public void failQuestion(String questionId);

}
