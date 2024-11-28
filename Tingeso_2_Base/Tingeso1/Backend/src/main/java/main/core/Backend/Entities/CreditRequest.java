package main.core.Backend.Entities;

import main.core.Backend.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "creditrequest")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String type;
    int term;
    float interest;
    int propertyValue;
    float loanValue;
    float monthlyQuota;
    String status; //Estado de la solicitud

    @Lob
    @Column(name = "document1")
    byte[] document1;

    @Lob
    @Column(name = "document2")
    byte[] document2;

    @Lob
    @Column(name = "document3")
    byte[] document3;

    @Lob
    @Column(name = "document4")
    byte[] document4;
}
