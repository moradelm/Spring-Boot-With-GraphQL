package org.example.springbootwithgraphql.DTO;

import lombok.Data;
import org.example.springbootwithgraphql.entities.TypeTransaction;

import java.util.Date;

@Data
public class TransactionRequest {

    private Long compteId;
    private double montant;
    private TypeTransaction type;
    private Date dateTransaction;
}
