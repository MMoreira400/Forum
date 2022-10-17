package com.forun.Forum.controllers;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.reponse.PostagemDTO;
import com.forun.Forum.model.repositories.PostagemRepository;
import com.forun.Forum.model.repositories.UserRepository;
import com.forun.Forum.model.request.NovaPostagemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
    public ResponseEntity<PostagemDTO> registraNovaPostagem(@RequestBody @Valid NovaPostagemRequest novaPostagemRequest, UriComponentsBuilder uriComponentsBuilder) {

        Postagem novaPostagem = new Postagem(novaPostagemRequest.getPostagem(),userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail()));

        //postagemRepository.saveAndFlush(novaPostagem);
        novaPostagemRequest.adicionaePersistePostagemNoUsuario(userRepository,novaPostagem,userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail()));
        URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(novaPostagem.getAutoId()).toUri();

            return ResponseEntity.created(uri).body(new PostagemDTO(novaPostagem));

        }

    @GetMapping()
    public List<PostagemDTO> listaTodasPostagens(@PathParam("username") String username){

        if(username != null && userRepository.findByUsername(username) != null){
            return postagemRepository.findAllByUserAutoId(userRepository.findByUsername(username).getAutoId()).stream().map(PostagemDTO::new).collect(Collectors.toList());
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
