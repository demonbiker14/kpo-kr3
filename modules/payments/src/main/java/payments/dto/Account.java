package payments.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    private Long userId;

    @Version
    private Long version;

    @Column(nullable = false)
    private int balance;
}