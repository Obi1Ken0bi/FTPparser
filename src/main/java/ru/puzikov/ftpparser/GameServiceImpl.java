package ru.puzikov.ftpparser;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final GamesRepository gamesRepository;

    public GameServiceImpl(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Override
    public void create(Game game) {
        gamesRepository.save(game);
    }

    @Override
    public Iterable<Game> readAll() {
        return gamesRepository.findAll();
    }

    public Optional<Game> read(Long id) {
        return gamesRepository.findById(id);
    }

    @Override
    public boolean update(Game game, Long id) {
        if (gamesRepository.existsById(id)) {
            game.setId(id);
            gamesRepository.save(game);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (gamesRepository.existsById(id)) {
            gamesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}