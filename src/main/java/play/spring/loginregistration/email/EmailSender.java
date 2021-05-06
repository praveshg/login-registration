package play.spring.loginregistration.email;

public interface EmailSender {
  void send(String to, String email);
}
