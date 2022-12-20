package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.service.RequestLoanService;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@UiController("RequestLoan")
@UiDescriptor("request-loan.xml")
public class RequestLoan extends Screen {

    @Autowired
    private CollectionContainer<Client> clientsDc;
    @Autowired
    private TextField amount;
    @Autowired
    RequestLoanService requestLoanService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private CollectionLoader<Client> clientsDl;

    private Client client;
    @Autowired
    private EntityComboBox<Client> clientSelector;

    @Subscribe("request")
    public void onRequest(Action.ActionPerformedEvent event) {
        Client client = clientsDc.getItem();
        String amountString = amount.getRawValue();

        String msg = requestLoanService.executeRequest(client, amountString);

        if (!msg.isEmpty()) {
            notifications
                    .create()
                    .withCaption(msg)
                    .show();
        } else {
            close(StandardOutcome.CLOSE);
        }
    }

    @Subscribe("closeBtn")
    public void onCloseBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }

    public Client getClient() {
        return client;
    }

    public RequestLoan withClient(Client client) {
        this.client = client;

        clientsDl.setParameter("client", client);
        clientsDl.load();

        clientSelector.setValue(client);

        return this;
    }

}