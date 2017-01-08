package com.bank;

import com.bank.domain.Account;
import com.bank.domain.Bill;
import com.bank.domain.services.Movement;
import com.bank.domain.user.User;
import com.bank.service.AccountService;
import com.bank.service.BillService;
import com.bank.service.MovementService;
import com.bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.bank.domain.services.Direction.IN;

/**
 * Test data
 */
@Component
public class DataTest {

    private static final Logger log = LoggerFactory.getLogger(DataTest.class);

    @Autowired
    private BillService billService;

    @Autowired
    private MovementService movementService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;


    //Test data

    public static String jimmy = "Jimmy Willing";
    public static String susie = "Susie Willing";
    public static String bart = "Bart Willing";
    public static String kurt = "Kurt Willing";


    public static User jimmyUser;
    public static User susieUser;
    public static User bartUser;
    public static User kurtUser;

    public static Account jimmyAcc;
    public static Account susieAcc;
    public static Account bartAcc;


    public static Bill bartBill;
    public static Bill jimmyBill;

    public static Movement bartMov;

    @PostConstruct
    void init() {
        log.info("************************* Initialization **********************");


        jimmyUser = new User(jimmy, jimmy, jimmy + "@" + jimmy + ".us");
        susieUser = new User(susie, susie, jimmy + "@" + susie + ".us");
        bartUser = new User(bart, bart, bart + "@" + bart + ".us");
        kurtUser = new User(kurt, kurt, kurt + "@" + kurt + ".us");

        userService.save(jimmyUser, susieUser, bartUser, kurtUser);

        jimmyAcc = new Account(jimmyUser, jimmy, jimmy + "_home");
        bartAcc = new Account(bartUser, bart, bart + "_home");
        susieAcc = new Account(susieUser, susie, susie + "_home");

        accountService.save(jimmyAcc, susieAcc, bartAcc);

        bartBill = new Bill(bartAcc, 1200.0);
        jimmyBill = new Bill(jimmyAcc, 1200.0);

        billService.save(bartBill);
        billService.save(jimmyBill);

        bartMov = new Movement(bartBill, IN, 300.0);
        movementService.save(bartMov);


        User bankUser = new User("country_bank", "Uf!3aaV55qSkbLLh92112k4mQACa", "CountryBank@company.com");
        User govUser = new User("government", "qwerty)", "gov@gov.gov");
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
        User testUser = new User(test, test, test + "@" + test + ".us");
        Account testAcc = new Account(testUser, test, test + "_home");
        Bill testBill = new Bill(testAcc, 1000.0);
        userService.save(testUser);
        accountService.save(testAcc);
        billService.save(testBill);

        log.info("************************* End of Initialization **********************");
    }


}
