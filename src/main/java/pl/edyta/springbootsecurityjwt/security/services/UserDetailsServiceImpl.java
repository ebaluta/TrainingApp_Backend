package pl.edyta.springbootsecurityjwt.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edyta.springbootsecurityjwt.models.User;
import pl.edyta.springbootsecurityjwt.repo.UserRepository;

/*
This class implements UserDetailsService - the implementation of UserDetailSerivce will be used for
configuring DaoAuthenticationProvider by AuthenticationManagerBuilder.userDetailsService() method
UserDetailsService is for getting UserDetails object.

 */

/**
 * In code below we get full custom User object using UserRepository,
 * then we build a UserDetails object using static build() method.
 *
 * UserDetailsService interface has a method to load User by username and returns a UserDetails object that
 * Spring Security can use for authentication and validation
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(s)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: "+s));
        return UserDetailsImpl.build(user);

    }
}
