package org.gestionalimentos.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para redirigir automáticamente las solicitudes HTTP a HTTPS.
 * Esta clase asegura que todas las solicitudes HTTP (puerto 8080) sean redirigidas
 * al puerto HTTPS (8443) para garantizar una comunicación segura.
 */
@Configuration
public class HttpToHttpsRedirectConfig {

    /**
     * Método que personaliza el servidor web embebido de Spring Boot (Tomcat)
     * para añadir un conector que redirige las solicitudes HTTP a HTTPS.
     *
     * @return WebServerFactoryCustomizer<TomcatServletWebServerFactory> que permite
     *         agregar el conector HTTP al servidor Tomcat.
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> redirectHttpToHttps() {
        // Devuelve un customizador para el servidor Tomcat que agrega un conector HTTP.
        return factory -> factory.addAdditionalTomcatConnectors(createHttpConnector());
    }

    /**
     * Crea un conector HTTP que escucha en el puerto 8080 y redirige las solicitudes
     * al puerto HTTPS (8443).
     *
     * @return Connector El conector configurado que se usará para la redirección.
     */
    private Connector createHttpConnector() {
        // Crear un conector de Tomcat usando el protocolo predeterminado.
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);

        // Configuración del conector para escuchar en el puerto HTTP.
        connector.setScheme("http"); // Indica que el conector usará HTTP.
        connector.setPort(8080); // El puerto HTTP en el que se escucharán las solicitudes.
        connector.setSecure(false); // Indica que este conector no es seguro (no es HTTPS).

        // Redirigir todas las solicitudes al puerto HTTPS (8443).
        connector.setRedirectPort(8443); // Especifica el puerto al que se redirigirán las solicitudes HTTP.

        // Retorna el conector configurado.
        return connector;
    }
}
