package be.sdlg.apps.edcmobile;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	    throws Exception {
		
		auth.inMemoryAuthentication()
        .withUser("jeanyves").password("{noop}test11").roles("USER")
        .and()
        .withUser("ellen").password("{noop}sept2014!").roles("USER");
	  

	}
	

}


