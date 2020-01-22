package ua.mai.art.repository.callbacks;

import org.springframework.jdbc.core.RowMapper;
import ua.mai.art.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
  @Override
  public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
    Student student = new Student();
    student.setId(rs.getLong("id"));
    student.setName(rs.getString("name"));
    student.setPassportNumber(rs.getString("passport_number"));
    student.setBirthDate(rs.getDate("birth_date"));
    return student;
  }
}
