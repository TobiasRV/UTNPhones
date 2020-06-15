package com.utn.utnphones.controller;


import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.dto.UserLoginDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.Validation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final CityService cityService;

    @Autowired
    public UserController(UserService userService, CityService cityService) {
        this.userService = userService;
        this.cityService = cityService;
    }

    /* TODO
     *   ESTE ES EL ENDPOINT PARA INICIAR SESION, TODAVIA NO TIENE NINGUN CHECKEO, HABRIA QUE
     *   COMPARAR EL USERNAME Y PASSWORD QUE RECIBO CON LOS QUE TENGO EN LA BASE DE DATOS.
     *   UNA VEZ VALIDADO SE GENERA EL TOKEN DE SEGURIDAD CON getJWTToken(username),
     *   SE PUEDE GENERAR UN TOKEN CON CUALQUIER DATOS QUE LE PASES, EN ESTE CASO SOLO SE LE PASA EL USERNAME
     * */
    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> login(@RequestParam("username") String username, @RequestParam("password") String password) throws InvalidLoginException, ValidationException {
        UserLoginDto userDto = new UserLoginDto();

        if (username != null && password != null) {
            User user = userService.getUserByUsernameAndPassword(username, password);
            String token = getJWTToken(username, user.getRole());
            userDto.setUsername(username);
            userDto.setToken(token);

        } else
            throw new ValidationException("Fields username and password must not be null");

        return ResponseEntity.ok(userDto);
    }


    /* TODO
     *   ESTE METODO ES EL QUE GENERA EL TOKEN
     *   1) "mySecretKey" ES LA CONTRASEÑA PARA DESENCRIPTAR EL TOKEN
     *   2) "grantedAuthorities" CONTIENE LA LISTA DE ROLES O PERMISOS DEL USUARIO LOGEADO, EL TOKEN VA A DECIR SI UN
     *    USUARIO TIENE PERMISO O NO PARA LA PETICION QUE QUIERE REALIZAR.
     *      2.1) SPRING MANEJA LOS ROLES CON LA FORMA "ROLE_*****", ENTONCES LO QUE DEBERIAMOS HACER ES CONCATENAR
     *      "ROLE_"+user.getRole()" ASI NOS QUEDA ROLE_ADMIN, ROLE_INFRAESTRUCTURE,ETC.
     *   3) EL ".setId("softtekJWT")" DE LA LINEA 79 NO SE QUE SIGNIFICA, HAY QUE INVESTIGAR LA LIBRERIA JWTS, DEBE SER
     *      SOLO UN ID.
     *   4) TODOS LOS TOKENS TIENEN UN PREFIJO "BEARER", NO SE SI ES POR STANDARD O PORQUE EL TIPO DEL TUTORIAL LE GUSTA
     *      HACERLO ASI. DE CUALQUIER FORMA HAY UNA FUNCION EN EL FILTRO QUE LEE LOS TOKENS QUE PIDE DOS PARAMETROS, ESE
     *      PREFIJO Y EL TOKEN CONCATENADO.
     * */
    private String getJWTToken(String username, UserRole userRole) {

        String secretKey = "ultraMegaSecuredKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + userRole);

        String token = Jwts
                .builder()
                .setId("UTNPhonesToken")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }


    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAll() {

        List<User> userList = userService.getAll();

        if (userList.size() > 0) {
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) throws UserNotFoundException {
        User u = userService.getUserById(userId);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/")
    public ResponseEntity addUser(@RequestBody @Valid User u) throws CityNotFoundException, ValidationException {

        if (!cityService.existsById(u.getCity().getIdCity()))
            throw new CityNotFoundException();

        User newUser = userService.addUser(u);
        return ResponseEntity.created(getLocation(newUser)).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId, @RequestBody UpdateUserDto u) throws DataIntegrityViolationException, UserNotFoundException {
        userService.updateUser(userId, u);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //returns the location of the last inserted user through a header
    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getIdUser())
                .toUri();
    }
}

