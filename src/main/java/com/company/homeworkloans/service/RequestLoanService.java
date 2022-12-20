package com.company.homeworkloans.service;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.company.homeworkloans.entity.LoanStatus.REQUESTED;
import static com.company.homeworkloans.validate.ValidateRequestLoan.validateAmount;
import static com.company.homeworkloans.validate.ValidateRequestLoan.validateClients;

@Service
public class RequestLoanService {

    @Autowired
    private DataManager dataManager;

    public String executeRequest(Client client, String amount) {

        BigDecimal amountBD;

        try {
            amountBD = new BigDecimal(amount);
        } catch (Exception exception) {

            return "Введите валидное значение(только цифры) в поле Amount";
        }

        if (validateClients(client) && validateAmount(amountBD)) {
            Loan loan = dataManager.create(Loan.class);
            loan.setAmount(new BigDecimal(amount));
            loan.setClient(client);
            loan.setStatus(REQUESTED);
            loan.setRequestDate(LocalDate.now());
            dataManager.save(loan);

            return "";
        } else {
            return "Не были введены необходимые параметры/либо параметры введены неверно";
        }
    }
}
