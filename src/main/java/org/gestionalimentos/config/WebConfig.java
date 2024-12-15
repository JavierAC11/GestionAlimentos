package org.gestionalimentos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración global de CORS (Cross-Origin Resource Sharing) para permitir que
 * el frontend (u otras aplicaciones) accedan a recursos de este backend.
 *
 * Esta configuración es útil cuando se desea permitir que aplicaciones de frontend,
 * como las que corren en otros dominios o puertos, puedan realizar solicitudes
 * a la API del backend.
 */
@Configuration
public class WebConfig {

    /**
     * Configura y registra un filtro CORS global para la aplicación.
     * Este filtro permite controlar qué dominios, cabeceras y métodos pueden
     * interactuar con el backend. Se establece una configuración CORS
     * permitiendo acceso desde un dominio específico y con ciertos parámetros.
     *
     * @return un bean de tipo CorsFilter configurado que será utilizado para gestionar
     *         las solicitudes CORS en la aplicación.
     */
    @Bean
    public CorsFilter corsFilter() {
        // Crear un objeto de configuración CORS para establecer las reglas de acceso.
        CorsConfiguration config = new CorsConfiguration();

        // Permitir solicitudes desde el dominio "http://localhost:3000" (puede ser cambiado
        // por el dominio del frontend en producción).
        config.addAllowedOrigin("http://localhost:3000");

        // Permitir todos los encabezados (headers) en las solicitudes entrantes.
        config.addAllowedHeader("*"); // Permite todos los tipos de encabezados (headers).

        // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, etc.) en las solicitudes.
        config.addAllowedMethod("*"); // Permite todos los métodos HTTP.

        // Permitir que las solicitudes incluyan credenciales como cookies o cabeceras de autenticación.
        config.setAllowCredentials(true); // Habilita el uso de credenciales en las solicitudes.

        // Establecer el tiempo de vida de la configuración CORS. En este caso, 1 hora (3600 segundos).
        config.setMaxAge(3600L); // Tiempo de vida de la configuración CORS en segundos.

        // Crear un objeto que gestiona las configuraciones CORS basadas en la URL.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Registrar la configuración CORS para todas las URLs (/**).
        source.registerCorsConfiguration("/**", config);

        // Retornar el filtro CORS configurado, que será aplicado a todas las solicitudes.
        return new CorsFilter(source);
    }
}
