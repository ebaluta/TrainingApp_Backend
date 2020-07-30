package pl.edyta.springbootsecurityjwt.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.edyta.springbootsecurityjwt.models.ERole;
import pl.edyta.springbootsecurityjwt.models.Role;
import pl.edyta.springbootsecurityjwt.payload.request.SignupRequest;
import pl.edyta.springbootsecurityjwt.repo.RoleRepository;
import pl.edyta.springbootsecurityjwt.repo.UserRepository;


import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

    SignupRequest signupRequest = new SignupRequest();

    @Before
    public void setUpSignUpRequest(){
        signupRequest.setEmail("test@test.pl");
        signupRequest.setUsername("test2020");
        signupRequest.setPassword("Test2020@");

        Set<String> roles=new HashSet<>();
        Role userRole=roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(()->new RuntimeException("Role is not found"));
        roles.add(userRole.toString());

        signupRequest.setRoles(roles);

    }

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Test()
    public void shouldReturnResponseOk() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity httpEntity = new HttpEntity(signupRequest,headers);

        RestTemplate template = new RestTemplate();

        ResponseEntity responseEntity = template.exchange("http://localhost:8080/api//auth/signup",
                HttpMethod.POST,
                httpEntity,
                Object.class
                );

     //   userRepository.deleteAllByUsername(signupRequest.getUsername());

      Assertions.assertEquals(responseEntity.getStatusCode().toString(),"200 OK");

    }

    @After()
    public void clean() {

        try {
            userRepository.deleteAllByUsername(signupRequest.getUsername());
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }
    }


}
