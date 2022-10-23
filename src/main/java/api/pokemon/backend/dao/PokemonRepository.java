package api.pokemon.backend.dao;

import api.pokemon.backend.model.Pokemon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {
  Optional<Pokemon> findByNombre(String nombre);

  List<Pokemon> findByNombreLike(String nombre);
}
