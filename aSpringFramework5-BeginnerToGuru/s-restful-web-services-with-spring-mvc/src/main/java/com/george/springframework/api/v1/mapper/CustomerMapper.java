package com.george.springframework.api.v1.mapper;

import com.george.springframework.api.v1.model.CustomerDTO;
import com.george.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    // @Mapping(source = "getId", target = "id")
    CustomerDTO customerToCustomerDTO(Customer customer);
}
