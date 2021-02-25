package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * @author fpineda
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CrearClienteDto {

    @Pattern(regexp = "[a-zA-z]+", message = "nombre invalido")
    private String nombre;

    @Pattern(regexp = "[a-zA-z]+", message = "apellido invalido")
    private String apellido;

    @Min(value = 18, message = "cliente debe ser mayor de edad")
    private Integer edad;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaNacimiento;

    public CrearClientCommand toCommand() {
        return CrearClientCommand.builder().nombre(nombre).apellido(apellido)
                .edad(edad).fechaNacimiento(fechaNacimiento).build();
    }

}
