package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement heading = $("h1");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public MoneyTransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage sendMoney(DataHelper.CardInfo cardFrom, Integer amount) {
        fromField.setValue(cardFrom.getNumber());
        amountField.setValue(String.valueOf(amount));
        transferButton.click();
        return new DashboardPage();
    }
}
