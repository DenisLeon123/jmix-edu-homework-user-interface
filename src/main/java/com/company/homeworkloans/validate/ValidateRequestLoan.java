package com.company.homeworkloans.validate;

import com.company.homeworkloans.entity.Client;

import java.math.BigDecimal;

public class ValidateRequestLoan {

    public static boolean validateClients(Client client) {
        return client != null;
    }

    public static boolean validateAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
