package pl.pwr.dissertation.logic.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pl.pwr.dissertation.logic.domain.UserDto;
import pl.pwr.dissertation.persistance.entity.LecturerEntity;

@Service
public class LecturerServiceImpl {

    public static UserDto mapToDomain(LecturerEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDto lecturer = new UserDto();
        BeanUtils.copyProperties(entity, lecturer);
        return lecturer;
    }
}
