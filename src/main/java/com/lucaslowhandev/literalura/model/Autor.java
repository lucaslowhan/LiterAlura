package com.lucaslowhandev.literalura.model;

import com.lucaslowhandev.literalura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Autor")
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    @OneToMany(mappedBy = "autor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Livro> livros = new LinkedHashSet<>();

    public Autor(){}

    public Autor(AutorDTO data){
        this.nome = data.nome();
        this.anoNascimento = data.anoNascimento();
        this.anoFalecimento = data.anoFalecimento();
    }

    public Autor(String nome, Integer anoNascimento, Integer anoFalecimento){
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "\n----------------------- AUTOR -----------------------" +"\n" +
                "Autor: " + nome + "\n" +
                "Ano de nascimento: " + (anoNascimento!=null? anoNascimento.toString() : "Desconhecido") + "\n" +
                "Ano de falecimento: " + (anoFalecimento!=null? anoFalecimento.toString() : "Desconhecido") + "\n" +
                "Livros: " + livros.stream().map(b -> b.getTitulo()).collect(Collectors.toSet()) + "\n" +
                "-----------------------------------------------------";
    }
}
