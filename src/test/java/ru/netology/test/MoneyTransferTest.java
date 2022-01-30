package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromFirstCardToSecond() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);
        Integer balance1 = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        Integer balance2 = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());

        MoneyTransferPage moneyTransferPage = dashboardPage.transferMoney(DataHelper.getSecondCard().getId());

        DashboardPage dashboardPageResult = moneyTransferPage.sendMoney(DataHelper.getFirstCard(), balance1);

        Integer balanceResult2 = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        Integer balanceResult1 = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        assertEquals(balanceResult2, balance2 + balance1);
        assertTrue(balanceResult1 >= 0);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirst() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);
        Integer balance1 = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        Integer balance2 = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());

        MoneyTransferPage moneyTransferPage = dashboardPage.transferMoney(DataHelper.getFirstCard().getId());

        DashboardPage dashboardPageResult = moneyTransferPage.sendMoney(DataHelper.getSecondCard(), balance2);

        Integer balanceResult1 = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        Integer balanceResult2 = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceResult1, balance1 + balance2);
        assertTrue(balanceResult2 >= 0);
    }

    @Test
    void shouldTransferMoneyOverMaxAmmount() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);
        Integer balance1 = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        Integer balance2 = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());

        MoneyTransferPage moneyTransferPage = dashboardPage.transferMoney(DataHelper.getSecondCard().getId());

        DashboardPage dashboardPageResult = moneyTransferPage.sendMoney(DataHelper.getFirstCard(), balance1 + 10000);

        Integer balanceResult2 = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        Integer balanceResult1 = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        assertEquals(balanceResult2, balance2 + balance1 + 10000);
        assertTrue(balanceResult1 >= 0);
    }
}

