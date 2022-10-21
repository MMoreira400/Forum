package com.forun.Forum.controllers;

import com.forun.Forum.model.entites.Postagem;
import com.forun.Forum.model.reponse.PostagemDTO;
import com.forun.Forum.model.repositories.PostagemRepository;
import com.forun.Forum.model.repositories.UserRepository;
import com.forun.Forum.model.request.NovaPostagemRequest;
import com.forun.Forum.model.request.UpdatePostagemRequest;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
    @CacheEvict( value = {"postagemPorId"} )
    public ResponseEntity<PostagemDTO> registraNovaPostagem(@RequestBody @Valid NovaPostagemRequest novaPostagemRequest, UriComponentsBuilder uriComponentsBuilder) {

        Postagem novaPostagem = new Postagem(novaPostagemRequest.getPostagem(),userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail()));
        novaPostagemRequest.adicionaePersistePostagemNoUsuario(userRepository,novaPostagem,userRepository.findByEmailContainingIgnoreCase(novaPostagemRequest.getUsuario().getEmail()));
        URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(novaPostagem.getAutoId()).toUri();

            return ResponseEntity.created(uri).body(new PostagemDTO(novaPostagem));

        }

    @GetMapping()
    public Page<PostagemDTO> listaTodasPostagens(@PathParam("username") String username,
                                                 @PageableDefault(page = 0, size = 10,sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable paginacao){

        if(username != null && userRepository.findByUsername(username).isPresent()){
            return PostagemDTO.converterPostagemPage(postagemRepository.findAllByUserAutoId(userRepository.findByUsername(username).get().getAutoId(), paginacao));
        }else{
            return PostagemDTO.converterPostagemPage(postagemRepository.findAll(paginacao));
        }

    }

    @GetMapping("/{id}")
    @Cacheable(value = "postagemPorId")
    public ResponseEntity retornarPostagemPorId(@PathVariable Long id){

        Optional<Postagem> postagem = postagemRepository.findById(id);

        if(postagem.isPresent()){
            return ResponseEntity.ok(new PostagemDTO(postagem.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    @CacheEvict( value = {"postagemPorId"} )
    public ResponseEntity<PostagemDTO> updatePostagem(@PathVariable("id") Long idPostagem, @RequestBody @Valid UpdatePostagemRequest postagemUpdate){

        Optional<Postagem> postagemAlteracao = postagemRepository.findById(idPostagem);

        if(postagemAlteracao.isPresent()){
            UpdatePostagemRequest.alteraePersistePostagem(postagemRepository, idPostagem,postagemUpdate);
            return ResponseEntity.ok(new PostagemDTO(postagemAlteracao.get()));
        }
        return ResponseEntity.notFound().build();
    }

}
