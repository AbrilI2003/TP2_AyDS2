package AyDS2.TP2.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import AyDS2.TP2.dto.ClienteDTO;
import AyDS2.TP2.entity.Cliente;
import AyDS2.TP2.exception.EmailYaRegistradoException;
import AyDS2.TP2.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente altaSimple(ClienteDTO clienteDTO) {

        Cliente cliente = new Cliente(
                clienteDTO.getNombre(),
                clienteDTO.getApellido(),
                clienteDTO.getEmail(),
                clienteDTO.getTelefono(),
                LocalDateTime.now()
        );

        return clienteRepository.save(cliente);
    }

    public Cliente altaValidada(ClienteDTO clienteDTO) {

        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new EmailYaRegistradoException("El email ya está registrado");
        }

        Cliente cliente = new Cliente(
                clienteDTO.getNombre(),
                clienteDTO.getApellido(),
                clienteDTO.getEmail(),
                clienteDTO.getTelefono(),
                LocalDateTime.now()
        );

        return clienteRepository.save(cliente);
    }
}
