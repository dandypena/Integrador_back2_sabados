package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
}
