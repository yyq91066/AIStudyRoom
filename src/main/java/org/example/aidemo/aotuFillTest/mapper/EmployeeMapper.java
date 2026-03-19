package org.example.aidemo.aotuFillTest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.aidemo.aotuFillTest.AutoFill;
import org.example.aidemo.aotuFillTest.OperationType;
import org.example.aidemo.sky.entiy.Employee;

@Mapper
public interface EmployeeMapper {
    @AutoFill(value = OperationType.INSERT)
    void save(Employee employee);
}
