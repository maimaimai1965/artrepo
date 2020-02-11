package ua.mai.art.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import ua.mai.art.domain.Student;
import ua.mai.art.service.StudentService;
import ua.telesens.plu.util.PluDateUtil;

public class StudentControllerUnitTest {

//  private final List<Student> students = new ArrayList<>();
//
//  @BeforeEach
//  public void initStudent () {
//    Student student = new Student(25L, "John Mayer", "XXX12345", PluDateUtil.getDate(1963, 7, 28));
//    students.add(student);
//  }

  @Test
  public void test_findAll() throws Exception {
    Student student = new Student(25L, "John Mayer", "XXX12345", PluDateUtil.getDate(1963, 7, 28));
    List<Student> students = new ArrayList<>();
    students.add(student);

    StudentService studentService = mock(StudentService.class);
    when(studentService.findAll()).thenReturn(students);

    StudentController studentController = new StudentController(studentService);
//    ReflectionTestUtils.setField(studentController, "studentService", studentService);

    ExtendedModelMap uiModel = new ExtendedModelMap();

    uiModel.addAttribute("/api/students", studentController.findAll());

    Object model = uiModel.get("/api/students");
    List<Student> modelStudents = (List<Student>)model;
    assertEquals(1, modelStudents.size());
  }

  @Test
  public void test_insert() {
    List<Student> studentsList = new ArrayList<>();
    final Student newStudent  =  new Student(25L, "John Mayer", "XXX12345", PluDateUtil.getDate(1963, 7, 28));

    StudentService studentService = mock(StudentService.class);
    when(studentService.insert(newStudent))
                       .thenAnswer(new Answer<Long>() {
                                         public Long answer(InvocationOnMock invocation) throws Throwable {
                                           studentsList.add(newStudent);
                                           return newStudent.getId();
                                         }
                                       });

    StudentController studentController = new StudentController(studentService);
//    ReflectionTestUtils.setField(studentController, "studentService", studentService);
    long id = studentController.insert(newStudent);

    assertEquals(25L, id);
  }

}
