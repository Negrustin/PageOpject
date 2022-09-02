package ru.netology.ibank.test;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.data.DataHelper;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.TransferPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class MoneyTransferTest {

    int amount = 200;


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
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        int firstCardStart_balance = dashboardPage.getCardBalance(dashboardPage.getFirstMyCardId());
        int secondCardStart_balance = dashboardPage.getCardBalance(dashboardPage.getSecondMyCardId());

        dashboardPage.topUpCard_001();

        transferPage.topUpYourCardByAmount(DataHelper.getAccountNumber_002(), amount);

        int expected = firstCardStart_balance + amount;
        int expected2 = secondCardStart_balance - amount;

        int firstCardCurrent_balance = dashboardPage.getCardBalance(dashboardPage.getFirstMyCardId());
        int secondCardCurrent_balance = dashboardPage.getCardBalance(dashboardPage.getSecondMyCardId());

        int actual = firstCardCurrent_balance;
        int actual2 = secondCardCurrent_balance;

//        assertTrue(dashboardPage
//                .verifyTransferFromSecondCardToFirstCard(
//                firstCardStart_balance,
//                secondCardStart_balance,
//                firstCardCurrent_balance,
//                secondCardCurrent_balance,
//                amount))
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }



    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        int firstCardStart_balance = dashboardPage.getCardBalance(dashboardPage.getFirstMyCardId());
        int secondCardStart_balance = dashboardPage.getCardBalance(dashboardPage.getSecondMyCardId());

        dashboardPage.topUpCard_002();

        transferPage.topUpYourCardByAmount(DataHelper.getAccountNumber_001(),amount);

        int expected = secondCardStart_balance + amount;
        int expected2 = firstCardStart_balance - amount;

        int firstCardCurrent_balance = dashboardPage.getCardBalance(dashboardPage.getFirstMyCardId());
        int secondCardCurrent_balance = dashboardPage.getCardBalance(dashboardPage.getSecondMyCardId());

        int actual = secondCardCurrent_balance;
        int actual2 = firstCardCurrent_balance;

        assertEquals(expected, actual);
        assertEquals(expected2, actual2);

    }

    @Test
    public void transferringMoreThanTheAmountInTheAccount() {
        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        int firstCardStart_balance = dashboardPage.getCardBalance(dashboardPage.getFirstMyCardId());
        int secondCardStart_balance = dashboardPage.getCardBalance(dashboardPage.getSecondMyCardId());

        int expected1 = firstCardStart_balance;
        int expected2 = secondCardStart_balance;

        dashboardPage.topUpCard_001();

        transferPage.topUpYourCardByAmount(DataHelper.getAccountNumber_002(), firstCardStart_balance + amount);

        int firstCardCurrent_balance = dashboardPage.getCardBalance(dashboardPage.getFirstMyCardId());
        int secondCardCurrent_balance = dashboardPage.getCardBalance(dashboardPage.getSecondMyCardId());

        int actual1 = firstCardCurrent_balance;
        int actual2 = secondCardCurrent_balance;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

        dashboardPage.getErrorMassage("Недомтаточно средств");

    }

    @Test
    void shouldTransferMoneyFromNon_ExistendAccountNumber() {
        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();


        dashboardPage.topUpCard_001();

        transferPage.topUpYourCardByAmount(DataHelper.getNon_ExistentdAccountNumber(), amount);

        dashboardPage.getErrorMassage("Номер счёта введен неверно");

    }




}

