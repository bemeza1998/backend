package api.pokemon.backend.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "pokemon")
@TypeAlias("pokemon")
public class Pokemon {

  @Id private String id;

  @NotNull(message = "El nombre es obligatorio")
  private String nombre;

  @NotNull(message = "La descripcion es obligatoria")
  private String descripcion;

  private String tipo;

  private List<String> evolucion;

  private Double altura;

  private Double peso;

  private String foto;
}
