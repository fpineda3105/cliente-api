package pe.com.fpineda.challenge.cliente.core.port;

import pe.com.fpineda.challenge.cliente.core.model.Cliente;

import java.util.List;

/**
 * @author fpineda
 */
public interface ObtenerListaClientePort {

    List<Cliente> obtenerLista();
}
