package com.fixcity.fixcity.comment.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        Long reportId,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String username,
        Long userId,
        Boolean canEdit,
        Boolean canDelete,
        Long parentCommentId
) {
}
