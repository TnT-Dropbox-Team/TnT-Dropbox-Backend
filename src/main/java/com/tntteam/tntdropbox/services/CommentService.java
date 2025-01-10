package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.CommentDTO;
import com.tntteam.tntdropbox.dtos.CommentInputDTO;
import com.tntteam.tntdropbox.dtos.SimpleUserDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.Comment;
import com.tntteam.tntdropbox.models.Group;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.CommentRepository;
import com.tntteam.tntdropbox.repositories.GroupRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(GroupRepository groupRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Page<CommentDTO> getAllComments(long groupId, long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (!group.getUsers().contains(user)) {
            throw new ForbiddenException("You do not have permission to access this group");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> commentPage = commentRepository.findByGroupId(groupId, pageable);
        return commentPage.map(comment -> {
            User author = comment.getUser();
            SimpleUserDTO authorDTO = new SimpleUserDTO(
                    author.getId(),
                    author.getUsername(),
                    author.getFirstName(),
                    author.getLastName()
            );
            return new CommentDTO(
                    comment.getId(),
                    comment.getText(),
                    comment.getCreatedAt(),
                    comment.getEditedAt(),
                    authorDTO
            );
        });
    }

    public CommentDTO uploadComment(long groupId, long userId, CommentInputDTO commentInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (!group.getUsers().contains(user)) {
            throw new ForbiddenException("You do not have permission to access this group");
        }

        Comment comment = new Comment();
        comment.setText(commentInput.getText());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setGroup(group);
        commentRepository.save(comment);

        SimpleUserDTO authorDTO = new SimpleUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getEditedAt(),
                authorDTO
        );
    }

    public CommentDTO editComment(long commentId, long userId, CommentInputDTO commentInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (user != comment.getUser()) {
            throw new ForbiddenException("You do not have permission to edit this comment");
        }

        comment.setText(commentInput.getText());
        comment.setEditedAt(LocalDateTime.now());
        commentRepository.save(comment);

        SimpleUserDTO authorDTO = new SimpleUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getEditedAt(),
                authorDTO
        );
    }

    public void deleteComment(long commentId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (user != comment.getUser()) {
            throw new ForbiddenException("You do not have permission to delete this comment");
        }
        commentRepository.delete(comment);
    }
}
