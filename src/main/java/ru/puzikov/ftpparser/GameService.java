package ru.puzikov.ftpparser;

import java.util.Optional;

public interface GameService {

    /**
     * Создает нового клиента
     *
     * @param game - клиент для создания
     */
    void create(Game game);

    /**
     * Возвращает список всех имеющихся клиентов
     *
     * @return список клиентов
     */
    Iterable<Game> readAll();

    /**
     * Возвращает клиента по его ID
     *
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    Optional<Game> read(Long id);

    /**
     * Обновляет клиента с заданным ID,
     * в соответствии с переданным клиентом
     *
     * @param game - клиент в соответсвии с которым нужно обновить данные
     * @param id   - id клиента которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(Game game, Long id);

    /**
     * Удаляет клиента с заданным ID
     *
     * @param id - id клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    boolean delete(Long id);
}
