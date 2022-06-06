package com.github.daniel.ifood.cadastro;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.daniel.ifood.cadastro.dto.AdicionarPratoDTO;
import com.github.daniel.ifood.cadastro.dto.AdicionarRestauranteDTO;
import com.github.daniel.ifood.cadastro.dto.AtualizarPratoDTO;
import com.github.daniel.ifood.cadastro.dto.AtualizarRestauranteDTO;
import com.github.daniel.ifood.cadastro.dto.PratoDTO;
import com.github.daniel.ifood.cadastro.dto.PratoMapper;
import com.github.daniel.ifood.cadastro.dto.RestauranteDTO;
import com.github.daniel.ifood.cadastro.dto.RestauranteMapper;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteResource {
	
    @Inject
    RestauranteMapper restauranteMapper;

    @Inject
    PratoMapper pratoMapper;

    @GET
    @Tag(name = "Restaurante")
    public List<RestauranteDTO> hello() {
    	Stream<Restaurante> restaurantes = Restaurante.streamAll();
        return restaurantes.map(r -> restauranteMapper.toRestauranteDTO(r)).collect(Collectors.toList());
    }
    
    @POST
    @Transactional
    @Tag(name = "Restaurante")
    public Response adicionar(AdicionarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();
        return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    @Tag(name = "Restaurante")
    public void editar(@PathParam("id") Long id, AtualizarRestauranteDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOp.get();

        restauranteMapper.toRestaurante(dto, restaurante);

        restaurante.persist();
    }
    
    @DELETE
    @Path("{id}")
    @Transactional
    @Tag(name = "Restaurante")
    public void deletar(@PathParam("id") Long id) {
    	Optional<Restaurante> restauranteEncontrado = Restaurante.findByIdOptional(id);
    	restauranteEncontrado.ifPresentOrElse(Restaurante::delete, 
    			() -> {throw new NotFoundException("Restaurante não encontrado: " + id);});
    }
    
    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "Prato")
    public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        Stream<Prato> pratos = Prato.stream("restaurante", restauranteOp.get());
        return pratos.map(p -> pratoMapper.toDTO(p)).collect(Collectors.toList());
    }

    @POST
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name = "Prato")
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, AdicionarPratoDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        //        //Utilizando dto, recebi detached entity passed to persist:
        //        Prato prato = new Prato();
        //        prato.nome = dto.nome;
        //        prato.descricao = dto.descricao;
        //
        //        prato.preco = dto.preco;
        //        prato.restaurante = restauranteOp.get();
        //        prato.persist();

        Prato prato = pratoMapper.toPrato(dto);
        prato.restaurante = restauranteOp.get();
        prato.persist();
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "Prato")
    public void atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, AtualizarPratoDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }

        //No nosso caso, id do prato vai ser único, independente do restaurante...
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);

        if (pratoOp.isEmpty()) {
            throw new NotFoundException("Prato não existe");
        }
        Prato prato = pratoOp.get();
        pratoMapper.toPrato(dto, prato);
        prato.persist();
    }

    @DELETE
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "Prato")
    public void delete(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }

        Optional<Prato> pratoOp = Prato.findByIdOptional(id);

        pratoOp.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });
    }
   
}