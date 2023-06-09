package com.codegym.service.locationRegion;

import com.codegym.model.LocationRegion;
import com.codegym.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class LocationRegionServiceImpl implements ILocationRegionService{

@Autowired
    LocationRegionRepository locationRegionRepository;

    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public LocationRegion getById(Long id) {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocationRegion save(LocationRegion locationRegion) {
        return locationRegionRepository.save(locationRegion);
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public void remove(LocationRegion locationRegion) {

    }
}
