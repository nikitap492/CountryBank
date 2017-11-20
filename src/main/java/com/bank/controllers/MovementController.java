package com.bank.controllers;

import com.bank.domain.Bill;
import com.bank.service.BillService;
import com.bank.service.MovementService;
import com.bank.validators.MovementValidationAnswer;
import com.bank.validators.MovementValidator;
import com.bank.validators.ValidationResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor
public class MovementController {
    private final BillService billService;
    private final MovementService movementService;
    private final MovementValidator movementValidator;

    /**
     * @param authentication needs for username
     * @return current bill for account
     */
    @ModelAttribute("curBill")
    public Bill getBill(Authentication authentication) {
        return authentication == null ? null : billService.findByUsername(authentication.getName());
    }

    /**
     * @param acm     was sent by ajax
     * @param curBill is current bill for account
     * @return response message
     */
    @RequestMapping(value = "/api/movement/transfer", method = POST)
    public String transfer(@RequestBody AjaxCreateMovement acm, @ModelAttribute("curBill") Bill curBill) {
        log.debug("UUID : " + acm.getUuid() + ", quantity : " + acm.getQuantity());
        ValidationResult<MovementValidationAnswer> result = movementValidator.validateTransfer(curBill, acm.getUuid(), acm.getQuantity());
        if (result.hasError())
            return result.getError();
        Bill bill = result.getEntity().getBill();
        Double money = result.getEntity().getMoney();
        movementService.makeTransfer(bill, curBill, money, acm.message);
        return "Transfer has been saved";
    }

    /**
     * @param acm     was sent by ajax
     * @param curBill is current bill for account
     * @return {@link ResponseEntity<String>} status and message
     */
    @RequestMapping(value = "/api/movement/pay", method = POST)
    public ResponseEntity<String> pay(@RequestBody AjaxCreateMovement acm, @ModelAttribute("curBill") Bill curBill) {
        log.debug("Attempt to pay " + acm.getQuantity());
        ValidationResult<Double> result = movementValidator.validatePay(curBill, acm.getQuantity());
        if (result.hasError())
            return ResponseEntity.badRequest().body(result.getError());
        movementService.makePay(curBill, result.getEntity());
        return ResponseEntity.ok().body("Your payment has been saved");
    }


    /**
     * Ajax entity for {@link com.bank.domain.services.Movement}
     */
    private static class AjaxCreateMovement {

        private String uuid;
        private String quantity;
        private String message;

        public AjaxCreateMovement() {
        }

        @JsonCreator
        public AjaxCreateMovement(@JsonProperty("uuid") String uuid,
                                  @JsonProperty("quantity") String quantity,
                                  @JsonProperty("message") String message) {
            this.uuid = uuid;
            this.quantity = quantity;
            this.message = message;
        }

        public String getUuid() {
            return uuid;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getMessage() {
            return message;
        }

    }

}
