package com.example.unittesting.app.suite;

import com.example.unittesting.app.service.srvImpl.CalculatorSrvImplTest;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Disabled("wqe")
@Suite
@SelectClasses({CalculatorSrvImplTest.class})
@SelectPackages("com.example.unittesting.app.service.srvImpl")
public class SuiteTest {
}
