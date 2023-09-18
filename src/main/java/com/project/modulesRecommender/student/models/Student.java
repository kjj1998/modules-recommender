package com.project.modulesRecommender.student.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.modulesRecommender.auth.models.Role;
import com.project.modulesRecommender.module.models.Module;
import lombok.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Student")
public class Student implements UserDetails {
    @Version Long version;
    @Id
    @Property("student_id")
    private String studentId;
    @Property("password")
    private String password;
    @Property("email")
    private String email;
    @Property("major")
    private String major;
    @Property("first_name")
    private String firstName;
    @Property("last_name")
    private String lastName;
    @Property("year_of_study")
    private Integer yearOfStudy;
    @JsonProperty("modules")
    @Relationship(type = "TAKES", direction = Relationship.Direction.OUTGOING)
    private List<Module> modules;
    @Property("role")
    private Role role;
    @Property("disciplines")
    private List<String> disciplines;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return studentId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getCourseCodes() {
        List<String> courseCodes = new ArrayList<>();

        for (Module module : modules) {
            courseCodes.add(module.getCourseCode());
        }

        return courseCodes;
    }
}