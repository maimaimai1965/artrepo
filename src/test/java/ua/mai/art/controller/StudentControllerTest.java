package ua.mai.art.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ua.mai.art.domain.Student;
import ua.telesens.plu.util.PluDateUtil;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

  @LocalServerPort
  int randomServerPort;

  @Test
  public void test_0010_insertStudentSuccess() throws URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();
    final String baseUrl = "http://localhost:" + randomServerPort + "/students/";
    URI uri = new URI(baseUrl);

    Student student = new Student("Иванов", "", PluDateUtil.getDate(1975, 12, 24));

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-COM-PERSIST", "true");

    HttpEntity<Student> request = new HttpEntity<>(student, headers);

    ResponseEntity<Long> result = restTemplate.postForEntity(uri, request, Long.class);

    //Verify request succeed
    Assertions.assertEquals(201, result.getStatusCodeValue());
  }

  @Test
  public void test_0020_insertStudentMissingHeader() throws URISyntaxException
  {
    RestTemplate restTemplate = new RestTemplate();
    final String baseUrl = "http://localhost:"+randomServerPort+"/students/";
    URI uri = new URI(baseUrl);
    Student student = new Student("Иванов", "", PluDateUtil.getDate(1975, 12, 24));

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<Student> request = new HttpEntity<>(student, headers);

    try
    {
      restTemplate.postForEntity(uri, request, String.class);
      Assertions.fail();
    }
    catch(HttpClientErrorException ex) {
      //Verify bad request and missing header
      Assertions.assertEquals(400, ex.getRawStatusCode());
      Assertions.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
    }
  }

  @Test
  public void test_0030_findAllSuccessWithHeaders() throws URISyntaxException
  {
    RestTemplate restTemplate = new RestTemplate();

    final String baseUrl = "http://localhost:"+randomServerPort+"/students/";
    URI uri = new URI(baseUrl);

    HttpHeaders headers = new HttpHeaders();
//    headers.set("X-COM-LOCATION", "USA");

    HttpEntity<Student> requestEntity = new HttpEntity<>(null, headers);

    try
    {
      restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
      Assertions.fail();
    }
    catch(HttpClientErrorException ex)
    {
      //Verify bad request and missing header
      Assertions.assertEquals(400, ex.getRawStatusCode());
      Assertions.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
    }
  }
}
