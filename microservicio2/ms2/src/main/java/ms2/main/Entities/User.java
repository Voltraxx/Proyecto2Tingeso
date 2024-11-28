package ms2.main.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    String name;
    int age;
    float income;
    float balance; //saldo de la cuenta
    float debts;
    String job;

    @ElementCollection
    @CollectionTable(name = "withdrawals", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "retiro")
    List<Float> withdrawals = new ArrayList<>(Collections.nCopies(12, 0.0f));

    @ElementCollection
    @CollectionTable(name = "deposits", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "deposito")
    List<Float> deposits = new ArrayList<>(Collections.nCopies(12, 0.0f));

    int account_age;

//PRUEBA
}