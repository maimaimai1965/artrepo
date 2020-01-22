package ua.mai.art.repository;

import ua.mai.art.domain.Student;

import java.util.List;

public interface StudentRepository {

  public List<Student> findAll();

  public Student findById(long id);

  public int insert(Student student);

  public int update(Student student);

  /**
   *
   * @param student
   * @return
   * @exception RuntimeException если не задан студент
   */
  public default int delete(Student student) {
    if (student == null) {
      throw new RuntimeException("Not defined student to delete!");
    }
    return deleteById(student.getId());
  };

  public int deleteById(long id);

}
