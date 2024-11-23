package org.example.springbootwithgraphql.Controllers;


import lombok.AllArgsConstructor;
import org.example.springbootwithgraphql.DTO.TransactionRequest;
import org.example.springbootwithgraphql.entities.Compte;
import org.example.springbootwithgraphql.entities.TypeTransaction;
import org.example.springbootwithgraphql.repositories.CompteRepository;
import org.example.springbootwithgraphql.repositories.TransactionRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.example.springbootwithgraphql.entities.Transaction;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TransactionControllerGraphQL {

    private TransactionRepository transactionRepository;
    private CompteRepository compteRepository;

    @MutationMapping
    public Transaction addTransaction(@Argument TransactionRequest transactionRequest) {

        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDate(transactionRequest.getDate());
        transaction.setType(transactionRequest.getType());
        transaction.setCompte(compte);


        transactionRepository.save(transaction);

        return transaction;
    }


    @QueryMapping
    public List<Transaction> allTransactions() {
        return transactionRepository.findAll();
    }

    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte not found"));
        return transactionRepository.findByCompte(compte);
    }

    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT);
        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );
    }


}