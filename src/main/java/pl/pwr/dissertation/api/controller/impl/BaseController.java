package pl.pwr.dissertation.api.controller.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.pwr.dissertation.api.exception.NotFoundException;
import pl.pwr.dissertation.logic.domain.FileWrapper;
import pl.pwr.dissertation.logic.domain.User;
import pl.pwr.dissertation.persistance.entity.UserEntity;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class BaseController {

    protected static final CacheControl CACHE_CONTROL_12_HOURS = CacheControl.maxAge(43200, TimeUnit.SECONDS)
            .noTransform()
            .mustRevalidate();

    protected User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUser(auth);
    }

    protected User getUser(Authentication auth) {
        UserEntity principal = (UserEntity) auth.getPrincipal();
        User user = new User();
        BeanUtils.copyProperties(principal, user);

        user.setRoles(new HashSet<>(principal.getRoles()));

        return user;
    }

    protected ResponseEntity<byte[]> generateFileResponse(FileWrapper fileWrapper, boolean withCache) {

        if (fileWrapper == null) {
            throw new NotFoundException("File not found");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileWrapper.getFileName());

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileWrapper.getContent().length)
                .contentType(MediaType.valueOf(fileWrapper.getMime()));

        if (withCache) {
            bodyBuilder = bodyBuilder.cacheControl(CACHE_CONTROL_12_HOURS);
        }

        return bodyBuilder.body(fileWrapper.getContent());
    }
}
