package by.yurhilevich.WebApp.service;


import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isUserExists(String email, String phone, String username) {
        return (userRepository.findByEmail(email).isPresent() || userRepository.findByPhone(phone).isPresent() || userRepository.findByUsername(username).isPresent());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void changeUserRole(Long userId, User.Role role) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setRole(role);
            userRepository.save(currentUser);
        }
    }

    public User getAuthorizedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username).orElse(null);
        }

        return null;
    }


    @Transactional
    public boolean setProfileImg(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }

        Path uploadDirectory = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/images");
        String fileName = getAuthorizedUser().getId().toString();
        String fullName = uploadDirectory + "/" + fileName + ".png";
        File targetFile = new File(fullName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        User currentUser = getAuthorizedUser();
        currentUser.setImageUrl("/images/" + fileName + ".png");
        userRepository.save(currentUser);

        UserDetails updatedUserDetails = loadUserByUsername(currentUser.getEmail());
        updateUserInSecurityContext(updatedUserDetails);  // Актуализируем данные сессии
        return true;
    }


    @Transactional
    public boolean updateProfileInfo(String name, String email, String password, String confirmPassword) {
        User user = getAuthorizedUser();

        if (user == null) {
            return false;
        }

        // Обновление имени пользователя
        if (!user.getUsername().equals(name)) {
            if (userRepository.findByUsername(name).isPresent()) {
                return false;
            }
            user.setUsername(name);
        }

        // Обновление email
        if (!user.getEmail().equals(email)) {
            if (userRepository.findByEmail(email).isPresent()) {
                return false;
            }
            user.setEmail(email);
        }

        // Обновление пароля
        if (!password.isEmpty() && password.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        // Сохраняем пользователя
        userRepository.save(user);

        // Перезагружаем пользователя через email (или username)
        UserDetails updatedUserDetails = loadUserByUsername(user.getEmail());  // или user.getUsername()

        // Обновляем контекст безопасности
        updateUserInSecurityContext(updatedUserDetails);

        return true;
    }


    private void updateUserInSecurityContext(UserDetails updatedUserDetails) {
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if (currentAuthentication != null) {
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    updatedUserDetails,  // Обновленный пользователь
                    updatedUserDetails.getPassword(),  // Пароль (хешированный)
                    currentAuthentication.getAuthorities()  // Роли
            );

            // Обновляем контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }
    }



}