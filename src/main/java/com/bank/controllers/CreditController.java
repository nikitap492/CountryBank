package com.bank.controllers;

import com.bank.domain.Bill;
import com.bank.domain.services.credit.Credit;
import com.bank.service.BillService;
import com.bank.service.CreditService;
import com.bank.validators.CreditValidator;
import com.bank.validators.ValidationResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class CreditController {
    private static final Logger log = LoggerFactory.getLogger(CreditController.class);

    @Autowired
    private CreditService creditService;

    @Autowired
    private BillService billService;

    @Autowired
    private CreditValidator validator;

    /**
     * @param authentication needs for username
     * @return current bill for account
     */
    @ModelAttribute("curBill")
    public Bill curBill(Authentication authentication) {
        return authentication == null ? null : billService.findByUsername(authentication.getName());
    }

    /**
     * @param ajaxCredit is special entity {@link CreditController.AjaxCredit}
     * @param bill       is current bill
     * @return {@link ResponseEntity<String>} status and message
     */
    @RequestMapping(value = "/api/credit/save", method = POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody AjaxCredit ajaxCredit, @ModelAttribute("curBill") Bill bill) {
        //validation
        ValidationResult<Credit> validated = validator
                .validate(bill, ajaxCredit.numOfWithdraws, ajaxCredit.money, ajaxCredit.type);
        if (validated.hasError()) {
            log.debug("There was error : " + validated.getError());
            return ResponseEntity.badRequest().body(validated.getError());
        }
        creditService.save(validated.getEntity());
        return ResponseEntity.ok().body(null);
    }

    /**
     * Ajax credit
     */
    private static class AjaxCredit {

        private String money;
        private String type;
        private String numOfWithdraws;

        public AjaxCredit() {
        }

        @JsonCreator
        public AjaxCredit(@JsonProperty("money") String money, @JsonProperty("type") String type, @JsonProperty("num") String numOfWithdraws) {
            this.money = money;
            this.type = type;
            this.numOfWithdraws = numOfWithdraws;
        }

        public String getMoney() {
            return money;
        }

        public String getType() {
            return type;
        }

        public String getNumOfWithdraws() {
            return numOfWithdraws;
        }
    }


}
