package net.aarontian.springboot.demo.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    ReaderRepository readerRepository;

    public ReaderRepository getReaderRepository() {
        return readerRepository;
    }

    @Autowired
    public void setReaderRepository(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").access("hasRole('READER')")
                .antMatchers("/h2/**").permitAll()
                .and()
                .formLogin().loginPage("/login")
                            .failureUrl("/login?error=true");
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            Optional<Reader> readers = readerRepository.findById(username);
            if (readers.isPresent())
                return readers.get();
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        });
    }
/*
    @EventListener(ApplicationReadyEvent.class)
    public void addUsersAfterStartUp() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        Reader reader = new Reader();
        reader.setUsername("craig");
        reader.setFullname("Craig Walls");
        reader.setPassword(encoder.encode("password"));
        readerRepository.save(reader);
        reader.setUsername("walt");
        reader.setFullname("Walt Disney");
        reader.setPassword(encoder.encode("marceline"));
        readerRepository.save(reader);
    }
 */
}
