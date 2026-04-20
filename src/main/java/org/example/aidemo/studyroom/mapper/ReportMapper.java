package org.example.aidemo.studyroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.aidemo.studyroom.entity.ReportCountVO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReportMapper {
    List<ReportCountVO> getCount(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);
}
