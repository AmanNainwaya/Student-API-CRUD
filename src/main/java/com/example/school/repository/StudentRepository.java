package com.example.school.repository;
import java.util.*;
import com.example.school.model.Student;

public interface StudentRepository{
    ArrayList<Student> getStudent();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    String addMultipleStudent(ArrayList<Student> student);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);
}