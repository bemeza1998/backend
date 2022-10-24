package api.pokemon.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.math.BigDecimal;
import java.util.Arrays;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
  private static final String WITH_AUTHENTICATION = "1";

  @Autowired private ApplicationValues appValues;

  @Override
  protected String getDatabaseName() {
    return appValues.getMongoDB();
  }

  @Override
  public MongoClient mongoClient() {
    if (WITH_AUTHENTICATION.equals(appValues.getMongoAut())) {
      return MongoClients.create(
          "mongodb://"
              + appValues.getMongoUsr()
              + ":"
              + appValues.getMongoPwd()
              + "@"
              + appValues.getMongoHost()
              + "/"
              + appValues.getMongoDB()
              + "&readPreference=primaryPreferred?ssl=true&authSource=admin&retryWrites=true&w=majority");
    }

    return MongoClients.create(
        "mongodb://" + appValues.getMongoHost() + "/" + appValues.getMongoDB());
  }

  @Override
  public boolean autoIndexCreation() {
    return true;
  }

  @Bean
  @Override
  public MappingMongoConverter mappingMongoConverter(
      MongoDatabaseFactory databaseFactory,
      MongoCustomConversions customConversions,
      MongoMappingContext mappingContext) {

    MappingMongoConverter converter =
        super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return converter;
  }

  /**
   * Inject a CustomConversions bean to overwrite the default mapping of BigDecimal.
   *
   * @return a new instance of CustomConversons
   */
  @Bean
  @Override
  public MongoCustomConversions customConversions() {
    Converter<Decimal128, BigDecimal> decimal128ToBigDecimal =
        new Converter<>() {
          @Override
          public BigDecimal convert(Decimal128 s) {
            return s == null ? null : s.bigDecimalValue();
          }
        };

    Converter<BigDecimal, Decimal128> bigDecimalToDecimal128 =
        new Converter<>() {
          @Override
          public Decimal128 convert(BigDecimal s) {
            return s == null ? null : new Decimal128(s);
          }
        };

    return new MongoCustomConversions(
        Arrays.asList(decimal128ToBigDecimal, bigDecimalToDecimal128));
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public ValidatingMongoEventListener validatingMongoEventListener() {
    return new ValidatingMongoEventListener(validator());
  }
}
