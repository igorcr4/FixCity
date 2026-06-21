package com.fixcity.fixcity.comment.request;

public record CreateCommentRequest(
        String text,
        Long parentCommentId
) {
}
