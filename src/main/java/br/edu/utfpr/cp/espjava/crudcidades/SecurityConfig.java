package br.edu.utfpr.cp.espjava.crudcidades;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{


	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/").hasAnyAuthority("listar", "admin")
		.antMatchers("/criar").hasAnyAuthority("admin")
		.antMatchers("/excluir").hasAnyAuthority("admin")
		.antMatchers("/preparaAlterar").hasAnyAuthority("admin")
		.antMatchers("/alterar").hasAnyAuthority("admin")
		.anyRequest().denyAll()
		.and()
		.formLogin().permitAll();
	}

	@Bean
	public PasswordEncoder cifrador() {
		return new BCryptPasswordEncoder();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void printSenhas() {
		System.out.println(this.cifrador().encode("teste123"));
	}

}
