package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StudentSrvImplTest {

    @InjectMocks
    StudentSrvImpl srv;

    @Mock
    StudentRepository studentRepository;

    @Test
    void getAllStudents() {

        assertTrue(this.srv.getAllStudents().isEmpty());
    }
}