package com.crud_clientes.services;

import com.crud_clientes.dto.ClienteDTO;
import com.crud_clientes.entities.Cliente;
import com.crud_clientes.repositories.ClienteRepository;
import com.crud_clientes.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
}
