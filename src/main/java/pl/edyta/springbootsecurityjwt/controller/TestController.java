package pl.edyta.springbootsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edyta.springbootsecurityjwt.models.User;
import pl.edyta.springbootsecurityjwt.repo.UserRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/all")
    public List<User> allAccess() {
        List<User> all = new ArrayList<>();
        userRepository.findAll().forEach(all::add);
        return all;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('moderator')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public List<String> moderatorList() {
        List<String> lista = Stream.of("Edyta", "Joanna", "Paulina", "Anna", "Wioleta").collect(Collectors.toList());
        return lista;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board";
    }


}
