package com.meridian.api.users;

import com.meridian.api.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsersDTO createUser(UsersDTO usersDTO) {

        // Map DTO to entity
        Users users = modelMapper.map(usersDTO, Users.class);
        
        // Hash the password before saving
        users.setPassword(passwordEncoder.encode(usersDTO.getPassword()));

        // Save and return
        Users savedUser = usersRepository.save(users);
        return modelMapper.map(savedUser, UsersDTO.class);
    }

    public void deleteUserById(Long id) {

        if (!usersRepository.existsById(id)) {

            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        usersRepository.deleteById(id);
    }

    public List<UsersDTO> getAllUsers() {

        List<Users> list = usersRepository.findAll();

        return list.stream()
                .map(users -> modelMapper.map(users, UsersDTO.class))
                .collect(Collectors.toList());
    }

    public UsersDTO getUserById(Long id) {

        if (!usersRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        Optional<Users> user = usersRepository.findById(id);

        UsersDTO usersDTO = modelMapper.map(user.get(), UsersDTO.class);

        return usersDTO;
    }

    public UsersDTO updateUser(UsersDTO usersDTO, Long id) {

        Optional<Users> users = usersRepository.findById(id);

        return users
                .map(
                        user -> {
                            user.setFirstName(usersDTO.getFirstName());
                            user.setLastName(usersDTO.getLastName());
                            user.setUsername(usersDTO.getUsername());
                            // Hash the password if it's being updated
                            if (usersDTO.getPassword() != null && !usersDTO.getPassword().isEmpty()) {
                                user.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
                            }
                            Users savedUser = usersRepository.save(user);
                            return modelMapper.map(savedUser, UsersDTO.class);
                        })
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
}
