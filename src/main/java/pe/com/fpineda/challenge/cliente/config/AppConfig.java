package pe.com.fpineda.challenge.cliente.config;

import org.springframework.context.annotation.*;
import pe.com.fpineda.challenge.cliente.api.CrearClienteApi;
import pe.com.fpineda.challenge.cliente.api.ObtenerClientesKPIApi;
import pe.com.fpineda.challenge.cliente.api.ObtenerListaClienteApi;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerClientesKPIPort;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerListaClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerClientesKPIUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerListaClientesUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author fpineda
 */
@Configuration
@Import(DatabaseConfig.class)
@ComponentScan(basePackages = "pe.com.fpineda.challenge.cliente.infrastructure.adapter.web")
@Profile("!test")
public class AppConfig {

    private ClienteJdbcAdapter clienteJdbcAdapter;

    public AppConfig(final ClienteJdbcAdapter clienteJdbcAdapter) {
        this.clienteJdbcAdapter = clienteJdbcAdapter;
    }

    @Bean
    public CrearClienteUseCase crearClienteUseCase() {
        return new CrearClienteApi(crearClientePort());
    }

    @Bean
    public ObtenerListaClientesUseCase obtenerListaClientesUseCase() {
        return new ObtenerListaClienteApi(obtenerListaClientePort());
    }

    @Bean
    public ObtenerClientesKPIUseCase obtenerKPIClientesUseCase() {
        return new ObtenerClientesKPIApi(obtenerClientesKPIPort());
    }

    @Bean
    public CrearClientePort crearClientePort() {
        return this.clienteJdbcAdapter;
    }

    @Bean
    public ObtenerListaClientePort obtenerListaClientePort() {
        return this.clienteJdbcAdapter;
    }

    @Bean
    public ObtenerClientesKPIPort obtenerClientesKPIPort() {
        return this.clienteJdbcAdapter;
    }

    @Bean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pe.com.fpineda.challenge.cliente.infrastructure.adapter.web"))
                .build();
    }


}
