package ru.tn.nnjack.TelegramBotWeatherService.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;

import java.util.Set;

@Repository
public interface GeographicalObjectRepository extends JpaRepository<GeographicalObject, Integer> {
    Set<GeographicalObject> findByUsersId(Long userId);
}
