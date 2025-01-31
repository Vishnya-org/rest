package ru.itmentor.spring.boot_security.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.Optional;

@Repository
public interface AuthUserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);
}
