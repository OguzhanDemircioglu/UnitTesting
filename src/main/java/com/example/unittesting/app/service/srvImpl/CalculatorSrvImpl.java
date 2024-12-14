package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.repository.CalculatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatorSrvImpl{
    private final CalculatorRepository repository;

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0)
            throw new ArithmeticException("Divide by zero");

        return a / b;
    }
}
