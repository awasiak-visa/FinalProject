package pl.coderslab.play;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import pl.coderslab.Status;
import pl.coderslab.cafe.Cafe;
import pl.coderslab.user.User;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "plays")
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    private BoardGame boardGame;
    @NotNull
    @Future
    private LocalDateTime dateTime;
    @ManyToOne
    @NotNull
    private Cafe cafe;
    @ManyToMany
    @UniqueElements
    @NotEmpty
    private List<User> users;
    @NotNull
    private Status status;
    @Min(0)
    @NotNull
    private Integer freePlaces;
}
