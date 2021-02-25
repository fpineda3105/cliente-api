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
import pe.com.fpineda.challenge.cliente.core.model.ClientesKPI;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerClientesKPIUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerListaClientesUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.ClienteDto;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.CrearClienteDto;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.ListaClientes;

import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

/**
 * @author fpineda
 */
@Api(tags = "Cliente Rest API")
@RestController
@AllArgsConstructor
@Slf4j
public class ClienteController {

    private CrearClienteUseCase crearClienteApi;
    private ObtenerListaClientesUseCase obtenerListaClientesApi;
    private ObtenerClientesKPIUseCase obtenerKpiClientesApi;

    @ApiOperation(value = "Crea un cliente y devuelve la URL de localizacion", code = 201)
    @PostMapping("/creacliente")
    public ResponseEntity<Void> crearCliente(@Valid @RequestBody CrearClienteDto request) {

        var result = crearClienteApi.create(request.toCommand());

        URI location = fromCurrentContextPath().path("consultacliente/{id}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Obtiene lista de clientes", code = 200)
    @GetMapping("/listclientes")
    public ResponseEntity<ListaClientes> obtenerListaClientes() {
        var result = obtenerListaClientesApi.obtenerLista();

        var resultTransformed = result.stream().map(ClienteDto::fromCliente).collect(Collectors.toList());
        return ResponseEntity.ok(new ListaClientes(resultTransformed));
    }

    @ApiOperation(value = "Obtiene KPI de clientes, Promedio de Edades y Desviacion Estandar", code = 200)
    @GetMapping("/kpideclientes")
    public ResponseEntity<ClientesKPI> obtenerClientesKpi() {
        return ResponseEntity.ok(obtenerKpiClientesApi.obtenerClientesKPI());
    }
}
