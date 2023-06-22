package in.sbms.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private static final String Admin = "ADMIN";
	private static final String USER = "USER";
	
	@Autowired
	private DataSource source;
	
	@Autowired
	private void authmanager(AuthenticationManagerBuilder auth) throws Exception{	
		
		auth.jdbcAuthentication()
		    .dataSource(source)
		    .passwordEncoder(new BCryptPasswordEncoder())
		    .usersByUsernameQuery("select username,password,enabled from users where username=? ")
		    .authoritiesByUsernameQuery("select username,authority from authorities where username=?");
	}
	
	
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((req) -> req
                        .antMatchers("/greet").permitAll()
                        .antMatchers("/admin").hasRole(Admin)
                        .antMatchers("/user").hasAnyRole(Admin, USER)
                        .anyRequest().authenticated()

        ).formLogin(withDefaults());
		
		return http.build();

	}

}
