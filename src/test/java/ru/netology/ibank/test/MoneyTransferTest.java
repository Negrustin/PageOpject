package ru.netology.ibank.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.data.DataHelper;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.ibank.data.DataHelper.*;
import static ru.netology.ibank.data.DataHelper.generateInvalidAmount;

public class MoneyTransferTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void transferFromFirstCardToSecondCard() {
        DashboardPage dashboardPage = new DashboardPage();
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);

        var amount = generateValidAmount(secondCardBalance);

        transferPage.makeValidTransfer(amount, secondCardInfo);

        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);

        Assertions.assertEquals(firstCardBalance + amount, actualBalanceFirstCard);
        Assertions.assertEquals(secondCardBalance - amount, actualBalanceSecondCard);
    }

    @Test
    void transferFromSecondCardToFirstCard() {
        DashboardPage dashboardPage = new DashboardPage();
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        var amount = generateValidAmount(firstCardBalance);

        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        transferPage.makeValidTransfer(amount, firstCardInfo);

        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);

        Assertions.assertEquals(firstCardBalance - amount, actualBalanceFirstCard);
        Assertions.assertEquals(secondCardBalance + amount, actualBalanceSecondCard);
    }

    @Test
    void shouldErrorMassageIfCardNumberInvalid(){
        DashboardPage dashboardPage = new DashboardPage();
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var invalidCardInfo = getInvalidCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        var amount = generateValidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        transferPage.makeInvalidCardNumberTransfer(amount, invalidCardInfo);

        transferPage.getErrorWindow("Номер карты указан неверно");

    }

    @Test
    void shouldErrorMassageIfAmountMoreBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        transferPage.makeValidTransfer(amount,firstCardInfo);
        transferPage.getErrorWindow("Недостатосно средств для выполнения операции");

        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);


        Assertions.assertEquals(firstCardBalance,actualBalanceFirstCard);
        Assertions.assertEquals(secondCardBalance,actualBalanceSecondCard);
    }

}
