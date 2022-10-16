package com.forun.Forum.controllers;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.entites.User;
import com.forun.Forum.model.reponse.PostagemDTO;
import com.forun.Forum.model.reponse.UserDTO;
import com.forun.Forum.model.repositories.PostagemRepository;
import com.forun.Forum.model.repositories.UserRepository;
import com.forun.Forum.model.request.NovaPostagemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostagemController {

    @Autowired
    PostagemRepository postagemRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Postagem> registraNovaPostagem(@RequestBody @Valid NovaPostagemRequest novaPostagemRequest, UriComponentsBuilder uriComponentsBuilder) {

        Postagem novaPostagem = new Postagem(novaPostagemRequest.getPostagem(),userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail()));
        postagemRepository.saveAndFlush(novaPostagem);

            URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(novaPostagem.getAutoId()).toUri();

            return ResponseEntity.created(uri).body(novaPostagem);

        }

    @GetMapping()
    public List<PostagemDTO> listaTodasPostagens(String username){
        ArrayList<PostagemDTO> postagemDTOS = new ArrayList<>();
        if(username != null && userRepository.findByUsername(username)!= null){
            (postagemRepository.findAllByUserAutoId(userRepository.findByUsername(username).getAutoId())).forEach(e ->{
                postagemDTOS.add(new PostagemDTO(e));
                });
            return postagemDTOS;
        }else{
            return postagemRepository.findAll().stream().map(PostagemDTO::new).collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity retornarPostagemPorId(@PathVariable Long id){

        Optional<Postagem> postagem = postagemRepository.findById(id);

        if(postagem.isPresent()){
            return ResponseEntity.ok(new PostagemDTO(postagem.get()));
        }
        return ResponseEntity.notFound().build();
    }

}
