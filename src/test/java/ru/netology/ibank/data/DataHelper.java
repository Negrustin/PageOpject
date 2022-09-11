package ru.netology.ibank.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {


  public static int generateValidAmount(int cardBalance) {
    return new Random().nextInt(cardBalance) + 1;
  }

  public static int generateInvalidAmount(int cardBalance) {
    return Math.abs(cardBalance) + new Random().nextInt(10000);
  }


  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  @Value
  public static class CardInfo {
    private String cardNumber;
    private String data_test_id;
  }

  @Value
  public static class VerificationCode {
    private String code;
  }


  public static AuthInfo getAuthInfo() {

    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {

    return new AuthInfo("petya", "123qwerty");
  }

  public static CardInfo getFirstCardInfo() {
    return new CardInfo("5559000000000001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
  }

  public static CardInfo getSecondCardInfo() {
    return new CardInfo("5559000000000002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
  }

  public static CardInfo getInvalidCardInfo(){
    return new CardInfo("0000000000000000", "00000000-0000-0000-0000-000000000000");
  }


  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }
}


