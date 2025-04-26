package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.exceptions.ClientException;
import br.com.api_str_innovation.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    private void existsMailOrCnpj(ClientRequestDTO data) {
        if (repository.findByEmail(data.email()).isPresent() ) {
            throw new ClientException(String.format("O email %s já está em uso.", data.email()));
        }

        if (repository.findByDocument(data.document()).isPresent() ) {
            throw new ClientException(String.format("O CNPJ %s já está em uso.", data.document()));
        }
    }

    private void validDocumentLength(String document) {
        assert document != null;
        String cnpj = document.replaceAll("\\D", "");

        if (cnpj.length() != 14) {
            throw new ClientException("Informe o CNPJ corretamente!");
        }
    }

    public List<ClientResponseDTO> getAll() {
        List<ClientResponseDTO> client = repository
                .findAll()
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
        return client;
    }

    public Integer count() {
        Integer count = repository
                .findActiveClients()
                .toArray()
                .length;
        return count;
    }

    @GetMapping(value = "/page")
    public Page<ClientResponseDTO> getPaged(Pageable pageable) {
        Page<ClientResponseDTO> client = repository
                .findActiveClients(pageable)
                .map(ClientResponseDTO::new);
        return client;
    }

    @GetMapping(value = "/search")
    public Page<ClientResponseDTO> getSearched(Pageable pageable, String name) {
        Page<ClientResponseDTO> client = repository
                .findSearchClients(pageable, name)
                .map(ClientResponseDTO::new);
        return client;
    }

    public ClientEntity getById(UUID id) {
        ClientEntity client = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        return client;
    }

    public void post(@Valid ClientRequestDTO data) {
        existsMailOrCnpj(data);
        validDocumentLength(data.document());

        ClientEntity clientData = new ClientEntity(data);
        repository.save(clientData);
    }

    public ClientResponseDTO put(UUID id, @Valid ClientRequestDTO data) {
        existsMailOrCnpj(data);
        validDocumentLength(data.document());

        ClientEntity client = this.getById(id);
        client.setName(data.name());
        client.setEmail(data.email());
        client.setDocument(data.document());
        repository.save(client);
        return new ClientResponseDTO(client);
    }

    public ClientResponseDTO patch(UUID id, @Valid ClientRequestDTO data) {
        existsMailOrCnpj(data);
        validDocumentLength(data.document());

        ClientEntity client = this.getById(id);
        client.setName(data.name());
        client.setEmail(data.email());
        client.setDocument(data.document());
        repository.save(client);
        return new ClientResponseDTO(client);
    }

    public void inactivate(UUID id) {
        ClientEntity client = getById(id);
        client.setInactivatedDt(LocalDateTime.now());
        repository.save(client);
    }

}
