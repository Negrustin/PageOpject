package ru.netology.ibank.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.ibank.data.DataHelper;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;


public class DashboardPage {
    private final SelenideElement heading = $x("//*[@data-test-id = 'dashboard']");
    private final ElementsCollection cards = $$(".list__item div");
    public DashboardPage() {heading.shouldBe(visible);};


    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        val text = cards.findBy(attribute("data-test-id", cardInfo.getData_test_id())).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo){
        cards.findBy(attribute("data-test-id", cardInfo.getData_test_id())).$("button").click();
        return new TransferPage();
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
