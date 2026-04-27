package org.example.aidemo.file.service;

import org.example.aidemo.file.entiy.SystemFile;
import org.example.aidemo.file.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MInioService {
    @Autowired
    private MinioUtil minioUtil;
    public String uploadWithInfo(MultipartFile file, SystemFile systemFile) {

        try {

            String fileName = minioUtil.uploadFile(file);


            return "上传成功！文件存储路径：" + fileName + "\n访问URL：" + minioUtil.getFileUrl(fileName);
        } catch (Exception e) {

            return "上传失败：" + e.getMessage();
        }
    }
}
