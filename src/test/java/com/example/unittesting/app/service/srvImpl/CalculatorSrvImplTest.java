package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.repository.CalculatorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CalculatorSrvImplTest {

    @InjectMocks
    CalculatorSrvImpl srv;

    @Mock
    CalculatorRepository repository;

    @BeforeAll
    static void setUpBeforeClass() {
        System.out.println("Test setUpBeforeClass");
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("Test tearDownAfterClass");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("BeforeEach is calling");
    }

    @AfterEach
    public void tearDown() {
        srv = null;
        System.out.println("AfterEach is calling");
    }

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {

        @Test
        @Disabled("CalculatorSrvImplTest add method, Open it in version 1.23.4")
        @DisplayName("Add Two Numbers")
        void add() {
            assertEquals(4, srv.add(1, 3));
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {

        @Test
        @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
        @DisplayName("Subtract Two Numbers")
        @Order(3)
        void subtract() {
            int first = (int) (Math.random() * 1000);
            assertEquals(first - 3, srv.subtract(first, 3));
        }
    }

    @Nested
    @DisplayName("Multiply Tests")
    @Order(2)
    class MultiplyTests {

        @Test
        @RepeatedTest(value = 5, name = "Test {currentRepetition}*{totalRepetitions}: Multpily works correctly")
        void multiply() {
            Random random = new Random();
            int first = random.nextInt(1000);
            assertEquals(first * 3, srv.multiply(first, 3));
        }
    }

    @Test
    @Order(1)
    void divide() {
        assertThrows(ArithmeticException.class, () -> srv.divide(1, 0));
        assertEquals(1, srv.divide(3, 3));
    }


}