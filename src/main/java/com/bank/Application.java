package com.bank;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.domain.user.User;
import com.bank.service.AccountService;
import com.bank.service.BillService;
import com.bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import java.util.Properties;


@SpringBootApplication
@PropertySource(value = "classpath:application.yml")
@EnableScheduling
public class Application {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl_auto;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BillService billService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JavaMailSender mailSender(@Value("${spring.mail.host}") String host,
                                     @Value("${spring.mail.port}") int port,
                                     @Value("${spring.mail.username}") String username,
                                     @Value("${spring.mail.password}") String password,
                                     @Value("${spring.mail.smtp.auth}") String auth,
                                     @Value("${spring.mail.starttls.enable}") String starttls) {
        log.debug("Mail sender starts on host " + host + " and port " + port);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        log.debug("Main email username is " + username);
        sender.setUsername(username);
        sender.setPassword(password);
        Properties properties = new Properties();
        log.debug("Option mail.smtp.auth is " + auth);
        properties.setProperty("mail.smtp.auth", auth);
        log.debug("Option mail.smtp.starttls.enable is " + starttls);
        properties.setProperty("mail.smtp.starttls.enable", starttls);
        sender.setJavaMailProperties(properties);
        return sender;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

    /**
     * Dummy data
     */
    @Bean
    public CommandLineRunner commandLineRunner(@Value("${spring.mail.username}") String email) {
        return args -> {
            log.warn("Ddl-auto  :   " + ddl_auto);
            log.debug("Creation of dummy data");
            User bankUser = new User("country_bank", "Uf!3aaV55qSkbLLh92112k4mQACa", "CountryBank@company.com");
            User govUser = new User("government", "qwerty)", "gov@gov.gov");
            govUser.setEnabled(true);
            bankUser.setEnabled(true);
            userService.save(bankUser);
            userService.save(govUser);
            Account government = new Account(govUser, "Government", "Washington D.C");
            Account countryBank = new Account(bankUser, "Country Bank", "Wild West");
            accountService.save(government);
            accountService.save(countryBank);
            Bill governmentBill = new Bill(government, BillService.governmentUUID, 1_000_000_000.0);
            Bill countryBankBill = new Bill(countryBank, BillService.bankUUID, 10_000_000.0);
            billService.save(governmentBill);
            billService.save(countryBankBill);
            log.debug("Creation of test user");
            String test = "test";
            User testUser = new User(test, test, email);
            testUser.setEnabled(true);
            Account testAcc = new Account(testUser, "test", "test location");
            Bill testBill = new Bill(testAcc, 1000.0);
            userService.save(testUser);
            accountService.save(testAcc);
            billService.save(testBill);

        };
    }

}
