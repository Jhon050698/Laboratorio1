package com.PPOOII.Laboratorio.Entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario", schema = "PPOOII")
@IdClass(UsuarioId.class)  
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Clave primaria compuesta (requerimiento PDF) ---
    @Id
    @Column(name = "login", updatable = false, length = 50)  // No se puede modificar
    private String login;

    @Id
    @OneToOne
    @JoinColumn(name = "idpersona", referencedColumnName = "idpersona")
    private Persona persona;

    // --- Campos ---
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    // --- Constructores ---
    public Usuario() {}

    public Usuario(String login, Persona persona, String password) {
        this.login = login;
        this.persona = persona;
        this.password = password;
    }

    // --- Getters y Setters ---
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- toString() ---
    @Override
    public String toString() {
        return "Usuario [login=" + login + ", password=" + password + "]";
    }
}