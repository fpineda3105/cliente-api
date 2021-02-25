package pe.com.fpineda.challenge.cliente.core.model;

import lombok.*;

import java.time.LocalDate;

/**
 * @author fpineda
 */

@Builder
@Setter
@Getter
@AllArgsConstructor
@ToString
public class Cliente {

    private long id;
    private String nombre;
    private String apellido;
    private int edad;
    private LocalDate fechaNacimiento;
    private LocalDate fechaMuertePosible;

}
