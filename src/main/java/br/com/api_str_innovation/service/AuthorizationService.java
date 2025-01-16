package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.authentication.AuthenticationRequestDTO;
import br.com.api_str_innovation.dto.authentication.AuthenticationResponseDTO;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.repository.UserRepository;
import br.com.api_str_innovation.infra.security.Encrypter;
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
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username);
    }

    public ResponseEntity<AuthenticationResponseDTO> authEmployee(AuthenticationRequestDTO credentials) {
        String hashPassword = Encrypter.encrypt(credentials.password());
        UserEntity employee = repository.authIdentity(credentials.login(), hashPassword);

        if(employee == null) {
            verifyUserRegistered(credentials.login());
        }

        String token = tokenService.generateToken(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponseDTO(token, employee));

    }

    private void verifyUserRegistered(String login) {
        String error = repository.findByLogin(login) != null ?
                "Senha incorreta" :
                "Usuário não cadastrado";
        throw new UserException(error);
    }

    private UserDetails findByLogin(String username) {
        return repository.findByLogin(username);
    }

}