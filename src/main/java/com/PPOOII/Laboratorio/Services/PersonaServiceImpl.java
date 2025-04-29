package com.PPOOII.Laboratorio.Services;

import com.PPOOII.Laboratorio.Entities.Persona;
import com.PPOOII.Laboratorio.Entities.Usuario;
import com.PPOOII.Laboratorio.Repository.PersonaRepository;
import com.PPOOII.Laboratorio.Repository.UsuarioRepository;
import org.apache.logging.log4j.Logger; // Importación añadida
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//[Resto de imports...]

@Service
public class PersonaServiceImpl implements PersonaService {

 // [Inyecciones de dependencias...]

 @Override
 @Transactional
 public boolean actualizar(Persona persona) {
     try {
         if (persona == null || persona.getIdpersona() == null) {
             logger.error("ERROR ACTUALIZAR: Persona o ID nulo");
             return false;
         }

         return personaRepository.findById(persona.getIdpersona())
                 .map(p -> {
                     p.setPnombre(persona.getPnombre());
                     p.setPapellido(persona.getPapellido());
                     p.setFechanacimiento(persona.getFechanacimiento());
                     if (p.getFechanacimiento() != null) {
                         p.calcularEdades();
                     }
                     personaRepository.save(p);
                     return true;
                 })
                 .orElse(false);
     } catch (Exception e) {
         logger.error("ERROR ACTUALIZAR_PERSONA: ", e);
         return false;
     }
 }

 @Override
 @Transactional
 public boolean eliminar(Long id) {
     try {
         if (id == null) return false;
         
         Optional<Persona> personaOpt = personaRepository.findById(id);
         if (!personaOpt.isPresent()) return false;
         
         Persona persona = personaOpt.get();
         if (persona.getUsuario() != null) {
             usuarioRepository.delete(persona.getUsuario());
         }
         personaRepository.delete(persona);
         return true;
     } catch (Exception e) {
         logger.error("ERROR ELIMINAR_PERSONA: ", e);
         return false;
     }
 }

 @Override
 @Transactional(readOnly = true)
 public List<Persona> consultarPersona(Pageable pageable) {
     try {
         return pageable == null ? 
                personaRepository.findAll() : 
                personaRepository.findAll(pageable).getContent();
     } catch (Exception e) {
         logger.error("ERROR CONSULTAR_PERSONAS: ", e);
         return List.of();
     }
 }

 @Override
 @Transactional(readOnly = true)
 public Persona findById(Long id) {
     try {
         if (id == null) return null;
         return personaRepository.findById(id).orElse(null);
     } catch (Exception e) {
         logger.error("ERROR BUSCAR_POR_ID: ", e);
         return null;
     }
 }

 @Override
 @Transactional(readOnly = true)
 public List<Persona> findByNombre(String pnombre) {
     try {
         if (pnombre == null || pnombre.trim().isEmpty()) {
             return personaRepository.findAll();
         }
         return personaRepository.findByPnombreContainingIgnoreCase(pnombre);
     } catch (Exception e) {
         logger.error("ERROR BUSCAR_POR_NOMBRE: ", e);
         return List.of();
     }
 }

 @Override
 @Transactional(readOnly = true)
 public List<Persona> findByEdad(int edad) {
     try {
         return personaRepository.findAll().stream()
             .filter(p -> p.getEdad() != null && p.getEdad() == edad)
             .collect(Collectors.toList());
     } catch (Exception e) {
         logger.error("ERROR BUSCAR_POR_EDAD: ", e);
         return List.of();
     }
 }
}