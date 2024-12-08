package com.tingeso.mscars.services;

import com.tingeso.mscars.entities.Bono;
import com.tingeso.mscars.repositories.BonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonoService {
    @Autowired
    BonoRepository bonoRepository;

    public ArrayList<Bono> getBonos(){
        return (ArrayList<Bono>) bonoRepository.findAll();
    }

    public Bono saveBono(Bono bono)  {
        return bonoRepository.save(bono);
    }

    public Bono getBonoByID(Long id) {
        return bonoRepository.findById(id).orElse(null);
    }

    public List<Bono> getBonosByMarca(String marca) {
        return bonoRepository.findByMarca(marca);
    }

    public Bono updateBono(Bono bono) {
        return bonoRepository.save(bono);
    }

    public boolean deleteBono(Long id) throws Exception {
        try{
            bonoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}