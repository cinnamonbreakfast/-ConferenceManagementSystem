package service;

import dto.LoginCredentials;
import dto.UserDTO;
import dto.UserRegisterDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginAccount(LoginCredentials loginCredentials)
    {
        User requestUser = this.userRepository.findByUsernameAndPassword(loginCredentials.getUsername(), loginCredentials.getPassword());

        return requestUser;
    }

    public User registerNewUserAccount(UserRegisterDTO accountDto) {
//        if (emailExist(accountDto.getEmail())) {
//            throw new EmailExistsException(
//                    "There is an account with that email adress:" + accountDto.getEmail());
//        }

        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());

//        user.setRole(new Role(Integer.valueOf(1), user));

        return userRepository.save(user);
    }
}
