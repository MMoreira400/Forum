package com.forun.Forum.controllers;

import com.forun.Forum.model.reponse.UserDTO;
import com.forun.Forum.model.entites.User;
import com.forun.Forum.model.repositories.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registrarUsuario(@RequestBody @NotNull User usuario, UriComponentsBuilder uriBuilder) {

        if(userRepository.existsByEmail(usuario.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Já existe usuario com o mesmo email!");
        }

        userRepository.saveAndFlush(usuario);
        UserDTO userDTO = new UserDTO(usuario);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(usuario.getAutoId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @GetMapping("/{id}")
    public Optional<User> retornarUsuarioPorId(@PathVariable Long id){
            return userRepository.findById(id);
    }


}
