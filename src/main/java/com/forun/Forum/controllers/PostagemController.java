package com.forun.Forum.controllers;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.entites.User;
import com.forun.Forum.model.repositories.PostagemRepository;
import com.forun.Forum.model.repositories.UserRepository;
import com.forun.Forum.model.request.NovaPostagemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostagemController {

    @Autowired
    PostagemRepository postagemRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Postagem> registraNovaPostagem(@RequestBody NovaPostagemRequest novaPostagemRequest, UriComponentsBuilder uriComponentsBuilder) {
        try {

            User usuarioValidacao = userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail());

            if (usuarioValidacao.hashCode() != novaPostagemRequest.getUsuario().hashCode()) { // Valida valores do usuario conforme Hash
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário incorreto");
            }

            Postagem postagem = new Postagem(novaPostagemRequest.getPostagem().getTitulo(),novaPostagemRequest.getPostagem().getDescricao(),usuarioValidacao);

            postagemRepository.saveAndFlush(postagem);

            URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(postagem.getAutoId()).toUri();

            return ResponseEntity.created(uri).body(postagem);

        } catch (java.lang.NullPointerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário informado inválido");
        }
    }

    @GetMapping("/{id}")
    public Optional<Postagem> retornarPostagemPorId(@PathVariable Long id){
        return postagemRepository.findById(id);
    }

}
