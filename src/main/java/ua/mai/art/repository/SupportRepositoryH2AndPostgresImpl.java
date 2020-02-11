package ua.mai.art.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.telesens.plu.log.StepLogDb;

import java.util.Map;


/**
 * Реализация API репозитария (хранилища) вспомогательных функций для БД H2 и PostgreSQL.
 */
@Repository
@Profile({"db-h2-in-memory", "db-h2-in-file", "db-postgres"})
public class SupportRepositoryH2AndPostgresImpl implements SupportRepository {

//  private Logger logger = LoggerFactory.getLogger(this.getClass());

  NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public SupportRepositoryH2AndPostgresImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public void checkConnection() {
//    StepLogDb stepLogDb = new StepLogDb(logger,"SupportRepositoryH2AndPostgresImpl.StepLogDb()");
//    stepLogDb.startStep("Check DB connection");
//    try {
     String str = jdbcTemplate.queryForObject("SELECT 'CONNECTED'", (Map<String,Object>)null, String.class);
//      stepLogDb.finishStepOk("DB connection OK!");
//    }
//    catch (Exception e) {
//      stepLogDb.finishStepErr(e);
//      throw e;
//    }
  }

}
