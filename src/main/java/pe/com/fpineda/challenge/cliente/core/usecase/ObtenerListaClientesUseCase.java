package pe.com.fpineda.challenge.cliente.core.usecase;

import pe.com.fpineda.challenge.cliente.core.model.Cliente;

import java.util.List;

/**
 * @author fpineda
 */
public interface ObtenerListaClientesUseCase {

    List<Cliente> obtenerLista();

}
