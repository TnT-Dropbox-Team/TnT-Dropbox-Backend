package com.tntteam.tntdropbox.services;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public String getAllComments(long grId) {
        return "Seznam vseh komentarjev v skupini: " + grId;
    }
    public String uploadComment(long grId) {
        return "dodaj komentar v skupino: " + grId;
    }
    public String editComment(long id) {
        return "uredi komentar: " + id;
    }
    public String deleteComment(long id) {
        return "Izbriši komentar: " + id;
    }
}
