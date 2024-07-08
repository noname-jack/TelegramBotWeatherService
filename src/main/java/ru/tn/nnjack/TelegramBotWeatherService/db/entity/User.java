package ru.tn.nnjack.TelegramBotWeatherService.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tn.nnjack.TelegramBotWeatherService.db.enums.UserState;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name="user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private  String lastName;

    @Column(name = "language")
    private String language = "ru";

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UserState state = UserState.BASIC_STATE;

    @ManyToMany
    @JoinTable(
            name = "user_geographical_object",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "geographical_object_id")
    )
    private Set<GeographicalObject> geographicalObjects;
}
