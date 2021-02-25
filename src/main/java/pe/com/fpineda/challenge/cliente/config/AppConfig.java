package pe.com.fpineda.challenge.cliente.config;

import org.springframework.context.annotation.*;
import pe.com.fpineda.challenge.cliente.api.CrearClienteApi;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
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

    private DatabaseConfig databaseConfig;

    public AppConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Bean
    public CrearClienteUseCase crearClienteUseCase() {
        return new CrearClienteApi(crearClientePort());
    }

    @Bean
    public CrearClientePort crearClientePort() {
        return new ClienteJdbcAdapter(this.databaseConfig.getDataSource());
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
