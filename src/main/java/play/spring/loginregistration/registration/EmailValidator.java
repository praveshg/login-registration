package play.spring.loginregistration.registration;

import java.util.function.Predicate;
import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String> {

  public boolean test(String email) {
    return true;
  }
}
