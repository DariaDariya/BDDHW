package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;

import static com.codeborne.selenide.Selenide.$;


public class TransferPage {
    private SelenideElement addMoneyHeading = $(withText("Пополнение карты"));
    private SelenideElement totField = $(".money-input .input__control");
    private SelenideElement amountField = $("[data-test-id='amount'] [type='text']");
    private SelenideElement sourceCardField = $("[data-test-id='from'] .input__control");
    private SelenideElement addFundsButton = $("[data-test-id='action-transfer'] .button__text");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public TransferPage() {
    }

    public void transaction(String value, String source) {
        totField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, value.replace(" ", ""));
        sourceCardField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, source.replace(" ", ""));
        addFundsButton.click();
    }

    public DashboardPage validTransfer(String amountTransfer, DataHelper.InfoCard infoCard) {
        amountField.setValue(amountTransfer);
        sourceCardField.setValue(infoCard.getCardNumber());
        addFundsButton.click();
        return new DashboardPage();
    }

}
