package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.model.Student;
import com.example.unittesting.app.repository.StudentRepository;
import com.example.unittesting.app.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentSrvImpl implements StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }
}
