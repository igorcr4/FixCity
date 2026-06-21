package com.fixcity.fixcity.comment.controller;

import com.fixcity.fixcity.comment.request.CreateCommentRequest;
import com.fixcity.fixcity.comment.request.EditCommentRequest;
import com.fixcity.fixcity.comment.response.CommentResponse;
import com.fixcity.fixcity.comment.service.CommentService;
import com.fixcity.fixcity.user.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService service;

    @PostMapping("/report/{reportId}/create")
    public ResponseEntity<CommentResponse> createComment(@AuthenticationPrincipal UserPrincipal principal,
                                                         @PathVariable Long reportId,
                                                         @RequestBody CreateCommentRequest request) {
        CommentResponse comment = service.createComment(principal.id(), reportId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/by-report/{reportId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByReport(@PathVariable Long reportId,
                                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<CommentResponse> comments = service.getCommentsByReport(reportId, userPrincipal.id());
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<CommentResponse> editComment(@PathVariable Long id,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @RequestBody EditCommentRequest request) {
        CommentResponse comment = service.editComment(id, userPrincipal.id(), request);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
