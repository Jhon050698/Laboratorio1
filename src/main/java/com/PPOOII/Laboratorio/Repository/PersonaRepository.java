package com.PPOOII.Laboratorio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PPOOII.Laboratorio.Entities.Persona;
import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findById(Long id);  // Long con mayúscula
    List<Persona> findByPnombre(String pnombre);  // Método para buscar por nombre
    List<Persona> findByEdad(Integer edad);  // Usa Integer, no int
}