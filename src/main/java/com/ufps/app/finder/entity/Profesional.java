/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jerson
 */
@Entity
@Table(name = "profesional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesional.findAll", query = "SELECT p FROM Profesional p"),
    @NamedQuery(name = "Profesional.findById", query = "SELECT p FROM Profesional p WHERE p.id = :id"),
    @NamedQuery(name = "Profesional.findByEstado", query = "SELECT p FROM Profesional p WHERE p.estado = :estado"),
    @NamedQuery(name = "Profesional.findByCiudad", query = "SELECT p FROM Profesional p WHERE p.ciudad = :ciudad")})
public class Profesional implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesional")
    private List<OfertaProfesional> ofertaProfesionalList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesional")
    private List<Titulo> tituloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesional")
    private List<Competencia> competenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesional")
    private List<Habilidades> habilidadesList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesional")
    private List<Servicio> servicioList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;
    @Column(name = "ciudad")
    private String ciudad;
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario idPersona;

    public Profesional() {
    }

    public Profesional(Integer id) {
        this.id = id;
    }

    public Profesional(Integer id, boolean estado) {
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Usuario getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Usuario idPersona) {
        this.idPersona = idPersona;
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
        if (!(object instanceof Profesional)) {
            return false;
        }
        Profesional other = (Profesional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ufps.app.finder.entity.Profesional[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Servicio> getServicioList() {
        return servicioList;
    }

    public void setServicioList(List<Servicio> servicioList) {
        this.servicioList = servicioList;
    }

    @XmlTransient
    public List<Titulo> getTituloList() {
        return tituloList;
    }

    public void setTituloList(List<Titulo> tituloList) {
        this.tituloList = tituloList;
    }

    @XmlTransient
    public List<Competencia> getCompetenciaList() {
        return competenciaList;
    }

    public void setCompetenciaList(List<Competencia> competenciaList) {
        this.competenciaList = competenciaList;
    }

    @XmlTransient
    public List<Habilidades> getHabilidadesList() {
        return habilidadesList;
    }

    public void setHabilidadesList(List<Habilidades> habilidadesList) {
        this.habilidadesList = habilidadesList;
    }

    @XmlTransient
    public List<OfertaProfesional> getOfertaProfesionalList() {
        return ofertaProfesionalList;
    }

    public void setOfertaProfesionalList(List<OfertaProfesional> ofertaProfesionalList) {
        this.ofertaProfesionalList = ofertaProfesionalList;
    }

}
