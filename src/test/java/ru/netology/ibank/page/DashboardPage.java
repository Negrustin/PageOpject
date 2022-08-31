package ru.netology.ibank.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {
  private final SelenideElement heading = $("[data-test-id=dashboard]");

  private final SelenideElement card_001 = $x("//*[@data-test-id = '92df3f1c-a033-48e6-8390-206f6b1f56c0']");
  private final SelenideElement card_002 = $x("//*[@data-test-id = '0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
  private final SelenideElement topUpAcc_OO1_Button = $x("//*[@data-test-id = '92df3f1c-a033-48e6-8390-206f6b1f56c0']/button");
  private final SelenideElement topUpAcc_002_Button = $x("//*[@data-test-id = '0f3f5c2a-249e-4c3d-8287-09f7a039391d']/button");
  private final SelenideElement reload = $x("//button[@data-test-id = 'action-reload']");
  private final SelenideElement errorWindow =$x("//*[@data-test-id ='error-notification']");


  private final ElementsCollection cards = $$(".list__item");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";


  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public int getCardBalance(String id) {
    val d_t_id_001  = card_001.getAttribute("data-test-id");
    val d_t_id_002 = card_002.getAttribute("data-test-id");

    String text = null;

    for(SelenideElement card : cards){
      if(d_t_id_001.equals(id)){
        text = card_001.text();
      } else if (d_t_id_002.equals(id)) {
        text = card_002.text();
      }

    }
    return extractBalance(text);
  }

  public int extractBalance(String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }

  public void topUpCard_001() {
    topUpAcc_OO1_Button.click();
  }


  public void topUpCard_002() {
    topUpAcc_002_Button.click();
  }
  public String getFirstMyCardId(){
    return card_001.getAttribute("data-test-id");
  }
  public String getSecondMyCardId(){
    return card_002.getAttribute("data-test-id");
  }

public boolean verifyTransferFromSecondCardToFirstCard (int firstCardStart_balance, int secondCardStart_balance,int firstCardCurrent_balance, int secondCardCurrent_balance, int amount){
    if(firstCardCurrent_balance == firstCardStart_balance + amount &&
            secondCardCurrent_balance == secondCardStart_balance - amount){
      return true;
    }else{
      return false;
    }
}

public void getErrorMassage(String errorMsg){
    errorWindow
            .shouldHave(visible,text(errorMsg));
}

}
