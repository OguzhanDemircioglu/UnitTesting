package com.example.unittesting.app.parametic;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ParameterizedTesting {

    static Stream<Arguments> getParameters(){
        return Stream.of(
                Arguments.arguments(0,1),
                Arguments.arguments(1,1),
                Arguments.arguments(2,2),
                Arguments.arguments(3,6),
                Arguments.arguments(4,24),
                Arguments.arguments(5,120)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "3, 6",
            "4, 24",
            "5, 120",
    })
    void fractionalTest(int number, int expected){
        int result = 1;
        for (int i = 1; i <= number; i++) {
            result = result * i;
        }
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    void fractionalTest2(int number, int expected) {
        int result = 1;
        for (int i = 1; i <= number; i++) {
            result = result * i;
        }
        assertEquals(expected, result);
    }
}