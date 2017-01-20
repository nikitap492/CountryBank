package com.bank.service;

import com.bank.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bank.DataTest.*;
import static com.bank.service.BlockerService.*;
import static com.bank.service.BlockerService.UserFailStatus.*;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource("classpath:test.properties")
public class BlockerServiceTest {

    @Autowired
    private BlockerService blockerService;

    @Test
    public void shouldReturnWrong(){
        assertEquals(WRONG, blockerService.failed("Not Found"));
        assertEquals(WRONG, blockerService.failed(bart));

    }

    @Test
    public void shouldReturnBlock(){
        int i = 0;
        while (i < MAX_ATTEMPTS) {
            blockerService.failed(kurt);
            i++;
        }
        assertEquals(BLOCK, blockerService.failed(kurt));
    }

    @Test
    public void shouldClearCache(){
        int i = 0;
        while (i < MAX_ATTEMPTS) {
            blockerService.failed(susie);
            i++;
        }
        blockerService.success(susie);
        assertEquals(WRONG, blockerService.failed(susie));
    }


}
