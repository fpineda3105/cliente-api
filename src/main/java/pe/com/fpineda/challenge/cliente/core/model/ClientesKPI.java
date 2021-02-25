package pe.com.fpineda.challenge.cliente.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author fpineda
 */
@AllArgsConstructor
@Builder
@Getter
public class ClientesKPI {

    private double promedioEdad;

    private double desviacionEstandar;

}
