package com.PPOOII.Laboratorio.Services.Interfaces;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.PPOOII.Laboratorio.Entities.Persona;

public interface IPersonaService {

	//METODOS CRUD
	boolean guardar(Persona persona);
	
	boolean actualizar(Persona persona);
	
	boolean eliminar(int id);
	
	List<Persona> consultarPersona(Pageable pageable);
	

	//LISTA DE PERSONA POR ID
	Persona findById(int id);
	
	//LISTA DE PERSONA POR PRIMER NOMBRE
	List<Persona> findByNombre(String pnombre);
	
	//LISTA DE PERSONA POR EDAD
	List<Persona> findByEdad(int edad);
}
