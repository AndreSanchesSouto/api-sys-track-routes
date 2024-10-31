package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<ClientResponseDTO> getAll() {
        List<ClientResponseDTO> client = repository
                .findAll()
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
        return client;
    }

    public Page<ClientResponseDTO> getPaged(Pageable pageable) {
        Page<ClientResponseDTO> client = repository
                .findAll(pageable)
                .map(ClientResponseDTO::new);
        return client;
    }

    public ClientEntity getById(UUID id) {
        ClientEntity client = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found"));
        return client;
    }

    public void post(@Valid ClientRequestDTO data) {
        System.out.println(data);
        ClientEntity clientData = new ClientEntity(data);
        repository.save(clientData);
    }

    public ClientResponseDTO put(UUID id, ClientRequestDTO data) {
        ClientEntity client = this.getById(id);
        client.setName(data.name());
        client.setContactType(data.contactType());
        client.setContact(data.contact());
        repository.save(client);
        return new ClientResponseDTO(client);
    }

    public void inactivate(UUID id) {
        ClientEntity client = getById(id);
        client.setStatus("INACTIVE");
        repository.save(client);
    }
}
