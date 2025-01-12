package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.FileAddDTO;
import com.tntteam.tntdropbox.dtos.FileGetDTO;
import com.tntteam.tntdropbox.dtos.FileGetDataDTO;
import com.tntteam.tntdropbox.exceptions.badRequest.BadRequest;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile({"file", "test"})
@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/user/{id}")
    public Page<FileGetDTO> getUserFiles(
            @PathVariable Long id,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("You cannot view another user's files");
        }
        return fileService.getAllUserFiles(
                id, searchQuery, page, size, sort);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/{id}")
    public FileGetDataDTO getFile(@PathVariable Long id) {
        return fileService.getFile(id);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/group/{id}")
    public Page<FileGetDTO> getGroupFiles(
            @PathVariable Long id,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        return fileService.getAllGroupFiles(
                id, searchQuery, page, size, sort);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FileGetDTO uploadNewFile(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String name = file.getOriginalFilename();

            if (name == null || name.trim().isEmpty()) {
                throw new BadRequest("File name is missing!");
            }

            byte[] fileData = file.getBytes();
            FileAddDTO fileAddDTO = new FileAddDTO(name, fileData);

            return fileService.uploadNewFile(fileAddDTO);
        } catch (IOException e) {
            throw new BadRequest("Invalid file data!");
        }
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PostMapping("{fileId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FileGetDTO linkFileToGroup(@PathVariable Long fileId, @PathVariable Long groupId) {
        return fileService.linkFileToGroup(fileId, groupId);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
