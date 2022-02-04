package ru.puzikov.ftpparser;

import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository<Game, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndVersion(String name, String version);

    Game findByName(String name);


}
