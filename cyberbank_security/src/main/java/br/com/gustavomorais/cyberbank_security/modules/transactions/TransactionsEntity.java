package br.com.gustavomorais.cyberbank_security.modules.transactions;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "transactions")
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String method;

    private String chave;

    private String accountPayment;

    private String accountReceivable;

    private Integer value;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
