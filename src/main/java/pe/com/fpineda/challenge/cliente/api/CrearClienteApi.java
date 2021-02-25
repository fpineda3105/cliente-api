package pe.com.fpineda.challenge.cliente.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;

/**
 * @author fpineda
 */
@AllArgsConstructor
@Slf4j
public class CrearClienteApi implements CrearClienteUseCase {

    private CrearClientePort port;

    @Override
    public Cliente create(final CrearClientCommand command) {
        return port.create(command);
    }

}
