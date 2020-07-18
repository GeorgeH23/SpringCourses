package com.george.petclinicapplication.services.map;

import com.george.petclinicapplication.model.Specialty;
import com.george.petclinicapplication.services.SpecialitiesService;

import java.util.Set;

public class SpecialityMapService extends AbstractMapService<Specialty, Long> implements SpecialitiesService {

    @Override
    public Set<Specialty> findAll() {
        return super.findAll();
    }

    @Override
    public Specialty findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Specialty save(Specialty object) {
        return super.save(object);
    }

    @Override
    public void delete(Specialty object) {
        super.delete(object);
    }

    @Override
    public void deleteByID(Long id) {
        super.deleteById(id);
    }
}
