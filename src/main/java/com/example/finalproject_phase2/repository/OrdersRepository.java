package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Collection<Orders> showOrdersToSpecialist(SubDuty subDuty );
    Optional<Orders> findOrdersWithThisCustomerAndSubDuty(Customer customer, SubDuty subDuty);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(Customer customer);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(Customer customer);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(Customer customer);
    Collection<Orders> findOrdersInStatusStarted(Customer customer);
    Collection<Orders> findOrdersInStatusPaid(Customer customer);
    Collection<Orders> findOrdersInStatusDone(Customer customer);
}
