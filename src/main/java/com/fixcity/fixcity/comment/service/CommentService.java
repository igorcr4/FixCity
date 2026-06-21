package com.fixcity.fixcity.comment.service;

import com.fixcity.fixcity.comment.Comment;
import com.fixcity.fixcity.comment.repository.CommentRepository;
import com.fixcity.fixcity.comment.request.CreateCommentRequest;
import com.fixcity.fixcity.comment.request.EditCommentRequest;
import com.fixcity.fixcity.comment.response.CommentResponse;
import com.fixcity.fixcity.report.exception.ReportNotFoundException;
import com.fixcity.fixcity.report.model.Report;
import com.fixcity.fixcity.report.service.ReportService;
import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final ReportService reportService;

    public CommentResponse createComment(Long userId, Long reportId, CreateCommentRequest request) {
        Report report = reportService.findReportById(reportId);
        User user = userService.findById(userId);
        Comment parentComment = null;

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setReport(report);
        comment.setText(request.text());
        comment.setCreatedAt(LocalDateTime.now());

        if(request.parentCommentId() != null) {
            parentComment = findCommentById(request.parentCommentId());
            comment.setParentComment(parentComment);
        }

        repository.save(comment);

        return new CommentResponse(
                comment.getId(),
                reportId,
                comment.getText(),
                comment.getCreatedAt(),
                null,
                user.getUsername(),
                userId,
                true,
                true,
                parentComment == null ? null : parentComment.getId()
        );
    }

    public List<CommentResponse> getCommentsByReport(Long reportId, Long userId) {
        Report report = reportService.findReportById(reportId);

        boolean isReportOwner = report.getUser().getId().equals(userId);

        return report.getComments().stream().map(comment -> {
            boolean isCommentOwner = comment.getUser().getId().equals(userId);

             return new CommentResponse(
                    comment.getId(),
                    report.getId(),
                    comment.getText(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt(),
                    comment.getUser().getUsername(),
                    comment.getUser().getId()   ,
                    isCommentOwner,
                    isReportOwner || isCommentOwner,
                     comment.getParentComment() == null ? null : comment.getParentComment().getId()
            );
        }).toList();
    }

    public void deleteComment(Long commentId) {

        //sa adaug validari cine poate sterge si cine nu
        Comment comment = findCommentById(commentId);
        repository.delete(comment);
    }

    public CommentResponse editComment (Long commentId, Long userId, EditCommentRequest request) {
        Comment comment = findCommentById(commentId);
        boolean isCommentOwner = comment.getUser().getId().equals(userId);

        if(!isCommentOwner) {
            throw new IllegalArgumentException();//exceptie personalizata trebuie sa adaug
        } else {
            comment.setText(request.text());
            comment.setUpdatedAt(LocalDateTime.now());
            repository.save(comment);
        }

        return new CommentResponse(
                comment.getId(),
                comment.getReport().getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser().getUsername(),
                comment.getUser().getId(),
                true,
                true,
                comment.getParentComment().getId()
        );
    }

    @Transactional
    public Comment findCommentById(Long commentId) {
        return repository.findById(commentId).orElseThrow();//exceptie personalizata
    }


}
