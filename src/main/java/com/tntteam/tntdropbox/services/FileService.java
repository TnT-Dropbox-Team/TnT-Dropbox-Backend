package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.repositories.FileRepository;
import com.tntteam.tntdropbox.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final GroupRepository groupRepository;

    public FileService(FileRepository fileRepository, GroupRepository groupRepository) {
        this.fileRepository = fileRepository;
        this.groupRepository = groupRepository;
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
    public List<File> getAllGroupFiles(long grId) {
        if (!groupRepository.existsById(grId))
            throw new ResourceNotFoundException("Group with id " + grId + " not found");
        return fileRepository.findAllByGroupId(grId);
    }
    public File getFile(long id) {
        return fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File with id " + id + " not found"));
    }
    public File uploadNewFile(File file) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public File uploadGroupFile(long grId, File file) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public File updateFile(long id, File file) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public void deleteFile(long id) {
        if (fileRepository.existsById(id))
            fileRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("File with id " + id + " not found");
    }
}