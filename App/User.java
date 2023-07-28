public class User {
  private String login;
  private String password = "default";
  private String profile;

  public User(String login, String password, String profile) {
    this.login = login;
    this.password = password;
    this.profile = profile;
  }

  // Геттеры и сеттеры для полей класса User

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    if (this.password == "default") {
      this.password = password;
    }
    else {
      // TODO Нужна реализация окна с сообщением о том, что изменить пароль нельзя
    }
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }
}
