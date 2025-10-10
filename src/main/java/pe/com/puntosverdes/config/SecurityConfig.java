package pe.com.puntosverdes.config;

import pe.com.puntosverdes.security.JwtAuthenticationEntryPoint;
import pe.com.puntosverdes.security.JwtAuthenticationFilter;
import pe.com.puntosverdes.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	// Beans de autenticación
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// Configuración principal de seguridad
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(auth -> auth

				// AUTENTIFICACIÓN (JWT)
				.requestMatchers("/auth/**").permitAll()		
				
				// ENTIDAD USUARIO (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()
				
				.requestMatchers(
				    HttpMethod.GET, 
				    "/api/usuarios/listar", 
				    "/api/usuarios/listar/estado/**",
				    "/api/usuarios/listar/rol/**",
				    "/api/usuarios/buscar/id/**",
				    "/api/usuarios/buscar/username/**",
				    "/api/usuarios/buscar/email/**",
				    "/api/usuarios/buscar/celular/**",
				    "/api/usuarios/estadisticas",
				    "/api/usuarios/ranking"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")
				
				.requestMatchers(
				    HttpMethod.PUT,
				    "/api/usuarios/**/cambiar-contrasena/admin",
				    "/api/usuarios/**/estado/**/admin",
				    "/api/usuarios/**/asignar-rol/admin",
				    "/api/usuarios/**/ajustar-puntos/admin",
				    "/api/usuarios/**/actualizar/admin"
				).hasRole("ADMIN")

				.requestMatchers(HttpMethod.DELETE, "/api/usuarios/eliminar/**").hasRole("ADMIN")
				
				.requestMatchers(HttpMethod.GET, "/api/usuarios/perfil/mi").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/actualizar/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/usuarios/**/perfil").authenticated()
				.requestMatchers("/api/usuarios/**").authenticated()
				
				// ENTIDAD ENTREGA (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/entregas/registrar").hasAnyRole("CIUDADANO", "RECOLECTOR")
				.requestMatchers(HttpMethod.POST, "/api/entregas/*/archivos").hasAnyRole("CIUDADANO", "RECOLECTOR")

				.requestMatchers(HttpMethod.GET, 
				    "/api/entregas/listar", 
				    "/api/entregas/listar/**", 
				    "/api/entregas/detalle/**", 
				    "/api/entregas/usuario/**/ultima", 
				    "/api/entregas/archivos/**"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.PUT, "/api/entregas/validar/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/entregas/eliminar/**").hasRole("ADMIN")
				
				// ENTIDAD CAMPANIAS (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/campanias/registrar").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.GET, 
				    "/api/campanias/listar", 
				    "/api/campanias/listar/**",
				    "/api/campanias/buscar/**",
				    "/api/campanias/detalle/**",
				    "/api/campanias/estadisticas"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.PUT, "/api/campanias/actualizar/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/campanias/eliminar/**").hasRole("ADMIN")
				
				// ENTIDAD PUNTOVERDE (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/puntos-verdes/registrar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/puntos-verdes/actualizar/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.GET, 
				    "/api/puntos-verdes/listar",
				    "/api/puntos-verdes/listar/**",
				    "/api/puntos-verdes/buscar/**",
				    "/api/puntos-verdes/detalle/**",
				    "/api/puntos-verdes/estadisticas"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.DELETE, "/api/puntos-verdes/eliminar/**").hasRole("ADMIN")

				// ENTIDAD RECOMPENSA (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/recompensas/registrar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/recompensas/actualizar/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.GET,
				    "/api/recompensas/listar",
				    "/api/recompensas/listar/**",
				    "/api/recompensas/buscar/**",
				    "/api/recompensas/estadisticas"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.DELETE, "/api/recompensas/eliminar/**").hasRole("ADMIN")
				
				// ENTIDAD CANJE (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/canjes/realizar/**").hasAnyRole("RECOLECTOR", "CIUDADANO")

				.requestMatchers(HttpMethod.GET,
				    "/api/canjes/listar",
				    "/api/canjes/listar/**"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.DELETE, "/api/canjes/eliminar/**").hasRole("ADMIN")

				// ENTIDAD INCIDENCIA (PERMISOS)
				.requestMatchers(HttpMethod.POST, "/api/incidencias/registrar").hasRole("RECOLECTOR")

				.requestMatchers(HttpMethod.GET,
				    "/api/incidencias/listar",
				    "/api/incidencias/listar/**",
				    "/api/incidencias/detalle/**",
				    "/api/incidencias/usuario/**",
				    "/api/incidencias/archivos/**"
				).hasAnyRole("ADMIN", "MUNICIPALIDAD")

				.requestMatchers(HttpMethod.PUT, "/api/incidencias/validar/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.POST, "/api/incidencias/*/archivos").hasRole("RECOLECTOR")
				.requestMatchers(HttpMethod.DELETE, "/api/incidencias/eliminar/**").hasRole("ADMIN")

				// CUALQUIER OTRA
				.anyRequest().authenticated()).exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
