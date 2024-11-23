package org.example.springbootwithgraphql.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double montant;

    @Temporal(TemporalType.DATE)
    private Date dateTransaction;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;
}