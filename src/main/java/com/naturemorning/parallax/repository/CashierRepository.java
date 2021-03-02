
package com.naturemorning.parallax.repository;

import org.springframework.stereotype.Repository;
import com.naturemorning.parallax.models.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CashierRepository extends JpaRepository<Cashier, Long> {
    //User findByEmail(String email);
}
