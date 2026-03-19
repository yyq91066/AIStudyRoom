package org.example.aidemo.aotuFillTest.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.aidemo.aotuFillTest.EmployeeDTO;
import org.example.aidemo.aotuFillTest.service.EmployeeService;
import org.example.aidemo.common.model.Result;
import org.example.aidemo.sky.entiy.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/add")
    @Operation(summary = "添加employee")
    public Result addEmployee(@RequestBody EmployeeDTO employeeDTO){
        Employee employee = new Employee().builder()
                .name(employeeDTO.getName())
                .sex(employeeDTO.getSex())
                .idNumber(employeeDTO.getIdNumber())
                .userName(employeeDTO.getUserName())
                .build();
        Long newEId=employeeService.save(employee);
        return Result.success(newEId+"register successes");
    }

}
