package com.devsuperior.dscliente.services;

import com.devsuperior.dscliente.dto.ClientDTO;
import com.devsuperior.dscliente.entities.Client;
import com.devsuperior.dscliente.repositories.ClientRepository;
import com.devsuperior.dscliente.services.expetions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> entities = repository.findAll(pageRequest);
        return entities.map(entity -> new ClientDTO(entity));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> optional = repository.findById(id);
        Client client = optional.orElseThrow(() -> new ResourceNotFoundException("Cliente com o id " + id + " não encontrado"));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDto) {
        Client client = new Client();
        copyDtoToEntity(clientDto, client);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    private void copyDtoToEntity(ClientDTO clientDto, Client client) {
        client.setName(clientDto.getName());
        client.setCpf(clientDto.getCpf());
        client.setIncome(clientDto.getIncome());
        client.setBirthDate(clientDto.getBirthDate());
        client.setChildren(clientDto.getChildren());
    }
}
