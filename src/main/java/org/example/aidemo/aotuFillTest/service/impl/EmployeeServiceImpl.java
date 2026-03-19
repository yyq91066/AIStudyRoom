package org.example.aidemo.aotuFillTest.service.impl;

import org.example.aidemo.aotuFillTest.mapper.EmployeeMapper;
import org.example.aidemo.aotuFillTest.service.EmployeeService;
import org.example.aidemo.sky.entiy.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Long save(Employee employee) {
        employeeMapper.save(employee);

        return employee.getId();

    }
}
