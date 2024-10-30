package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.general_manager.GeneralManagerRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "general_manager")
@Entity
@Getter
@NoArgsConstructor
public class GeneralManagerEntity extends AbstractEmployeeEntity {

    public GeneralManagerEntity(GeneralManagerRequestDTO data) {
    }
}
