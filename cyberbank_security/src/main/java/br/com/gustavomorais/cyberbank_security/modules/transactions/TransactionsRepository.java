package br.com.gustavomorais.cyberbank_security.modules.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsEntity, Long> {

    List<TransactionsEntity> findByAccountPayment(String accountPayment);

    List<TransactionsEntity> findByAccountReceivable(String accountReceivable);

}