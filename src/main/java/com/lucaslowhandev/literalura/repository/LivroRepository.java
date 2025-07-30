package com.lucaslowhandev.literalura.repository;

import com.lucaslowhandev.literalura.model.Autor;
import com.lucaslowhandev.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LivroRepository extends JpaRepository<Livro,Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String tituloLivro);

    List<Livro> findByIdiomaContainingIgnoreCase(String idiomaCodigo);

    Set<Livro> findByAutor(Autor a);

    Optional<Livro> findByTituloIgnoreCase(String tituloLivro);
}
