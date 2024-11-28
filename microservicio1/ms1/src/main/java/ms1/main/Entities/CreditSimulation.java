package ms1.main.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "creditsimulation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditSimulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    float loan;
    float interest;
    int payment_quantity;
    float quota;
}