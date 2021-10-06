package com.george.wspringmvcrestdocs.web.mappers;

import com.george.wspringmvcrestdocs.domain.Beer;
import com.george.wspringmvcrestdocs.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto dto);
}
