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
@Table(name = "oferta_profesional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OfertaProfesional.findAll", query = "SELECT o FROM OfertaProfesional o"),
    @NamedQuery(name = "OfertaProfesional.findById", query = "SELECT o FROM OfertaProfesional o WHERE o.id = :id"),
    @NamedQuery(name = "OfertaProfesional.findByEstado", query = "SELECT o FROM OfertaProfesional o WHERE o.estado = :estado")})
public class OfertaProfesional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @JoinColumn(name = "id_oferta_trabajo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OfertaTrabajo idOfertaTrabajo;
    @JoinColumn(name = "id_profesional", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profesional idProfesional;

    public OfertaProfesional() {
    }

    public OfertaProfesional(Integer id) {
        this.id = id;
    }

    public OfertaProfesional(Integer id, int estado) {
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public OfertaTrabajo getIdOfertaTrabajo() {
        return idOfertaTrabajo;
    }

    public void setIdOfertaTrabajo(OfertaTrabajo idOfertaTrabajo) {
        this.idOfertaTrabajo = idOfertaTrabajo;
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
        if (!(object instanceof OfertaProfesional)) {
            return false;
        }
        OfertaProfesional other = (OfertaProfesional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ufps.app.finder.entity.OfertaProfesional[ id=" + id + " ]";
    }
    
}
