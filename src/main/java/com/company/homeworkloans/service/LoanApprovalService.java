package com.company.homeworkloans.service;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApprovalService {

    @Autowired
    private DataManager dataManager;

    public void changeStatus(Loan loan, LoanStatus status) {
        loan.setStatus(status);
        dataManager.save(loan);
    }
}
