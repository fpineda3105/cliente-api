package pe.com.fpineda.challenge.cliente.core.port;

import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;

/**
 * @author fpineda
 */
public interface CrearClientePort {

    Cliente create(CrearClientCommand command);
}
