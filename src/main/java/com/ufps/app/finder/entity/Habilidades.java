/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerson
 */
@Entity
@Table(name = "habilidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habilidades.findAll", query = "SELECT h FROM Habilidades h"),
    @NamedQuery(name = "Habilidades.findById", query = "SELECT h FROM Habilidades h WHERE h.id = :id"),
    @NamedQuery(name = "Habilidades.findByNombre", query = "SELECT h FROM Habilidades h WHERE h.nombre = :nombre")})
public class Habilidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "certificado")
    private String certificado;
    @JoinColumn(name = "id_profesional", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profesional idProfesional;

    public Habilidades() {
    }

    public Habilidades(Integer id) {
        this.id = id;
    }

    public Habilidades(Integer id, String nombre, String certificado) {
        this.id = id;
        this.nombre = nombre;
        this.certificado = certificado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public Profesional getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Profesional idProfesional) {
        this.idProfesional = idProfesional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habilidades)) {
            return false;
        }
        Habilidades other = (Habilidades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ufps.app.finder.entity.Habilidades[ id=" + id + " ]";
    }
    
}
