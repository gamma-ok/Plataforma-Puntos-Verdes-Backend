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
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

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

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(withDefaults()).authorizeHttpRequests(auth -> auth

				// AUTENTIFICACIÓN (JWT)
				.requestMatchers("/api/auth/**").permitAll()
				
				// Permitir acceso publico a las imagenes
	            .requestMatchers("/uploads/perfiles/**").permitAll()

				// ENTIDAD USUARIO
				.requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/usuarios/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/listar/estado/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/listar/rol/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/buscar/id/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/buscar/username/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/buscar/email/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/buscar/celular/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/estadisticas").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/ranking").hasAnyRole("ADMIN", "MUNICIPALIDAD", "RECOLECTOR", "CIUDADANO")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/ranking/mi").hasAnyRole("ADMIN", "MUNICIPALIDAD", "RECOLECTOR", "CIUDADANO")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/*/cambiar-contrasena/admin").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/*/estado/*/admin").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/*/asignar-rol/admin").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/*/ajustar-puntos/admin").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/*/actualizar/admin").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/usuarios/eliminar/*").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/perfil/mi").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/actualizar/*").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/usuarios/*/perfil").authenticated()
				.requestMatchers("/api/usuarios/*").authenticated()

				// ENTIDAD ENTREGA
				.requestMatchers(HttpMethod.POST, "/api/entregas/registrar").hasAnyRole("CIUDADANO", "RECOLECTOR")
				.requestMatchers(HttpMethod.POST, "/api/entregas/*/archivos").hasAnyRole("CIUDADANO", "RECOLECTOR")
				.requestMatchers(HttpMethod.GET, "/api/entregas/mis-ultimas").hasAnyRole("CIUDADANO", "RECOLECTOR")
				.requestMatchers(HttpMethod.GET, "/api/entregas/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/entregas/listar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/entregas/detalle/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/entregas/usuario/*/ultima").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/entregas/archivos/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/entregas/validar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/entregas/eliminar/*").hasRole("ADMIN")

				// ENTIDAD CAMPANIAS
				.requestMatchers(HttpMethod.POST, "/api/campanias/registrar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/campanias/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/campanias/listar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/campanias/buscar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/campanias/detalle/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/campanias/estadisticas").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/campanias/actualizar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/campanias/eliminar/*").hasRole("ADMIN")

				// ENTIDAD PUNTOVERDE
				.requestMatchers(HttpMethod.POST, "/api/puntos-verdes/registrar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/puntos-verdes/actualizar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/puntos-verdes/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/puntos-verdes/listar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/puntos-verdes/buscar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/puntos-verdes/detalle/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/puntos-verdes/estadisticas").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/puntos-verdes/eliminar/*").hasRole("ADMIN")

				// ENTIDAD RECOMPENSA
				.requestMatchers(HttpMethod.POST, "/api/recompensas/registrar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/recompensas/actualizar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/recompensas/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/recompensas/listar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/recompensas/buscar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/recompensas/estadisticas").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/recompensas/eliminar/*").hasRole("ADMIN")

				// ENTIDAD CANJE
				.requestMatchers(HttpMethod.POST, "/api/canjes/realizar/*").hasAnyRole("RECOLECTOR", "CIUDADANO")
				.requestMatchers(HttpMethod.GET, "/api/canjes/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/canjes/listar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.DELETE, "/api/canjes/eliminar/*").hasRole("ADMIN")

				// ENTIDAD INCIDENCIA
				.requestMatchers(HttpMethod.POST, "/api/incidencias/registrar").hasRole("RECOLECTOR")
				.requestMatchers(HttpMethod.GET, "/api/incidencias/listar").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/incidencias/listar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/incidencias/detalle/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/incidencias/usuario/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/incidencias/archivos/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/incidencias/validar/*").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.POST, "/api/incidencias/*/archivos").hasRole("RECOLECTOR")
				.requestMatchers(HttpMethod.DELETE, "/api/incidencias/eliminar/*").hasRole("ADMIN")

				// CUALQUIER OTRA
				.anyRequest().authenticated()).exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
