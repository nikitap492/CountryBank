package com.bank;

import com.bank.domain.Bill;
import com.bank.domain.services.Movement;
import com.bank.repositories.RememberMeTokenRepositoryTest;
import com.bank.service.*;
import com.bank.validators.ValidatorSuiteTest;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;

/**
 * Common test suite
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceSuiteTest.class, ValidatorSuiteTest.class, RememberMeTokenRepositoryTest.class})
public class MainTestSuite {



}
