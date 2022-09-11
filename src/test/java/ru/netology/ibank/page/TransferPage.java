package ru.netology.ibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;


public class TransferPage {
    SelenideElement amountInput = $x("//*[@data-test-id = 'amount']//input");
    SelenideElement fromInput = $x("//*[@data-test-id = 'from']//input");
    SelenideElement transferButton = $x("//button[@data-test-id = 'action-transfer']");
    SelenideElement errorMassage = $x("//*[@data-test-id = 'error-notification']");



    public DashboardPage makeValidTransfer(int amountToTransfer, DataHelper.CardInfo cardInfo){
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }
    public void makeInvalidCardNumberTransfer(int amountToTransfer, DataHelper.CardInfo cardInfo){
        makeValidTransfer(amountToTransfer,cardInfo);
    }
    public void getErrorWindow(String expectedText){
        errorMassage
                .shouldHave(Condition.visible,text(expectedText));
    }

    public void makeTransfer(int amountToTransfer, DataHelper.CardInfo cardInfo){
        amountInput.setValue(Integer.toString(amountToTransfer));
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

}
