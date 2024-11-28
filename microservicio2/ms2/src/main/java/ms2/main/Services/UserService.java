package ms2.main.Services;

import ms2.main.Entities.User;
import ms2.main.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ArrayList<User> getUsers(){
        return(ArrayList<User>) userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) throws Exception {
        try{
            userRepository.deleteById(id);
            return true;
        } catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
