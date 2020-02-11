package ua.mai.art.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.mai.art.domain.Student;
import ua.mai.art.repository.StudentRepository;
import ua.mai.art.service.StudentService;

import java.util.List;

@RequestMapping("/api/students")
@RestController
public class StudentController {

  private StudentService studentService;

  @Autowired
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }


  public List<Student> findAll() {
    return studentService.findAll();
  }

  @GetMapping("{id}")
  public Student findById(@PathVariable(required=true) long id) {
    return studentService.findById(id);
  }

  @PostMapping()
  public long insert(@RequestBody Student student) {
    return studentService.insert(student);
  }

  public long update(Student student) {
    return studentService.update(student);
  }

  public long delete(Student student) {
    return studentService.delete(student);
  }

  @DeleteMapping("{id}")
  public long deleteById(@PathVariable(required=true) long id) {
    return studentService.deleteById(id);
  }

}

