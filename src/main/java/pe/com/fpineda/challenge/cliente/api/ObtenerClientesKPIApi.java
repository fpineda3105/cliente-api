package pe.com.fpineda.challenge.cliente.api;

import lombok.AllArgsConstructor;
import pe.com.fpineda.challenge.cliente.core.model.ClientesKPI;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerClientesKPIPort;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerClientesKPIUseCase;

/**
 * @author fpineda
 */
@AllArgsConstructor
public class ObtenerClientesKPIApi implements ObtenerClientesKPIUseCase {

    private ObtenerClientesKPIPort port;

    @Override
    public ClientesKPI obtenerClientesKPI() {
        return this.port.obtenerClientesKPI();
    }
}
