package ua.mai.art.domain;

import java.time.LocalDate;
import java.util.Date;

public class Student {
  private Long id;
  private String name;
  private String passportNumber;
  private Date birthDate;

  public Student() {
		super();
	}

  public Student(Long id, String name, String passportNumber, Date birthDate) {
    super();
    this.id = id;
    this.name = name;
    this.passportNumber = passportNumber;
    this.birthDate = birthDate;
  }

  public Student(String name, String passportNumber, Date birthDate) {
    super();
    this.name = name;
    this.passportNumber = passportNumber;
    this.birthDate = birthDate;
  }

  public Long getId()         { return id;    }
  public void setId(Long id)  { this.id = id; }

  public String getName()           { return name;      }
  public void setName(String name)  { this.name = name; }

  public String getPassportNumber()                     { return passportNumber;                }
  public void setPassportNumber(String passportNumber)  { this.passportNumber = passportNumber; }

  public Date getBirthDate()                { return birthDate;           }
  public void setBirthDate(Date birthDate)  { this.birthDate = birthDate; }

  @Override
  public String toString() {
    return String.format("Student [id=%s, name=%s, passportNumber=%s, birthDate=%s]", id, name, passportNumber, birthDate);
  }

}
