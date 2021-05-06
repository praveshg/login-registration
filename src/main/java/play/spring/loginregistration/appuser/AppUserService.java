package play.spring.loginregistration.appuser;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import play.spring.loginregistration.registration.token.ConfirmationToken;
import play.spring.loginregistration.registration.token.ConfirmationTokenService;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

  private final static String USER_NOT_FOUND = "User with Email %s Not Found";
  private final AppUserRepository appUserRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return appUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
  }

  public String signUpUser(AppUser appUser) {
    boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
    if(userExists) {
      // TODO check of attributes are the same and
      // TODO if email not confirmed send confirmation email.
      throw new IllegalStateException("Email Already Taken");
    }
    String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
    appUser.setPassword(encodedPassword);

    appUserRepository.save(appUser);

    //Send Confirmation Token

    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15),
        appUser
    );
    confirmationTokenService.saveConfirmationToken(confirmationToken);

    //TODO : Send Email
    return token;
  }

  public int enableAppUser(String email) {
    return appUserRepository.enableAppUser(email);
  }
}
