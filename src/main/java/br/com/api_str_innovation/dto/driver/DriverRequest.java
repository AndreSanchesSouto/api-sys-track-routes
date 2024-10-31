package br.com.api_str_innovation.dto.driver;

import br.com.api_str_innovation.dto.EmployeeRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequest extends EmployeeRequest {
    private String status;
}
