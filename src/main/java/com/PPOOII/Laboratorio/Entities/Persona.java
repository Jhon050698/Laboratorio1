package com.PPOOII.Laboratorio.Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import jakarta.persistence.*;

@Entity
@Table(name = "Persona", schema = "PPOOII")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Campos obligatorios (según PDF) ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersona")  // Nombre exacto para relación con Usuario
    private Long idpersona;

    @Column(name = "pnombre", nullable = false)
    private String pnombre;

    @Column(name = "papellido", nullable = false)  // Requerido para generar login
    private String papellido;

    @Column(name = "fechanacimiento", nullable = false)  // Base para cálculo de edades
    private LocalDate fechanacimiento;

    @Column(name = "edad")  // Se calcula automáticamente
    private Integer edad;

    @Column(name = "edadclinica", length = 50)  // Formato: "X años Y meses Z días"
    private String edadclinica;

    // --- Relación 1:1 con Usuario (requerimiento PDF) ---
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Usuario usuario;

    // --- Constructores ---
    public Persona() {}

    // Constructor para pruebas (sin edad/edadclinica, se calculan automáticamente)
    public Persona(String pnombre, String papellido, LocalDate fechanacimiento) {
        this.pnombre = pnombre;
        this.papellido = papellido;
        this.fechanacimiento = fechanacimiento;
    }

    // --- Método para cálculo automático de edades (requerimiento PDF) ---
    @PrePersist
    @PreUpdate
    public void calcularEdades() {
        if (this.fechanacimiento != null) {
            Period periodo = Period.between(this.fechanacimiento, LocalDate.now());
            this.edad = periodo.getYears();
            this.edadclinica = String.format(
                "%d años %d meses %d días",
                periodo.getYears(),
                periodo.getMonths(),
                periodo.getDays()
            );
        }
    }

    // --- Getters y Setters ---
    public Long getIdpersona() {
        return idpersona;
    }

    public String getPnombre() {
        return pnombre;
    }

    public void setPnombre(String pnombre) {
        this.pnombre = pnombre;
    }

    public String getPapellido() {
        return papellido;
    }

    public void setPapellido(String papellido) {
        this.papellido = papellido;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
        // Recalcula edades si se modifica la fecha
        this.calcularEdades();
    }

    public Integer getEdad() {
        return edad;
    }

    // No hay setter para 'edad' (se calcula automáticamente)

    public String getEdadclinica() {
        return edadclinica;
    }

    // No hay setter para 'edadclinica' (se calcula automáticamente)

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // --- toString() ---
    @Override
    public String toString() {
        return "Persona [id=" + idpersona + 
               ", pnombre=" + pnombre + 
               ", papellido=" + papellido + 
               ", edad=" + edad + 
               ", edadclinica=" + edadclinica + "]";
    }
}