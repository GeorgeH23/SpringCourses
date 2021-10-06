package com.george.wspringmvcrestdocs.repositories;

import com.george.wspringmvcrestdocs.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
