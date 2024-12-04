package br.com.api_str_innovation.dto.employee.driver;

import br.com.api_str_innovation.dto.employee.EmployeeRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequestDTO extends EmployeeRequestDTO {

    private String status;

}
