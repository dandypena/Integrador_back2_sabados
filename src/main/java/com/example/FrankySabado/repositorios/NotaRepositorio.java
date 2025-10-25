package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepositorio extends JpaRepository<Nota, Integer> {
    List<Nota> findByEstudianteId(Integer estudianteId);
    List<Nota> findByMateriaId(Long materiaId);
}
