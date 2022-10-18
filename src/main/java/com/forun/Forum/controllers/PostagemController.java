package com.forun.Forum.controllers;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.reponse.PostagemDTO;
import com.forun.Forum.model.repositories.PostagemRepository;
import com.forun.Forum.model.repositories.UserRepository;
import com.forun.Forum.model.request.NovaPostagemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        novaPostagemRequest.adicionaePersistePostagemNoUsuario(userRepository,novaPostagem,userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail()));
        URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(novaPostagem.getAutoId()).toUri();

            return ResponseEntity.created(uri).body(new PostagemDTO(novaPostagem));

        }

    @GetMapping()
    public Page<PostagemDTO> listaTodasPostagens(@PathParam("username") String username, @RequestParam int pagina, @RequestParam int qnt ){

        Pageable paginacao = PageRequest.of(pagina,qnt);

        if(username != null && userRepository.findByUsername(username) != null){
            return PostagemDTO.converterPostagem(postagemRepository.findAllByUserAutoId(userRepository.findByUsername(username).getAutoId(), paginacao));
        }else{
            return PostagemDTO.converterPostagem(postagemRepository.findAll(paginacao));
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
