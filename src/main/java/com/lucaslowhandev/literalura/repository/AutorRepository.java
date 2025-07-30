package com.lucaslowhandev.literalura.repository;

import com.lucaslowhandev.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String autorNome);

    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento > :ano AND a.anoNascimento <= :ano")
    List<Autor>findAutoresPorAno(int ano);
}
