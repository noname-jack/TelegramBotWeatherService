package ru.tn.nnjack.TelegramBotWeatherService.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.db.repository.GeographicalObjectRepository;
import ru.tn.nnjack.TelegramBotWeatherService.db.repository.UserRepository;
import ru.tn.nnjack.TelegramBotWeatherService.dto.GeographicalObjectDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DbService {
    private final UserRepository userRepository;
    private final GeographicalObjectRepository geographicalObjectRepository;

    public DbService(UserRepository userRepository, GeographicalObjectRepository geographicalObjectRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.geographicalObjectRepository = geographicalObjectRepository;

    }

    @Transactional
    public User findOrCreateAndUpdateUser(UpdateInfo updateInfo) {
        Optional<User> userOptional = userRepository.findById(updateInfo.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean updated = false;

            if (!updateInfo.getChatId().equals(user.getChatId())) {
                user.setChatId(updateInfo.getChatId());
                updated = true;
            }
            if (!updateInfo.getUserName().equals(user.getUserName())) {
                user.setUserName(updateInfo.getUserName());
                updated = true;
            }
            if (!updateInfo.getFirstName().equals(user.getFirstName())) {
                user.setFirstName(updateInfo.getFirstName());
                updated = true;
            }
            if (!updateInfo.getLastName().equals(user.getLastName())) {
                user.setLastName(updateInfo.getLastName());
                updated = true;
            }


            if (updated) {
                userRepository.save(user);
            }
            return user;
        } else {

            User newUser = new User();
            newUser.setId(updateInfo.getUserId());
            newUser.setChatId(updateInfo.getChatId());
            newUser.setUserName(updateInfo.getUserName());
            newUser.setFirstName(updateInfo.getFirstName());
            newUser.setLastName(updateInfo.getLastName());
            return userRepository.save(newUser);
        }
    }

    @Transactional
    public List<GeographicalObject> saveGeographicalObjects(List<GeographicalObjectDTO> geographicalObjectDTOListRu, List<GeographicalObjectDTO> geographicalObjectDTOListEN){
        List<GeographicalObject> geographicalObjectList = new ArrayList<GeographicalObject>();
        for (GeographicalObjectDTO dtoRu : geographicalObjectDTOListRu) {
            for (GeographicalObjectDTO dtoEn : geographicalObjectDTOListEN) {
                if (dtoRu.getId() == dtoEn.getId()) {
                    Optional<GeographicalObject> geographicalObjectOptional = geographicalObjectRepository.findById(dtoRu.getId());
                    if(geographicalObjectOptional.isPresent()){
                        GeographicalObject geographicalObject = geographicalObjectOptional.get();
                        geographicalObjectList.add(geographicalObject);
                    }
                    else{
                        GeographicalObject newGeographicalObject = new GeographicalObject();
                        newGeographicalObject.setId(dtoRu.getId());
                        newGeographicalObject.setCountry(dtoRu.getCountry());
                        newGeographicalObject.setNameRu(dtoRu.getName());
                        newGeographicalObject.setDistrictRu(dtoRu.getDistrict());
                        newGeographicalObject.setSubDistrictRu(dtoRu.getSubDistrict());
                        newGeographicalObject.setKindRu(dtoRu.getKind());
                        newGeographicalObject.setNameEn(dtoEn.getName());
                        newGeographicalObject.setDistrictEn(dtoEn.getDistrict());
                        newGeographicalObject.setSubDistrictEn(dtoEn.getSubDistrict());
                        newGeographicalObject.setKindEn(dtoEn.getKind());
                        geographicalObjectRepository.save(newGeographicalObject);
                        geographicalObjectList.add(newGeographicalObject);
                    }
                }
            }
        }
        return geographicalObjectList;
    }

    public Set<GeographicalObject> findGeographicalObjectsByUserId(Long userId){
        return geographicalObjectRepository.findByUsersId(userId);
    }

    public Optional<GeographicalObject> findGeographicalObjectsById(int id){
        return geographicalObjectRepository.findById(id);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteGeographicalObjectFromUser(User user, GeographicalObject geographicalObject) {
        User managedUser = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Hibernate.initialize(managedUser.getGeographicalObjects());
        managedUser.getGeographicalObjects().remove(geographicalObject);
        userRepository.save(managedUser);
    }

    @Transactional
    public void addGeographicalObjectToUser(User user, GeographicalObject geographicalObject) {
        User managedUser = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Hibernate.initialize(managedUser.getGeographicalObjects());
        managedUser.getGeographicalObjects().add(geographicalObject);
        userRepository.save(managedUser);
    }
}
