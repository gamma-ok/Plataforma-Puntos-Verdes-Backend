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

	// --- Beans de autenticación ---
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

	// --- Configuración principal de seguridad ---
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(auth -> auth

				// Rutas Públicas
				.requestMatchers("/auth/**").permitAll().requestMatchers("/api/usuarios/registrar").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/entregas/evidencias/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/incidencias/evidencias/**").permitAll()

				// CANJES
				.requestMatchers(HttpMethod.POST, "/api/canjes/solicitar").hasAnyRole("CIUDADANO", "RECOLECTOR")
				.requestMatchers(HttpMethod.PUT, "/api/canjes/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/canjes/**").authenticated()

				// GESTIÓN DE USUARIOS
				.requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}/ajustar-puntos").hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}/asignar-rol").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}/cambiar-contrasena").hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}/estado/**").hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/api/usuarios/perfil/mi").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				// ENTREGAS
				.requestMatchers(HttpMethod.POST, "/api/entregas/**").hasAnyRole("CIUDADANO", "RECOLECTOR")
				.requestMatchers(HttpMethod.PUT, "/api/entregas/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/entregas/**").authenticated()

				// INCIDENCIAS 
				.requestMatchers(HttpMethod.POST, "/api/incidencias/{id}/evidencias").hasRole("RECOLECTOR")
				.requestMatchers(HttpMethod.POST, "/api/incidencias/**").hasRole("RECOLECTOR")
				.requestMatchers(HttpMethod.PUT, "/api/incidencias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.GET, "/api/incidencias/**")
				.hasAnyRole("ADMIN", "MUNICIPALIDAD", "RECOLECTOR")

				// PUNTOS VERDES
				.requestMatchers(HttpMethod.GET, "/api/puntos-verdes/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/puntos-verdes/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/puntos-verdes/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				// CAMPANIAS
				.requestMatchers(HttpMethod.GET, "/api/campanias/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/campanias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
				.requestMatchers(HttpMethod.PUT, "/api/campanias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				// DASHBOARD
				.requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

				// Cualquier otra ruta requiere autenticación
				.anyRequest().authenticated()).exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
