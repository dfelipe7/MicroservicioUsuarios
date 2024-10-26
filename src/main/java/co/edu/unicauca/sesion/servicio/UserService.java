/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.sesion.servicio;

import co.edu.unicauca.sesion.dao.UserRepository;
import co.edu.unicauca.sesion.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String email, String password, String role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashPassword(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        String hashedPassword = hashPassword(password);
        return userRepository.findByEmail(email)
            .filter(user -> user.getPassword().equals(hashedPassword));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error de hashing", e);
        }
    }
      public Optional<User> findById(Long id) {
        return userRepository.findById(id); // Asumiendo que tu repositorio tiene este método
    }
      
        // Método para obtener el rol del usuario
    public String getRoleById(Long id) {
        return userRepository.findById(id)
            .map(User::getRole)
            .orElse(null); // Devuelve null si no se encuentra el usuario
    }

    // Método para obtener el ID del usuario por email
    public Long getIdByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(User::getId) // Asumiendo que tienes un método getId en la clase User
            .orElse(null); // Devuelve null si no se encuentra el usuario
    }
    public String getRoleByEmail(String email) {
    return userRepository.findByEmail(email)
        .map(User::getRole) // Asumiendo que User tiene un método getRole
        .orElse(null); // Devuelve null si no se encuentra el usuario
}

}
