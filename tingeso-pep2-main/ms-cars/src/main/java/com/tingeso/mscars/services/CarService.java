package com.tingeso.mscars.services;

import com.tingeso.mscars.entities.Car;
import com.tingeso.mscars.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public ArrayList<Car> getCars(){
        return (ArrayList<Car>) carRepository.findAll();
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCarByID(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> getCarsByMarca(String marca) {
        return carRepository.findByMarca(marca);
    }

    public List<Car> getCarsByMotor(String motor) {
        return carRepository.findByMotor(motor);
    }

    public List<Car> getCarsByType(String type) {
        return carRepository.findByType(type);
    }

    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    public List<String> getAllCarBrands() {
        List<String> brands = new ArrayList<>();
        List<Car> cars = getCars();
        for (int i = 0;i < cars.size();i++) {
            if (!brands.contains(cars.get(i).getMarca())) {
                brands.add(cars.get(i).getMarca());
            }
        }
        return brands;
    }

    public boolean deleteCar(Long id) throws Exception {
        try{
            carRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
