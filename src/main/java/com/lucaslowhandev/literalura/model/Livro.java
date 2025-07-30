package com.lucaslowhandev.literalura.model;

import com.lucaslowhandev.literalura.dto.LivroDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "livro")
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private String idioma;
    private Integer numeroDeDownloads;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro(){}

    public Livro(LivroDTO data){
        this.titulo = data.titulo();
        if (data.autores() != null && !data.autores().isEmpty()) {
            this.autor = new Autor(data.autores().get(0));
        } else {
            this.autor = null;
            System.out.println("Aviso: Autor não informado para o livro " + data.titulo());
        }

        if (data.idiomas() != null && !data.idiomas().isEmpty()) {
            this.idioma = data.idiomas().getFirst();
        } else {
            this.idioma = null; // Ou null
            System.out.println("Aviso: Idioma não informado para o livro " + data.titulo());
        }
        this.numeroDeDownloads = data.numeroDeDownloads();
    }

    public Livro(String titulo, Autor autor, String idioma, Integer numeroDeDownloads){
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDeDownloads = numeroDeDownloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDownloads() {
        return numeroDeDownloads;
    }

    public void setNumeroDeDownloads(Integer numeroDeDownloads) {
        this.numeroDeDownloads = numeroDeDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n----------------------- LIVRO -----------------------" + "\n" +
                "Título: "+ titulo + "\n"+
                "Autor: " + autor.getNome() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: "+ numeroDeDownloads + "\n" +
                "-----------------------------------------------------" + "\n";
    }
}
