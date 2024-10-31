package br.com.api_str_innovation.dto.driver;

import br.com.api_str_innovation.dto.EmployeeRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequestDTO extends EmployeeRequestDTO {

    private String status;

}
