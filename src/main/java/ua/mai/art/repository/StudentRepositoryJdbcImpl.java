package ua.mai.art.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.mai.art.domain.Student;
import ua.mai.art.repository.callbacks.StudentRowMapper;

import java.util.List;

@Repository
public class StudentRepositoryJdbcImpl implements StudentRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

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
     return jdbcTemplate.queryForObject("SELECT * FROM test_mai_student WHERE id=?", new Object[]{id},
                                        //Можно использовать одну из них:
                                        new StudentRowMapper()
//                                        new BeanPropertyRowMapper<Student>(Student.class)
     );
  }

  @Override
  public int deleteById(long id) {
    return jdbcTemplate.update("DELETE FROM test_mai_student WHERE id=?", new Object[]{ id });
  }

  @Override
  public int insert(Student student) {
    return jdbcTemplate.update("INSERT INTO test_mai_student (id, name, passport_number, birth_date) " + "VALUES(?,  ?, ?, ?)",
                               new Object[]{student.getId(), student.getName(), student.getPassportNumber(),
                                            student.getBirthDate()});
  }

  public int update(Student student) {
    return jdbcTemplate.update("UPDATE test_mai_student " + " SET NAME = ?, passport_number = ?, birth_date = ? "
                                    + " WHERE id = ?",
                               new Object[]{student.getName(), student.getPassportNumber(), student.getBirthDate(),
                                            student.getId()});
  }

}
