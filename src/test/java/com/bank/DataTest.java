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
    public static String bart = "Bart Black";
    public static String kurt = "Kurt Black";
    public static String susie = "Susie Black";

    public static User bartUser;
    public static User kurtUser;
    public static User susieUser;


    public static Account jimmyAcc;
    public static Account susieAcc;
    public static Account bartAcc;
    public static Account aliceAcc;

    public static Bill bartBill;
    public static Bill jimmyBill;

    public static Movement bartMov;

    @PostConstruct
    void init() {
        log.info("************************* Initialization **********************");
        String jimmy = "Jimmy Black";
        String alice = "Alice Black";

        User jimmyUser = new User(jimmy, jimmy, jimmy + "@" + jimmy + ".us");
        susieUser = new User(susie, susie, jimmy + "@" + susie + ".us");
        bartUser = new User(bart, bart, bart + "@" + bart + ".us");
        kurtUser = new User(kurt, kurt, kurt + "@" + kurt + ".us");
        User aliceUser = new User(alice, alice, alice + "@" + alice + ".us");

        userService.save(jimmyUser, susieUser, bartUser, kurtUser, aliceUser);

        jimmyAcc = new Account(jimmyUser, jimmy, jimmy + "_home");
        bartAcc = new Account(bartUser, bart, bart + "_home");
        susieAcc = new Account(susieUser, susie, susie + "_home");
        aliceAcc = new Account(aliceUser, alice, alice + "_home");

        accountService.save(jimmyAcc, susieAcc, bartAcc, aliceAcc);

        bartBill = new Bill(bartAcc, 1200.0);
        jimmyBill = new Bill(jimmyAcc, 1200.0);

        billService.save(bartBill);
        billService.save(jimmyBill);

        bartMov = new Movement(bartBill, IN, 300.0);

        movementService.save(bartMov);

        log.info("************************* End of Initialization **********************");
    }


}
