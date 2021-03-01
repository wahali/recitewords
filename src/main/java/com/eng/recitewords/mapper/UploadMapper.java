package com.eng.recitewords.mapper;

import com.eng.recitewords.entity.UploadFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadMapper {

    public List<UploadFile> selectAllUploadFiles();
    public List<UploadFile> selectUncheckedFiles();
    public List<UploadFile> selectCheckedFiles();
    public void updateChecked(String fileName);
    public void deleteFileByName(String fileName);
    public void insertFile(UploadFile uploadFile);
}
