package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;

import java.time.LocalDate;

/**
 * @author fpineda
 */
@Builder
@ToString
@Getter
@JsonPropertyOrder({"nombre", "apellido", "edad", "fechaNacimiento", "fechaMuertePosible"})
public class ClienteDto {

    private String nombre;
    private String apellido;
    private int edad;
    private LocalDate fechaNacimiento;
    private LocalDate fechaMuertePosible;

    public static ClienteDto fromCliente(final Cliente cliente) {
        return ClienteDto.builder()
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .edad(cliente.getEdad())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .fechaMuertePosible(cliente.getFechaMuertePosible())
                .build();
    }
}
