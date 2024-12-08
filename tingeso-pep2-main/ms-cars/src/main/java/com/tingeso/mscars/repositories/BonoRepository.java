package com.tingeso.mscars.repositories;


import com.tingeso.mscars.entities.Bono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Long> {
    public List<Bono> findByMarca(String marca);

}