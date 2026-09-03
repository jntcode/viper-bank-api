package com.viperbank.api.service;

import com.viperbank.api.dto.UserDTO;
import com.viperbank.api.model.User;
import com.viperbank.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
        return UserDTO.fromEntity(user);
    }

    public UserDTO create(UserDTO userDTO) {
        User user = userDTO.toEntity();
        User saved = userRepository.save(user);
        return UserDTO.fromEntity(saved);
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        existing.setName(userDTO.getName());

        if (userDTO.getAccount() != null) {
            existing.setAccount(userDTO.getAccount().toEntity());
        }
        if (userDTO.getCard() != null) {
            existing.setCard(userDTO.getCard().toEntity());
        }
        if (userDTO.getFeatures() != null) {
            existing.setFeatures(userDTO.getFeatures().stream()
                    .map(f -> f.toEntity())
                    .collect(Collectors.toList()));
        }
        if (userDTO.getNews() != null) {
            existing.setNews(userDTO.getNews().stream()
                    .map(n -> n.toEntity())
                    .collect(Collectors.toList()));
        }

        User saved = userRepository.save(existing);
        return UserDTO.fromEntity(saved);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
        userRepository.deleteById(id);
    }
}
