package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.LoanStatus;
import com.company.homeworkloans.service.LoanApprovalService;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@UiController("Loan.approval.browse")
@UiDescriptor("loan-approval-browse.xml")
@LookupComponent("loansTable")
public class LoanApprovalBrowse extends StandardLookup<Loan> {
    @Autowired
    private CollectionContainer<Loan> loansDc;

    @Autowired
    private Notifications notifications;

    @Autowired
    LoanApprovalService loanApprovalService;

    @Install(to = "loansTable.fullName", subject = "columnGenerator")
    private Component loansTableFullNameColumnGenerator(Loan loan) {
        Client client = loan.getClient();
        return new Table.PlainTextCell(client.getFirstName() + " " + client.getLastName());
    }

    @Install(to = "loansTable.clientAge", subject = "columnGenerator")
    private Component loansTableClientAgeColumnGenerator(Loan loan) {
        Client client = loan.getClient();
        return new Table.PlainTextCell(String.valueOf(Period.between(client.getBirthDate(), LocalDate.now()).getYears()));
    }

    @Subscribe("Approve")
    public void onApprove(Action.ActionPerformedEvent event) {
        Loan loan = loansDc.getItem();

        if (loan.getStatus() == LoanStatus.APPROVED) {
            showNotifications("Уже в статусе Approved");
        } else {
            loanApprovalService.changeStatus(loan, LoanStatus.APPROVED);

            List<Loan> loanList = loansDc.getMutableItems();
            loanList.remove(loan);

            showNotifications("Approved");
        }

    }

    @Subscribe("Reject")
    public void onReject(Action.ActionPerformedEvent event) {
        Loan loan = loansDc.getItem();

        if (loan.getStatus() == LoanStatus.REJECTED) {
            showNotifications("Уже в статусе Rejected");
        } else {
            loanApprovalService.changeStatus(loan, LoanStatus.REJECTED);

            List<Loan> loanList = loansDc.getMutableItems();
            loanList.remove(loan);

            showNotifications("Rejected");
        }
    }

    private void showNotifications(String message) {
        notifications
                .create()
                .withCaption(message)
                .show();
    }
}