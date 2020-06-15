package com.utn.utnphones;

import com.utn.utnphones.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class UtnphonesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtnphonesApplication.class, args);
	}


	/*TODO
	*  ACA SETEO LA CONFIGURACION DE SEGURIDAD DE LA API. INDICO QUE LA UNICA RUTA QUE NO TIENE SEGURIDAD
	*  ES LA DE "/api/user/login" PORQUE SINO NO ME VOY A PODER LOGEAR
	* */
	@EnableWebSecurity
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
					.anyRequest().authenticated();
		}
	}

}
