package com.bezkoder.spring.jpa.h2.controller;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.aspectj.apache.bcel.classfile.Signature.translate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.rules.ExpectedException;
import org.junit.runners.Suite;

import com.baeldung.junit5.bean.NumbersBean;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTests {
    private static Logger log = LogManager.getLogger(Log4j2.class);
    private NumbersBean bean = new NumbersBean();
    public boolean isNumberEven(Integer number) {
        return number % 2 == 0;
    }
    //Test
    @Test
    void givenEvenNumber_whenCheckingIsNumberEven_thenTrue() {
        boolean result = bean.isNumberEven(8);

        assertTrue(result);
    }
    @Test
    void givenOddNumber_whenCheckingIsNumberEven_thenFalse() {
        boolean result = bean.isNumberEven(3);

        Assertions.assertFalse(result);
    }
    @Test
    void givenLowerThanTenNumber_whenCheckingIsNumberEven_thenResultUnderTenMillis() {
        Assertions.assertTimeout(Duration.ofMillis(10), () -> bean.isNumberEven(3));
    }
    @Test
    void givenNull_whenCheckingIsNumberEven_thenNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> bean.isNumberEven(null));
    }

    //@BeforeAll and @BeforeEach
    @BeforeAll
    static void setup() {
        Container.log.info("@BeforeAll - executes once before all test methods in this class");
    }
    @BeforeEach
    void init() {
        Container.log.info("@BeforeEach - executes before each test method in this class");
    }

    //@DisplayName and @Disabled
    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        Container.log.info("Success");
    }
    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }

    //@AfterEach and @AfterAll
    @AfterEach
    void tearDown() {
        Container.log.info("@AfterEach - executed after each test method.");
    }
    @AfterAll
    static void done() {
        Container.log.info("@AfterAll - executed after all test methods.");
    }

    //Assertions
    @Test
    void lambdaExpressions() {
        List numbers = Arrays.asList(1, 2, 3);
        assertTrue(numbers.stream()
                .mapToInt(Integer::intValue)
                .sum() > 5, () -> "Sum should be greater than 5");
    }
    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        assertAll("numbers",
                () -> assertEquals(numbers[0], 1),
                () -> assertEquals(numbers[3], 3),
                () -> assertEquals(numbers[4], 1)
        );
    }

    //Assumptions
    @Test
    void trueAssumption() {
        assumeTrue(5 > 1);
        assertEquals(5 + 2, 7);
    }
    @Test
    void falseAssumption() {
        assumeFalse(5 < 1);
        assertEquals(5 + 2, 7);
    }
    @Test
    void assumptionThat() {
        String someString = "Just a string";
        assumingThat(
                someString.equals("Just a string"),
                () -> assertEquals(2 + 2, 4)
        );
    }

    //Exception Testing
    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals("Not supported", exception.getMessage());
    }
    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Integer.valueOf(str);
        });
    }

    //Test Suites
    @Suite
    @SelectPackages("com.baeldung")
    @ExcludePackages("com.baeldung.suites")
    public class AllUnitTest {}
    @Suite
    @SelectClasses({AssertionTest.class, AssumptionTest.class, ExceptionTest.class})
    public class AllUnitTest {}

    //Dynamic Tests
     private List<String> in = new ArrayList<>(Arrays.asList());
    private List<String> out = new ArrayList<>(Arrays.asList());
    @TestFactory
    Stream<DynamicTest> translateDynamicTestsFromStream() {
        return in.stream()
                .map(word ->
                        DynamicTest.dynamicTest("Test translate " + word, () -> {
                            int id = in.indexOf(word);
                            assertEquals(out.get(id), translate(word));
                        })
                );
    }

    //JUnit 5
    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void whenDerivedExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //JUnit 4
    @Test(expected = NullPointerException.class)
    public void whenExceptionThrown_thenExpectationSatisfied() {
        String test = null;
        test.length();
    }
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Test
    public void whenExceptionThrown_thenRuleIsApplied() {
        exceptionRule.expect(NumberFormatException.class);
        exceptionRule.expectMessage("For input string");
        Integer.parseInt("1a");
    }
}
