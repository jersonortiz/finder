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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerson
 */
@Entity
@Table(name = "tipo_titulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTitulo.findAll", query = "SELECT t FROM TipoTitulo t"),
    @NamedQuery(name = "TipoTitulo.findById", query = "SELECT t FROM TipoTitulo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoTitulo.findByTipo", query = "SELECT t FROM TipoTitulo t WHERE t.tipo = :tipo")})
public class TipoTitulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;

    public TipoTitulo() {
    }

    public TipoTitulo(Integer id) {
        this.id = id;
    }

    public TipoTitulo(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof TipoTitulo)) {
            return false;
        }
        TipoTitulo other = (TipoTitulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ufps.app.finder.entity.TipoTitulo[ id=" + id + " ]";
    }
    
}
