package ru.tn.nnjack.TelegramBotWeatherService.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
