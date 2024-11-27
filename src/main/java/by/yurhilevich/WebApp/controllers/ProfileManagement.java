package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileManagement {
    @Autowired
    UserService userService;

    @PostMapping("/upload-profile-image")
    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile file, Model model) {
        if (!userService.setProfileImg(file)) {
            return "redirect:/profile?load_img_unsuccessful=true";
        }
        return "redirect:/profile?load_img_successful=true";
    }

    @PostMapping("/update_profile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirm_password") String passwordConf,
                                Model model) {
        if (userService.updateProfileInfo(name, email, password, passwordConf)) {
            // Получаем обновленного пользователя
            User updatedUser = userService.getAuthorizedUser();
            model.addAttribute("user", updatedUser);
            return "redirect:/profile?success=true";  // Перенаправляем на страницу профиля с флагом успеха
        }
        return "redirect:/profile?update_failed=true";  // Если обновление не удалось, перенаправляем с флагом ошибки
    }
}
