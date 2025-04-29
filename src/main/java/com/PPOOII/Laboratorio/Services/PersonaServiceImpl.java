package com.PPOOII.Laboratorio.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.PPOOII.Laboratorio.Entities.Persona;
import com.PPOOII.Laboratorio.Entities.Usuario;
import com.PPOOII.Laboratorio.Repository.PersonaRepository;
import com.PPOOII.Laboratorio.Repository.UsuarioRepository;

@Service("PersonaService")
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;  // Inyección corregida

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(PersonaServiceImpl.class);

    @Override
    public boolean guardar(Persona persona) {
        try {
            if (persona == null) {
                logger.error("ERROR AGREGAR_PERSONA: LA PERSONA ES NULA!");
                return false;
            }
            
          
            persona = personaRepository.save(persona);

            // Genera login según PDF: pnombre + primera letra papellido + idpersona
            String login = persona.getPnombre() + 
                         persona.getPapellido().charAt(0) + 
                         persona.getIdpersona();

            // Genera password aleatorio
            String password = UUID.randomUUID().toString().substring(0, 8);

            // Crea y guarda Usuario
            Usuario usuario = new Usuario(login, persona, password);
            usuarioRepository.save(usuario);

            return true;
        } catch (Exception e) {
            logger.error("ERROR AGREGAR_PERSONA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Persona persona) {
        try {
            if (persona == null || persona.getIdpersona() == null) {
                logger.error("ERROR EDITAR_PERSONA: LA PERSONA ES NULA O EL ID ES 0!");
                return false;
            }
            
            Optional<Persona> personaExistente = personaRepository.findById(persona.getIdpersona());
            if (personaExistente.isPresent()) {
                Persona p = personaExistente.get();
                p.setPnombre(persona.getPnombre());
                p.setPapellido(persona.getPapellido());
                p.setFechanacimiento(persona.getFechanacimiento());
                personaRepository.save(p);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("ERROR EDITAR_PERSONA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            Optional<Persona> persona = personaRepository.findById((long) id);  // Cast a Long
            if (persona.isPresent()) {
                personaRepository.delete(persona.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("ERROR ELIMINAR_PERSONA: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Persona> consultarPersona(Pageable pageable) {
        return personaRepository.findAll(pageable).getContent();
    }

    @Override
    public Persona findById(int id) {
        Optional<Persona> persona = personaRepository.findById((long) id);  // Cast a Long
        return persona.orElse(null);
    }

    @Override
    public List<Persona> findByNombre(String pnombre) {
        return personaRepository.findByPnombre(pnombre);
    }

    @Override
    public List<Persona> findByEdad(int edad) {
        return personaRepository.findByEdad(edad);
    }
}
