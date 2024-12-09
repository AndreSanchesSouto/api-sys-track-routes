package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.authentication.AuthenticationRequestDTO;
import br.com.api_str_innovation.dto.authentication.AuthenticationResponseDTO;
import br.com.api_str_innovation.dto.employee.ResponseDTO;
import br.com.api_str_innovation.entities.employee.AbstractEmployeeEntity;
import br.com.api_str_innovation.repository.DriverRepository;
import br.com.api_str_innovation.repository.GeneralManagerRepository;
import br.com.api_str_innovation.repository.ShippingManagerRepository;
import br.com.api_str_innovation.security.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    GeneralManagerRepository generalManagerRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    ShippingManagerRepository shippingManagerRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username);
    }

    public ResponseEntity<AuthenticationResponseDTO> authEmployee(AuthenticationRequestDTO credentials) {
        String hashPassword = Encrypter.encrypt(credentials.password());
        AbstractEmployeeEntity employee = uthIdentity(credentials.login(), hashPassword);

        if(employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponseDTO("Não encontado",null));
        }

        String token = tokenService.generateToken(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponseDTO(token, employee));

    }

    private AbstractEmployeeEntity uthIdentity(String login, String hashPassword) {
        AbstractEmployeeEntity employee = generalManagerRepository.authIdentity(login, hashPassword);

        if(employee == null) {
            employee = driverRepository.authIdentity(login, hashPassword);
        }

        if(employee == null) {
            employee = shippingManagerRepository.authIdentity(login, hashPassword);
        }

        return employee;
    }

    private UserDetails findByLogin(String username) {
        UserDetails employee =  generalManagerRepository.findByLogin(username);

        if(employee == null) {
            employee = driverRepository.findByLogin(username);
        }

        if(employee == null) {
            employee = shippingManagerRepository.findByLogin(username);
        }

        return employee;
    }

}