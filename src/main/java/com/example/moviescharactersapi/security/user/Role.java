package com.example.moviescharactersapi.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.moviescharactersapi.security.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    USER_READ
            )
    ),

    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    ADMIN_DELETE,

                    MANAGER_CREATE,
                    MANAGER_UPDATE,
                    MANAGER_READ,
                    MANAGER_DELETE
            )
    ),

    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_UPDATE,
                    MANAGER_READ,
                    MANAGER_DELETE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        //usa el getter de permissions
        var authorities = getPermissions()
                .stream()
        //Crea un list de obj simplegrad.. y le carga el nombre del permiso
                .map( permissions -> new SimpleGrantedAuthority( permissions.getPermission() ) )
        //Crea una lista con los obj simplegrand..
                .collect( Collectors.toList() );

        //Aqui se pasa el nombre del rol this(el rol sobre el cual se esta trabajando).name(por ejemplo admin)
        //Se agrega "ROLE_" porque Spring necesita que comience con esa nomenclatura
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.name() ) );

        return authorities;
    }
}
