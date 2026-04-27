package org.example.aidemo.file.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.aidemo.file.entiy.SystemFile;

@Mapper
public interface SystemFIleMapper {
    void addSystemFile(SystemFile systemFile);
}
