package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.CrearClienteDto;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.ListaClientes;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

/**
 * @author fpineda
 */
@Api(tags = "Users Rest API")
@RestController
@AllArgsConstructor
@Slf4j
public class ClienteController {

    private CrearClienteUseCase crearClienteApi;

    @ApiOperation(value = "Crea un usuario", code = 201)
    @PostMapping("/creacliente")
    public ResponseEntity<Void> crearCliente(@Valid @RequestBody CrearClienteDto request) {

        var result = crearClienteApi.create(request.toCommand());
        URI location = fromCurrentContextPath().path("consultacliente/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/listclientes")
    public ResponseEntity<ListaClientes> obtenerListaClientes() {
        return ResponseEntity.ok(new ListaClientes(new ArrayList<>()));
    }
}
