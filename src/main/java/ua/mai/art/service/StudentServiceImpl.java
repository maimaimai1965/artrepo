package ua.mai.art.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mai.art.aspect.StepLogJobAnnotation;
import ua.mai.art.domain.Student;
import ua.mai.art.repository.StudentRepository;

import java.util.List;

/**
 *
 */
@Service
public class StudentServiceImpl implements ua.mai.art.service.StudentService {

//  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private StudentRepository studentRepository;

  @Autowired
  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }


  @StepLogJobAnnotation
  @Override
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @StepLogJobAnnotation
  @Override
  public Student findById(long id) {
    return studentRepository.findById(id);
  }

  @StepLogJobAnnotation
  @Override
  public long insert(Student student) {
    return studentRepository.insert(student);
  }

  @StepLogJobAnnotation
  @Override
  public long update(Student student) {
    return studentRepository.update(student);
  }

  @StepLogJobAnnotation
  @Override
  public long delete(Student student) {
    return studentRepository.delete(student);
  }

  @StepLogJobAnnotation
  @Override
  public long deleteById(long id) {
    return studentRepository.deleteById(id);
  }

}
