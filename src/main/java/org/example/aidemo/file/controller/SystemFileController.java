package org.example.aidemo.file.controller;

import org.example.aidemo.file.entiy.SystemFile;
import org.example.aidemo.file.service.SystemFIleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/systemFile")
@CrossOrigin
public class SystemFileController {
    @Autowired
    private SystemFIleService systemFIleService;
    @PostMapping(value = "/uploadWithInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute SystemFile systemFile
    ) {
        return systemFIleService.uploadFile(file, systemFile);
    }
}
