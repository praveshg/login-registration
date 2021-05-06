package play.spring.loginregistration.registration.token;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import play.spring.loginregistration.appuser.AppUser;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ConfirmationToken {

  @Id
  @SequenceGenerator(
      name = "confirmation_token_sequence",
      sequenceName = "confirmation_token_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "confirmation_token_sequence"
  )
  private Long id;
  @Column(nullable = false)
  private String token;
  @Column(nullable = false)
  private LocalDateTime createAt;
  @Column(nullable = false)
  private LocalDateTime expiresAt;
  private LocalDateTime confirmedAt;

  @ManyToOne
  @JoinColumn(
      nullable = false,
      name = "app_user_id"
  )
  private AppUser appUser;

  public ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expiredAt,
      AppUser appUser) {
    this.token = token;
    this.createAt = createAt;
    this.expiresAt = expiredAt;
    this.appUser = appUser;
  }
}
