package br.edu.aws.pw.locadora;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin 
@RequestMapping("/filmes")
public class FilmeController {
    
    @Autowired
    private FilmeRepository repository;


    @GetMapping
    public ResponseEntity<List<Filme>> consultar(@RequestParam String nome) {
        List<Filme> filmes = repository.findByNomeContainsIgnoreCase(nome);
        return ResponseEntity.ok().body(filmes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Filme> detalhar(@PathVariable Long id) {
        Optional<Filme> filme = repository.findById(id);
        if(filme.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(filme.get());
    }

    @PostMapping
    public ResponseEntity<Filme> salvar(@RequestBody Filme filme) {
        filme.setQuantidadeDisponivel(filme.getQuantidadeTotal());
        Filme filmeSalvo = repository.save(filme);
        return ResponseEntity.ok(filmeSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> alterar(@PathVariable Long id, @RequestBody Filme filme) {
        if(!id.equals(filme.getId())) {
            return ResponseEntity.badRequest().build();
        }
        else if(repository.existsById(id)) {
            Filme filmeAlterado = repository.save(filme);
            return ResponseEntity.ok().body(filmeAlterado);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Filme> excluir(@PathVariable Long id) {
        Optional<Filme> optionalfilme = repository.findById(id);
        if(optionalfilme.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
