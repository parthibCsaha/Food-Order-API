package com.orderfood.repository;

import com.orderfood.model.FinalOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalOrderRepository extends JpaRepository<FinalOrder, Long> {



}
