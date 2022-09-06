package com.crud_clientes.services;

import com.crud_clientes.dto.ClienteDTO;
import com.crud_clientes.entities.Cliente;
import com.crud_clientes.repositories.ClienteRepository;
import com.crud_clientes.services.exceptions.DataBaseException;
import com.crud_clientes.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorId(Long id) {
        Optional<Cliente> obj = clienteRepository.findById(id);
        Cliente entity = obj.orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado ! id: " + id));
        return new ClienteDTO(entity);
    }

    @Transactional
    public ClienteDTO insertCliente(ClienteDTO clienteDTO) {
        Cliente entity = new Cliente();
        convertDtoToEntity(entity, clienteDTO);
        entity = clienteRepository.save(entity);
        return new ClienteDTO(entity);
    }

    private void convertDtoToEntity(Cliente entity, ClienteDTO clienteDTO) {
        entity.setBirthDate(clienteDTO.getBirthDate());
        entity.setChildren(clienteDTO.getChildren());
        entity.setCpf(clienteDTO.getCpf());
        entity.setName(clienteDTO.getName());
        entity.setIncome(clienteDTO.getIncome());
    }

    public void deleteCliente(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Cliente não encontrado ! id: " + id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Existem outras tabelas que depender dessa !");
        }
    }
}
