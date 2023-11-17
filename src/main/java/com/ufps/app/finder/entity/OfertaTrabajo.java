/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerson
 */
@Entity
@Table(name = "oferta_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OfertaTrabajo.findAll", query = "SELECT o FROM OfertaTrabajo o"),
    @NamedQuery(name = "OfertaTrabajo.findById", query = "SELECT o FROM OfertaTrabajo o WHERE o.id = :id"),
    @NamedQuery(name = "OfertaTrabajo.findByTitulo", query = "SELECT o FROM OfertaTrabajo o WHERE o.titulo = :titulo"),
    @NamedQuery(name = "OfertaTrabajo.findBySalario", query = "SELECT o FROM OfertaTrabajo o WHERE o.salario = :salario"),
    @NamedQuery(name = "OfertaTrabajo.findByJornada", query = "SELECT o FROM OfertaTrabajo o WHERE o.jornada = :jornada"),
    @NamedQuery(name = "OfertaTrabajo.findByFecha", query = "SELECT o FROM OfertaTrabajo o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "OfertaTrabajo.findByExperiencia", query = "SELECT o FROM OfertaTrabajo o WHERE o.experiencia = :experiencia"),
    @NamedQuery(name = "OfertaTrabajo.findByEstado", query = "SELECT o FROM OfertaTrabajo o WHERE o.estado = :estado")})
public class OfertaTrabajo implements Serializable {

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
    @Lob
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "salario")
    private String salario;
    @Basic(optional = false)
    @Column(name = "jornada")
    private int jornada;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "experiencia")
    private int experiencia;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa idEmpresa;
    @JoinColumn(name = "id_sector", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sector idSector;
    @JoinColumn(name = "id_tipo_contrato", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoContrato idTipoContrato;

    public OfertaTrabajo() {
    }

    public OfertaTrabajo(Integer id) {
        this.id = id;
    }

    public OfertaTrabajo(Integer id, String titulo, String contenido, String salario, int jornada, Date fecha, int experiencia, boolean estado) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.salario = salario;
        this.jornada = jornada;
        this.fecha = fecha;
        this.experiencia = experiencia;
        this.estado = estado;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Sector getIdSector() {
        return idSector;
    }

    public void setIdSector(Sector idSector) {
        this.idSector = idSector;
    }

    public TipoContrato getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(TipoContrato idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
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
        if (!(object instanceof OfertaTrabajo)) {
            return false;
        }
        OfertaTrabajo other = (OfertaTrabajo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ufps.app.finder.entity.OfertaTrabajo[ id=" + id + " ]";
    }
    
}
