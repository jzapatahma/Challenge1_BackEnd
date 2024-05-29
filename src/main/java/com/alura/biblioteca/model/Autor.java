package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tblAutores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    @JsonProperty("name")
    private String name;
    @JsonProperty("birth_year")
    private String birth_year;
    @JsonProperty("death_year")
    private String death_year;

    public Autor(String name, String birth_year, String death_year) {
        this.name = name;
        this.birth_year = birth_year;
        this.death_year = death_year;
    }
    public Autor() {    }

    public Long getId() { return Id; }
    public void setId(Long id) { Id = id;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBirth_year() {
        return birth_year;
    }
    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }
    public String getDeath_year() {
        return death_year;
    }
    public void setDeath_year(String death_year) {
        this.death_year = death_year;
    }
    public Autor unwrap(Object o) {return null;}

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", death_year='" + death_year + '\'' ;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Autor autor = (Autor) o;
//        return Objects.equals(name, autor.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }

}
