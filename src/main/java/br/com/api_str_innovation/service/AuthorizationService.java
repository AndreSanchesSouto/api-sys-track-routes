package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.authentication.AuthenticationRequestDTO;
import br.com.api_str_innovation.dto.authentication.AuthenticationResponseDTO;
import br.com.api_str_innovation.entities.employee.UserEntity;
import br.com.api_str_innovation.repository.UserRepository;
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
    UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username);
    }

    public ResponseEntity<AuthenticationResponseDTO> authEmployee(AuthenticationRequestDTO credentials) {
        String hashPassword = Encrypter.encrypt(credentials.password());
        UserEntity employee = authIdentity(credentials.login(), hashPassword);

        if(employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponseDTO("Não encontado",null));
        }

        String token = tokenService.generateToken(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponseDTO(token, employee));

    }

    private UserEntity authIdentity(String login, String hashPassword) {
        return repository.authIdentity(login, hashPassword);
    }

    private UserDetails findByLogin(String username) {
        return repository.findByLogin(username);
    }

}