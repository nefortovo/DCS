package ru.liftcom.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liftcom.database.entity.CustomOrder;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomOrder, Long> {
    CustomOrder findByPhoneAndStatus(String phone, Integer status);
    List<CustomOrder> findByStatusOrderByOrderDate(int status);
    List<CustomOrder> findAllByStatus(int status);
}
