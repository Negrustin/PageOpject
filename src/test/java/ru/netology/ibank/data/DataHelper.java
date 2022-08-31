package ru.netology.ibank.data;

import lombok.Value;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }
  @Value
  public static class AccountNumber{
    private String accountNumber;

  }
public static AccountNumber getAccountNumber_001(){
    return new AccountNumber("5559000000000001");
}
public static AccountNumber getAccountNumber_002(){
  return new AccountNumber("5559000000000002");
}
public static AccountNumber getNon_ExistentdAccountNumber(){return new AccountNumber("0000000000000000");}
  public static AuthInfo getAuthInfo() {

    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }
}
