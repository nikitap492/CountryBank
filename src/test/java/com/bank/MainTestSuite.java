package com.bank;

import com.bank.repositories.RememberMeTokenRepositoryTest;
import com.bank.service.ServiceSuiteTest;
import com.bank.validators.ValidatorSuiteTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Common test suite
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceSuiteTest.class, ValidatorSuiteTest.class, RememberMeTokenRepositoryTest.class})
public class MainTestSuite {



}
