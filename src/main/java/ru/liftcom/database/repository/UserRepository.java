package ru.liftcom.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liftcom.database.entity.CustomUser;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByName(String name);

}
