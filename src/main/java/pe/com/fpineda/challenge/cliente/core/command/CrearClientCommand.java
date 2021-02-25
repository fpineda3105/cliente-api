package pe.com.fpineda.challenge.cliente.core.command;

import lombok.Builder;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;

import java.time.LocalDate;

/**
 * @author fpineda
 */
@Builder
public class CrearClientCommand {

    private String nombre;
    private String apellido;
    private int edad;
    private LocalDate fechaNacimiento;

    public Cliente toCliente() {
        return Cliente.builder().nombre(nombre).apellido(apellido).edad(edad).fechaNacimiento(fechaNacimiento).build();
    }

}
