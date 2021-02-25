package pe.com.fpineda.challenge.cliente.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerListaClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerListaClientesUseCase;

import java.util.List;

/**
 * @author fpineda
 */
@AllArgsConstructor
@Slf4j
public class ObtenerListaClienteApi implements ObtenerListaClientesUseCase {

    private ObtenerListaClientePort port;

    @Override
    public List<Cliente> obtenerLista() {
        return port.obtenerListaClientes();
    }
}
