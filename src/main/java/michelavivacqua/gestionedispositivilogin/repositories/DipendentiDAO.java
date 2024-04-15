package michelavivacqua.gestionedispositivilogin.repositories;


import michelavivacqua.gestionedispositivilogin.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipendentiDAO extends JpaRepository<Dipendente, Integer> {
    boolean existsByEmail(String email);
    Optional<Dipendente> findByEmail(String nome);

}