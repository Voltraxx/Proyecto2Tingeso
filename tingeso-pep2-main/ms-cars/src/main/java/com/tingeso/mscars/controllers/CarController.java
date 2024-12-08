package com.tingeso.mscars.controllers;

import com.tingeso.mscars.entities.Car;
import com.tingeso.mscars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("/")
    public ResponseEntity<List<Car>> listCars() {
        List<Car> cars = carService.getCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carService.getCarByID(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/motor/{motor}")
    public ResponseEntity<List<Car>> listCarsByMotor(@PathVariable String motor) {
        List<Car> cars = carService.getCarsByMotor(motor);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Car>> listCarsByMarca(@PathVariable String marca) {
        List<Car> cars = carService.getCarsByMarca(marca);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Car>> listCarsByTipo(@PathVariable String tipo) {
        List<Car> cars = carService.getCarsByType(tipo);
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/")
    public ResponseEntity<Car> saveCar(@RequestBody Car car) {
        Car newCar = carService.saveCar(car);
        return ResponseEntity.ok(newCar);
    }

    @PutMapping("/")
    public ResponseEntity<Car> updateCar(@RequestBody Car car){
        Car carUpdated = carService.updateCar(car);
        return ResponseEntity.ok(carUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCarById(@PathVariable Long id) throws Exception {
        var isDeleted = carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
