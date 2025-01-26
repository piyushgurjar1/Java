package com.example.demo.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "Courses")
public class Course {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String firstName;
   private Integer duration;

   public String getfirstName() {
      return firstName;
   }

   public void setfirstName(String b) {
      firstName = b;
   }

   public Integer getduration() {
      return duration;
   }

   public void setduration(Integer getduration) {
      duration = getduration;
   }
}
