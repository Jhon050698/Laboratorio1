package com.PPOOII.Laboratorio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PPOOII.Laboratorio.Entities.Usuario;
import com.PPOOII.Laboratorio.Entities.UsuarioId;

public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioId> {
}