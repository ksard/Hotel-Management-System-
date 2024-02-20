package com.example.hotelmanagementsystem.security;

import com.example.hotelmanagementsystem.models.Person;
import com.example.hotelmanagementsystem.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private final PersonRepository userRepository;

    @Autowired
    public CustomUserDetailsService(PersonRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = userRepository.findByEmail(email);
        if(user==null)
            throw new UsernameNotFoundException("Email not found");
        List<String> roles=new ArrayList<String>();
        roles.add(user.getType());
        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(roles));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}