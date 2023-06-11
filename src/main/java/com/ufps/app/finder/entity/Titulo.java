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
@Table(name = "titulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Titulo.findAll", query = "SELECT t FROM Titulo t"),
    @NamedQuery(name = "Titulo.findById", query = "SELECT t FROM Titulo t WHERE t.id = :id"),
    @NamedQuery(name = "Titulo.findByTitulo", query = "SELECT t FROM Titulo t WHERE t.titulo = :titulo"),
    @NamedQuery(name = "Titulo.findByInstitucion", query = "SELECT t FROM Titulo t WHERE t.institucion = :institucion")})
public class Titulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "institucion")
    private String institucion;
    @Basic(optional = false)
    @Lob
    @Column(name = "certificado")
    private String certificado;
    @JoinColumn(name = "id_profesional", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profesional idProfesional;
    @JoinColumn(name = "id_tipo_titulo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoTitulo idTipoTitulo;

    public Titulo() {
    }

    public Titulo(Integer id) {
        this.id = id;
    }

    public Titulo(Integer id, String titulo, String institucion, String certificado) {
        this.id = id;
        this.titulo = titulo;
        this.institucion = institucion;
        this.certificado = certificado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
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

    public TipoTitulo getIdTipoTitulo() {
        return idTipoTitulo;
    }

    public void setIdTipoTitulo(TipoTitulo idTipoTitulo) {
        this.idTipoTitulo = idTipoTitulo;
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
        if (!(object instanceof Titulo)) {
            return false;
        }
        Titulo other = (Titulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ufps.app.finder.entity.Titulo[ id=" + id + " ]";
    }
    
}
