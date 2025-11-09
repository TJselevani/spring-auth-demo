package com.example.authorite.config;

import com.example.authorite.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || !(authentication.getPrincipal() instanceof User user)) {
            return false;
        }

        String requiredPermission = permission.toString();

        log.info("Evaluating permission: {} for user: {}", requiredPermission, user.getUsername());

        // Example ABAC rules:
        switch (requiredPermission) {
            case "OWN_PROFILE":
                return targetDomainObject instanceof Long targetUserId && user.getId().equals(targetUserId);

            case "DEPARTMENT_ACCESS":
                return targetDomainObject instanceof String targetDepartment &&
                        user.getDepartment().equalsIgnoreCase(targetDepartment);

            case "ACTIVE_USER":
                return user.isEnabled();

            default:
                return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}

//This allows expressions like:
//@PreAuthorize("hasPermission(#userId, 'OWN_PROFILE')")
//@PreAuthorize("hasPermission(#department, 'DEPARTMENT_ACCESS')")
//@PreAuthorize("hasPermission(null, 'ACTIVE_USER')")

