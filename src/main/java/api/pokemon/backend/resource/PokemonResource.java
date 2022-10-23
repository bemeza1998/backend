package api.pokemon.backend.resource;

import api.pokemon.backend.dao.PokemonRepository;
import api.pokemon.backend.model.Pokemon;
import api.pokemon.backend.service.PokemonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/pokemon")
@RequiredArgsConstructor
public class PokemonResource {

  private final PokemonService pokemonService;
  private final PokemonRepository pokemonRepository;

  @GetMapping
  public ResponseEntity<List<Pokemon>> obtenerPokemon() {
    return ResponseEntity.ok(this.pokemonService.listarPokemon());
  }

  @PostMapping
  public ResponseEntity<Pokemon> crear(@RequestBody Pokemon pokemon) {
    this.pokemonRepository.save(pokemon);
    return ResponseEntity.ok().build();
  }

  @GetMapping(path = "/{nombre}")
  public ResponseEntity<Pokemon> buscarPorNombre(@PathVariable String nombre) {
    return ResponseEntity.ok(this.pokemonService.buscarPorNombre(nombre));
  }

  @GetMapping(path = "/termino/{termino}")
  public ResponseEntity<List<Pokemon>> buscarPorTermino(@PathVariable String termino) {
    return ResponseEntity.ok(this.pokemonService.buscarPorTermino(termino));
  }
}
