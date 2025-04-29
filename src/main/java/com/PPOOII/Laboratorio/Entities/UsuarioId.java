package com.PPOOII.Laboratorio.Entities;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioId implements Serializable {

    private String login;
    private Long idpersona;

    // --- Constructores ---
    public UsuarioId() {}

    public UsuarioId(String login, Long idpersona) {
        this.login = login;
        this.idpersona = idpersona;
    }

    // --- Getters y Setters ---
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    // --- equals() y hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioId usuarioId = (UsuarioId) o;
        return Objects.equals(login, usuarioId.login) && 
               Objects.equals(idpersona, usuarioId.idpersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, idpersona);
    }
}