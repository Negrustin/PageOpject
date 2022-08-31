package ru.netology.ibank.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferAmountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement accountTransferButton = $("[data-test-id='action-transfer']");

    public DashboardPage topUpYourCardByAmount(DataHelper.AccountNumber fromAccount, int amount){
        String stringAmount = Integer.toString(amount);

        transferAmountInput.setValue(stringAmount);
        fromInput.setValue(fromAccount.getAccountNumber());
        accountTransferButton.click();
        return new DashboardPage();

        }

    }
