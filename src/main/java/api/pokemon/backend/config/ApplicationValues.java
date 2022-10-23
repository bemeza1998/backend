package api.pokemon.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationValues {
  private final String mongoHost;
  private final String mongoDB;
  private final String mongoUsr;
  private final String mongoPwd;
  private final String mongoAut;

  public ApplicationValues(
      @Value("${api.pokemon.backend.mongo.host}") String mongoHost,
      @Value("${api.pokemon.backend.mongo.db}") String mongoDB,
      @Value("${api.pokemon.backend.mongo.usr}") String mongoUsr,
      @Value("${api.pokemon.backend.mongo.pwd}") String mongoPwd,
      @Value("${api.pokemon.backend.mongo.aut}") String mongoAut) {

    this.mongoHost = mongoHost;
    this.mongoDB = mongoDB;
    this.mongoUsr = mongoUsr;
    this.mongoPwd = mongoPwd;
    this.mongoAut = mongoAut;
  }
}
