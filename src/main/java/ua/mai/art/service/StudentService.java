package ua.mai.art.service;

import org.springframework.stereotype.Service;
import ua.mai.art.aspect.StepLogJobAnnotation;
import ua.mai.art.domain.Student;

import java.util.List;


public interface StudentService {

//  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @StepLogJobAnnotation
  public List<Student> findAll();

  @StepLogJobAnnotation
  public Student findById(long id);

  @StepLogJobAnnotation
  public long insert(Student student);

  @StepLogJobAnnotation
  public long update(Student student);

  @StepLogJobAnnotation
  public long delete(Student student);

  @StepLogJobAnnotation
  public long deleteById(long id);

}
