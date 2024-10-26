/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.sesion.presentacion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import co.edu.unicauca.sesion.model.User;
import co.edu.unicauca.sesion.servicio.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.registerUser(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> foundUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (foundUser.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
@GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
    Optional<User> user = userService.findById(id); // Asegúrate de que userService tenga este método
    if (user.isPresent()) {
        return ResponseEntity.ok(user.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}
@GetMapping("/role")
public ResponseEntity<String> getUserRoleByEmail(@RequestParam String email) {
    // Asumiendo que tienes un método en el userService que obtiene el rol por correo electrónico
    String role = userService.getRoleByEmail(email); // Método que debes implementar en UserService
    if (role != null) {
        return ResponseEntity.ok(role);
    } else {
        return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el rol
    }
}
@GetMapping("/id")
public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
    Long userId = userService.getIdByEmail(email); // Método que debes implementar en UserService
    if (userId != null) {
        return ResponseEntity.ok(userId);
    } else {
        return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el ID
    }
}



}
