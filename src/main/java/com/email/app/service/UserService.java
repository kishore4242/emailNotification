package com.email.app.service;

import com.email.app.model.Confirmation;
import com.email.app.model.User;
import com.email.app.repository.ConfirmationRepository;
import com.email.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    public User saveUser(User user){
        if((userRepository.existsByEmail(user.getEmail())) && user.isEnabled()){
            throw new RuntimeException("User already exists");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            User avaliableUser = userRepository.getByEmail(user.getEmail());
            emailService.sendReverification(avaliableUser.getName(),avaliableUser.getEmail(),confirmationRepository.getReferenceById(avaliableUser.getId()).getToken());
            return avaliableUser;
        }

        user.setEnabled(false);
        userRepository.save(user);
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        emailService.sendMail(user.getName(),user.getEmail(),confirmation.getToken());
        return user;
    }

    public boolean verifyToken(String token){
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        confirmationRepository.deleteById(confirmation.getUser().getId());
        return Boolean.TRUE;
    }
}
