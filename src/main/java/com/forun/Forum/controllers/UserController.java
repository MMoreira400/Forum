package com.forun.Forum.controllers;

import com.forun.Forum.model.reponse.UserDTO;
import com.forun.Forum.model.entites.User;
import com.forun.Forum.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDTO> registrarUsuario(@RequestBody @Valid User usuario, UriComponentsBuilder uriBuilder) {

        userRepository.saveAndFlush(usuario);
        UserDTO userDTO = new UserDTO(usuario);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(usuario.getAutoId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity retornarUsuarioPorId(@PathVariable Long id){

        Optional<User> usuario =  userRepository.findById(id);

        if(usuario.isPresent()){
        return ResponseEntity.ok(new UserDTO(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }


}
