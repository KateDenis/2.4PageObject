package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public SelenideElement getCardButton(String id) {
        return $("[data-test-id='" + id + "']").find("button");
    }

    public int getCardBalance(String id) {
        val text = $("[data-test-id='" + id + "']").text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage transferMoney(String id) {
        getCardButton(id).click();
        return new MoneyTransferPage();
    }
}






