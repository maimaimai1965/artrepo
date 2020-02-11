package ua.mai.art.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.mai.art.domain.Student;
import ua.mai.art.repository.callbacks.StudentRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepositoryJdbcImpl implements StudentRepository {

//  private Logger logger = LoggerFactory.getLogger(this.getClass());

  NamedParameterJdbcTemplate jdbcTemplate;

  //  @Autowired
  public StudentRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public List<Student> findAll() {
    return jdbcTemplate.query("SELECT * FROM test_mai_student",
                              //Можно использовать одну из них:
                              new StudentRowMapper()
//                              new BeanPropertyRowMapper<Student>(Student.class)
    );
  }

  @Override
  public Student findById(long id) {
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", id);
    return jdbcTemplate.queryForObject("SELECT * FROM test_mai_student WHERE id=:id", namedParameters,
                                       //Можно использовать одну из них:
                                       new StudentRowMapper()
//                                        new BeanPropertyRowMapper<Student>(Student.class)
     );
  }

  @Override
  public int deleteById(long id) {
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", id);
    return jdbcTemplate.update("DELETE FROM test_mai_student WHERE id=:id", namedParameters);
  }

  @Override
  public int insert(Student student) {
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", student.getId());
    namedParameters.put("name", student.getName());
    namedParameters.put("passport_number", student.getPassportNumber());
    namedParameters.put("birth_date", student.getBirthDate());
    return jdbcTemplate.update("INSERT INTO test_mai_student (id, name, passport_number, birth_date) "
                                    + "VALUES(:id,  :name, :passport_number, :birth_date)", namedParameters);
  }

  @Override
  public int update(Student student) {
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", student.getId());
    namedParameters.put("name", student.getName());
    namedParameters.put("passport_number", student.getPassportNumber());
    namedParameters.put("birth_date", student.getBirthDate());
    return jdbcTemplate.update("UPDATE test_mai_student " + " SET NAME = :name, passport_number = :passport_number, birth_date = :birth_date"
                                    + " WHERE id = :id", namedParameters);
  }

  @Override
  public int delete(Student student) {
    return StudentRepository.super.delete(student);
  }

}
