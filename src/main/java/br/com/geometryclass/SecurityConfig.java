package br.com.geometryclass;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * @Autowired
	 * private DataSource dataSource;
	 */	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers()
				.frameOptions()
					.sameOrigin()
					.and()
				.authorizeRequests()
					.antMatchers("/css/**", "/js/**", "/img/**", "/", "/home", "/cadastro", "/aulas/*", "/aulas")
						.permitAll()
					.antMatchers("/aula/*")
						.hasAuthority("LER_AULA")
					.antMatchers("/editor")
						.hasAuthority("CRIAR_AULA")
					.anyRequest()
						.authenticated()
					.and()
				.formLogin()
					.loginPage("/home#login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/home")
	                .failureUrl("/home#login-erro")
	                .usernameParameter("usuario")
	                .passwordParameter("senha")
	                	.permitAll()
	                .and()
				.logout()
					.logoutSuccessUrl("/home")
						.permitAll()
					.and()
//				.rememberMe()
//					.rememberMeCookieName("remember-me-cookie")
//					.tokenRepository(persistentTokenRepository())
//					.tokenValiditySeconds(2*24*60*60)
//					.and()
				.exceptionHandling();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
	
	/*
	 * private PersistentTokenRepository persistentTokenRepository() {
	 * JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
	 * tokenRepository.setDataSource(dataSource); return tokenRepository; }
	 */
}


