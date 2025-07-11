package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.authentication.AuthenticationRequestDTO;
import br.com.api_str_innovation.dto.authentication.AuthenticationResponseDTO;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.repository.UserRepository;
import br.com.api_str_innovation.infrastructure.security.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<UserEntity> verifyLoginAndPassword(String login, String password) {
        String hashPassword = Encrypter.encrypt(password);
        return repository.authIdentity(login, hashPassword);
    }

    public ResponseEntity<AuthenticationResponseDTO> authEmployee(AuthenticationRequestDTO credentials) {
        Optional<UserEntity> employee = this.verifyLoginAndPassword(credentials.login(), credentials.password());

        if(employee.isEmpty()) {
            throw verifyUserRegistered(credentials.login());
        }

        String token = tokenService.generateToken(employee.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponseDTO(token, employee.get()));

    }

    private UserException verifyUserRegistered(String login) {
        return repository.findByLogin(login).map(
                (user) -> user.getInactivatedDt() == null ?
                        new UserException("Senha incorreta") :
                        new UserException("Usuário teve seu acesso revogado")
        ).orElse( new UserException("Usuário não cadastrado"));
    }

    private UserDetails findByLogin(String username) {
        return repository.findByLogin(username).orElseThrow(
                () -> new UserException("NotFund")
        );
    }

}