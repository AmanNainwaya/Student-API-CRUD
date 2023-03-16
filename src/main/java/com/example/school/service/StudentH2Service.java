package com.example.school.service;
import com.example.school.repository.StudentRepository;
import com.example.school.model.Student;
import com.example.school.model.StudentRowMapper;
import java.util.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class StudentH2Service implements StudentRepository{
    @Autowired
    private JdbcTemplate db;

    // public int uniqueId = 20;
     
    @Override
    public ArrayList<Student> getStudent(){
        List<Student> studentList = db.query("select * from student", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentList);
        return students;
    }

    @Override
    public Student getStudentById(int studentId){
        try{
            Student student = db.queryForObject("select * from student where studentId = ?", new StudentRowMapper(), studentId);
            return student;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student){
        db.update("insert into student (studentName, gender, standard) values (?, ?, ?)", student.getStudentName(), student.getGender(), student.getStandard());
        Student newStudent = db.queryForObject("select * from student where studentName = ? and gender = ? and standard = ?", new StudentRowMapper(),
        student.getStudentName(), student.getGender(), student.getStandard());
        // uniqueId += 1;
        return newStudent;
    }

    @Override
    public String addMultipleStudent(ArrayList<Student> student){
        for(int i=0; i<student.size(); i++){
            db.update("insert into student (studentName, gender, standard) values (?, ?, ?)", student.get(i).getStudentName(), student.get(i).getGender(), student.get(i).getStandard());
            // uniqueId += 1;
        }
        String str  = String.format("Successfully added %d students", student.size());
        return str;
    }

    @Override
    public Student updateStudent(int studentId, Student student){
        
        if(student.getStudentName() != null){
            db.update("update student set studentName = ? where studentId = ?", student.getStudentName(), studentId);
        }
        if(student.getGender() != null){
            db.update("update student set gender = ? where studentId = ?", student.getGender(), studentId);
        }
        if(student.getStandard() != 0){
            db.update("update student set standard = ? where studentId = ?", student.getStandard(), studentId);
        }
        return getStudentById(studentId);
    }

    @Override
    public void deleteStudent(int studentId){
        db.update("delete from student where studentId = ?", studentId);
    }

    
}