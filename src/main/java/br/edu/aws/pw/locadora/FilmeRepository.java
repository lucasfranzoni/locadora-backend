package br.edu.aws.pw.locadora;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends CrudRepository<Filme, Long> {

    public List<Filme> findByNomeContainsIgnoreCase(String nome); 
}
