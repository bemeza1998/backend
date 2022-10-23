package api.pokemon.backend.service;

import api.pokemon.backend.dao.PokemonRepository;
import api.pokemon.backend.model.Pokemon;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {

  private final PokemonRepository pokemonRepository;

  public List<Pokemon> listarPokemon() {
    return this.pokemonRepository.findAll();
  }

  public Pokemon buscarPorNombre(String nombre) {
    Optional<Pokemon> pokemonOpt = this.pokemonRepository.findByNombre(nombre);
    return pokemonOpt.orElseThrow(
        () -> new RuntimeException("No se encontrño el pokemon con nombre " + nombre));
  }

  public List<Pokemon> buscarPorTermino(String nombre) {
    return this.pokemonRepository.findByNombreLike(nombre);
  }
}
