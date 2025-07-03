package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.exceptions.ClientException;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.repository.ClientRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    private void validAndExistsMailOrCnpj(ClientRequestDTO data, @Nullable ClientEntity existClient) {
        String cnpj = data.document().replaceAll("\\D", "");
        if (cnpj.length() != 14) {
            throw new ClientException("Informe o CNPJ corretamente!");
        }

        Optional<ClientEntity> clientEmail = repository.findByEmail(data.email());
        if (clientEmail.isPresent() && (existClient == null || !clientEmail.get().getId().equals(existClient.getId()))) {
            throw new ClientException(String.format("O email %s já está em uso.", data.email()));
        }

        Optional<ClientEntity> clientCNPJ = repository.findByDocument(data.document());
        if (clientCNPJ.isPresent() && (existClient == null || !clientCNPJ.get().getId().equals(existClient.getId()))) {
            throw new ClientException(String.format("O CNPJ %s já está em uso.",  data.document()));
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

    public List<ClientResponseDTO> findAvailable(UUID generalManagerId) {
        return repository
                .findAvailable(generalManagerId)
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
    }


    public Integer count(UUID generalManagerID) {
        Integer count = repository
                .findActiveClients(generalManagerID)
                .toArray()
                .length;
        return count;
    }

    @GetMapping(value = "/page")
    public Page<ClientResponseDTO> getPaged(Pageable pageable, UUID generalManagerId) {
        Page<ClientResponseDTO> client = repository
                .findActiveClients(pageable, generalManagerId)
                .map(ClientResponseDTO::new);
        return client;
    }

    public Page<ClientResponseDTO> getSearched(Pageable pageable, String attribute, String search, UUID generalManagerId) {
        return switch (attribute) {
            case "name" -> repository
                    .findSearchClientsByName(pageable, search, generalManagerId)
                    .map(ClientResponseDTO::new);
            case "email" -> repository
                    .findSearchClientsByEmail(pageable, search, generalManagerId)
                    .map(ClientResponseDTO::new);
            case "cellphone" -> repository
                    .findSearchClientsByCellphone(pageable, search, generalManagerId)
                    .map(ClientResponseDTO::new);
            case "document" -> repository
                    .findSearchClientsByDocument(pageable, search, generalManagerId)
                    .map(ClientResponseDTO::new);
            default -> throw new ClientException("Parâmetro não aceito para a pesquisa");
        };
    }

    public ClientEntity getById(UUID id) {
        ClientEntity client = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        return client;
    }

    public void post(@Valid ClientRequestDTO data, UUID generalManager) {
        validAndExistsMailOrCnpj(data, null);

        ClientEntity clientData = new ClientEntity(data, generalManager);
        repository.save(clientData);
    }

    @Transactional
    public ClientResponseDTO patch(UUID id, @Valid ClientRequestDTO data) {
        ClientEntity client = this.getById(id);
        validAndExistsMailOrCnpj(data, client);

        client.setName(data.name());
        client.setEmail(data.email());
        client.setDocument(data.document());
        client.setCellphone(data.cellphone());
        repository.save(client);
        return new ClientResponseDTO(client);
    }

    @Transactional
    public void inactivate(UUID id) {
        ClientEntity client = getById(id);
        client.setInactivatedDt(LocalDateTime.now());
        repository.save(client);
    }

}
