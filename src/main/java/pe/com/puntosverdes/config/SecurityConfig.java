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
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth

                // --- Rutas públicas ---
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/usuarios/registrar").permitAll()
                .requestMatchers(HttpMethod.GET, "/entregas/evidencias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/incidencias/evidencias/**").permitAll()

                // --- CANJES ---
                // Solicitar canje -> CIUDADANO o RECOLECTOR
                .requestMatchers(HttpMethod.POST, "/api/canjes/solicitar").hasAnyRole("CIUDADANO", "RECOLECTOR")
                // Resolver (aprobar o rechazar) -> ADMIN o MUNICIPALIDAD
                .requestMatchers(HttpMethod.PUT, "/api/canjes/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
                // Consultar (listar, obtener por ID, por usuario)
                .requestMatchers(HttpMethod.GET, "/api/canjes/**").authenticated()

                // --- Gestión de usuarios ---
                .requestMatchers("/api/usuarios/{id}/asignar-rol").hasRole("ADMIN")

                // --- ENTREGAS ---
                .requestMatchers(HttpMethod.POST, "/entregas/**").hasRole("CIUDADANO")
                .requestMatchers(HttpMethod.PUT, "/entregas/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
                .requestMatchers(HttpMethod.GET, "/entregas/**").authenticated()

                // --- INCIDENCIAS ---
                .requestMatchers(HttpMethod.POST, "/incidencias/{id}/evidencias").hasRole("RECOLECTOR")
                .requestMatchers(HttpMethod.POST, "/incidencias/**").hasRole("RECOLECTOR")
                .requestMatchers(HttpMethod.PUT, "/incidencias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
                .requestMatchers(HttpMethod.GET, "/incidencias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD", "RECOLECTOR")

                // --- PUNTOS VERDES ---
                .requestMatchers(HttpMethod.GET, "/puntos-verdes/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/puntos-verdes/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
                .requestMatchers(HttpMethod.PUT, "/puntos-verdes/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

                // --- CAMPAÑAS ---
                .requestMatchers(HttpMethod.GET, "/campanias/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/campanias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
                .requestMatchers(HttpMethod.PUT, "/campanias/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")

                // --- DASHBOARD ---
                // Solo accesible por ADMIN o MUNICIPALIDAD
                .requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAnyRole("ADMIN", "MUNICIPALIDAD")
                
                // --- Cualquier otra ruta requiere autenticación ---
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // --- Filtro JWT ---
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
