package com.tingeso.mscars.repositories;

import com.tingeso.mscars.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findByMarca(String marca);

    public List<Car> findByType(String type);

    public List<Car> findByMotor(String motor);

    @Query(value = "SELECT DISTINCT c.marca FROM cars c", nativeQuery = true)
    public List<String> findAllMarcas();
}
