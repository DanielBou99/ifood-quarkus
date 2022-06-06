package com.github.daniel.ifood.cadastro.dto;

import com.github.daniel.ifood.cadastro.Prato;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    PratoDTO toDTO(Prato p);

    Prato toPrato(AdicionarPratoDTO dto);

    void toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);

}
