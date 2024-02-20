package com.example.hotelmanagementsystem.services.impl;

import com.example.hotelmanagementsystem.controllers.LoggingController;
import com.example.hotelmanagementsystem.models.Guest;
import com.example.hotelmanagementsystem.models.Person;
import com.example.hotelmanagementsystem.models.SharedData;
import com.example.hotelmanagementsystem.repositories.GuestRepository;
import com.example.hotelmanagementsystem.repositories.PersonRepository;
import com.example.hotelmanagementsystem.services.GuestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.parser.Entity;
import java.util.stream.Collectors;


@Service
public class GuestServiceImpl implements GuestService {

    GuestRepository guestRepository;
    EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    private LoggingController loggingController;

    @Autowired
    public GuestServiceImpl(PersonRepository personRepository, GuestRepository guestRepository, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.guestRepository = guestRepository;
        this.passwordEncoder=passwordEncoder;
        this.entityManager=entityManager;
        this.personRepository=personRepository;
    }

    public Long registerGuest(Person person)
    {
        try{
            if (personRepository.existsByEmail(person.getEmail())) {
                loggingController.logger.info("Email "+person.getEmail()+" already exists");
                return (long) -1;
            }
            if(person.getPassword()==null) {
                loggingController.logger.info("Password null for "+person.getEmail());
                return 0L;
            }
            person.setPassword(passwordEncoder.encode((person.getPassword())));

            person.setType(SharedData._GuestType);
            if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet()).contains("ROLE_ANONYMOUS"))
            {
                person.setCreatedBy(person.getEmail());
                person.setModifiedBy(person.getEmail());
            }
            else {
                person.setCreatedBy(null);
                person.setModifiedBy(null);
            }
            Guest guest= new ObjectMapper().readValue((new ObjectMapper().writeValueAsString(person)), Guest.class);
            guest=guestRepository.save(guest);
            if(guest.getPersonId()<1)
            {
                return (long) 0;
            }
            return guest.getPersonId();
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return (long) -1;

    }

    public Long findGuestId(String email)
    {
        try{
           return personRepository.findByEmail(email).getPersonId();
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return (long) 0;
    }
}
