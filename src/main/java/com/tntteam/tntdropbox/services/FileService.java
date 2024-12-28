package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.FileGetDTO;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.repositories.FileRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }
    public Page<FileGetDTO> getAllUserFiles(Long userId, String searchQuery, String fileType, int page, int size, String[] sort) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        searchQuery = (searchQuery == null || searchQuery.trim().isEmpty()) ? "%" : "%" + searchQuery.trim() + "%";
        fileType = (fileType == null || fileType.trim().isEmpty()) ? "%" : "%" + fileType.trim() + "%";
        Pageable pageable = buildPageable(page, size, sort);
        Page<File> filePage = fileRepository.findByUserIdAndNameLikeAndTypeLike(userId, searchQuery, fileType, pageable);
        return filePage.map(file -> new FileGetDTO(
                file.getId(),
                file.getName(),
                file.getSize(),
                file.getType(),
                file.getCreatedAt(),
                file.getUpdatedAt()
        ));
    }
    private Pageable buildPageable(int page, int size, String[] sort) {
        if (sort == null || sort.length == 0) {
            return PageRequest.of(page, size, Sort.unsorted());
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sort.length; i += 2) {
            String field = sort[i];
            String direction = sort[i + 1];
            orders.add(new Sort.Order(Sort.Direction.fromString(direction), field));
        }
        return PageRequest.of(page, size, Sort.by(orders));
    }
    public File getFile(long id) {
        return fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File with id " + id + " not found"));
    }
    public File uploadNewFile(File file) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public File uploadGroupFile(Long grId, File file) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public File updateFile(Long id, File file) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public void deleteFile(Long id) {
        if (!fileRepository.existsById(id))
            throw new ResourceNotFoundException("File with id " + id + " not found");
        fileRepository.deleteById(id);
    }
}