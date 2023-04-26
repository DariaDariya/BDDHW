package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var DashboardPage = verificationPage.validVerify(verificationCode);
        var infoCardFirst = DataHelper.getFirstInfoCard();
        var infoCardSecond = DataHelper.getSecondInfoCard();
        var firstDashboardPage = DashboardPage.getDashboardPage(infoCardFirst);
        var secondDashboardPage = DashboardPage.getDashboardPage(infoCardSecond);
        var amount = generateValidAmount(firstDashboardPage);
        var expectedFirstDashboardPage = firstDashboardPage + amount;
        var expectedSecondDashboardPage = secondDashboardPage - amount;
        var transactionPage = DashboardPage.selectCardToTransfer(infoCardFirst);
        transactionPage.validTransfer(String.valueOf(amount), infoCardSecond);

        assertEquals(expectedFirstDashboardPage, DashboardPage.getDashboardPage(infoCardFirst));
        assertEquals(expectedSecondDashboardPage, DashboardPage.getDashboardPage(infoCardSecond));

    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val DashboardPage = verificationPage.validVerify(verificationCode);
        var infoCardSecond = DataHelper.getSecondInfoCard();
        var infoCardFirst = DataHelper.getFirstInfoCard();
        val secondDashboardPage = DashboardPage.getDashboardPage(infoCardSecond);
        val firstDashboardPage = DashboardPage.getDashboardPage(infoCardFirst);
        val amount = generateValidAmount(firstDashboardPage);
        val expectedFirstDashboardPage = firstDashboardPage - amount;
        val expectedSecondDashboardPage = secondDashboardPage + amount;
        val transactionPage = DashboardPage.selectCardToTransfer(infoCardSecond);
        transactionPage.validTransfer(String.valueOf(amount), infoCardFirst);

        assertEquals(expectedSecondDashboardPage, DashboardPage.getDashboardPage(infoCardSecond));
        assertEquals(expectedFirstDashboardPage, DashboardPage.getDashboardPage(infoCardFirst));

    }

    @Test
    void shouldFailToAuthorizeWithInvalidVerificationCode() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var badVerificationCode = DataHelper.getOtherVerificationCodeFor(authInfo);
        verificationPage.invalidVerify(badVerificationCode);
    }

    @Test
    void shouldFailToAuthorizeWithInvalidVerificationCode2() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getOtherAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var badVerificationCode = DataHelper.getOtherVerificationCodeFor(authInfo);
        verificationPage.invalidVerify(badVerificationCode);
    }


}

