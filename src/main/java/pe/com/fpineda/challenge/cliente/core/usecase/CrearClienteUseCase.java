package pe.com.fpineda.challenge.cliente.core.usecase;

import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;

/**
 * @author fpineda
 */
public interface CrearClienteUseCase {

    Cliente create(CrearClientCommand command);

}
