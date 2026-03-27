package com.example.FrankySabado.repositorios;

import com.example.FrankySabado.modelos.Docente;
import com.example.FrankySabado.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    Optional<Docente> findByUsuarioId(Integer usuarioId);
    Optional<Docente> findByUsuario(Usuario usuario);
}

