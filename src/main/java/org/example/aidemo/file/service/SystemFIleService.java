package org.example.aidemo.file.service;

import lombok.extern.slf4j.Slf4j;
import org.example.aidemo.file.entiy.SystemFile;
import org.example.aidemo.file.mapper.SystemFIleMapper;
import org.example.aidemo.file.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class SystemFIleService {
    @Autowired
    private SystemFIleMapper systemFIleMapper;
    @Autowired
    private MinioUtil minioUtil;

    public String uploadFile(MultipartFile file, SystemFile systemFile) {
        try {
            log.info("上传文件"+systemFile);
            String fileName = minioUtil.uploadFile(file);
            //url
            String url = "http://s3.yyq66.top:9000/test-bucket/" + fileName;
//           systemFile.setUrl(minioUtil.getFileUrl(fileName));
           systemFile.setUrl(url);
           systemFile.setRemark(fileName);
           systemFile.setType(file.getContentType());
           systemFile.setRemark1(file.getOriginalFilename());
           systemFIleMapper.addSystemFile(systemFile);

//            return "上传成功！文件存储路径：" + fileName + "\n访问URL：" + minioUtil.getFileUrl(fileName);
            return "上传成功！"+"\n访问URL："+url;
        } catch (Exception e) {
            return "上传失败：" + e.getMessage();
        }
    }
//    public String uploadFile(SystemFile file) {
//        try {
//            systemFIleMapper.addSystemFile(file);
//
//        }catch (Exception e)
//            {
//            return "上传失败：" + e.getMessage();}
//        return "success";
//    }

}
