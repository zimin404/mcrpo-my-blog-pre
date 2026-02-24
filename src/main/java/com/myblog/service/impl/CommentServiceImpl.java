package com.myblog.service.impl;

import com.myblog.dao.CommentDao;
import com.myblog.dto.CreateCommentRequest;
import com.myblog.dto.UpdateCommentRequest;
import com.myblog.model.Comment;
import com.myblog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        log.debug("Getting comments for post with id: {}", postId);
        return commentDao.findByPostId(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long commentId) {
        log.debug("Getting comment by id: {}", commentId);
        return commentDao.findById(commentId);
    }

    @Override
    @Transactional
    public Comment createComment(CreateCommentRequest request) {
        log.debug("Creating new comment for post with id: {}", request.getPostId());

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setPostId(request.getPostId());

        return commentDao.create(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long postId, Long commentId, UpdateCommentRequest request) {
        log.debug("Updating comment {} for post {}", commentId, postId);

        // 1. Проверяем существование комментария
        Comment comment = commentDao.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        // 2. Проверяем принадлежность к посту
        if (!comment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment " + commentId + " does not belong to post " + postId);
        }

        // 3. Обновляем текст комментария
        comment.setText(request.getText());

        // 4. Сохраняем изменения через DAO
        return commentDao.update(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        log.debug("Deleting comment {} for post {}", commentId, postId);

        // 1. Проверяем существование комментария
        Comment comment = commentDao.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        // 2. Проверяем принадлежность к посту
        if (!comment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment " + commentId + " does not belong to post " + postId);
        }

        // 3. Удаляем комментарий через DAO
        commentDao.delete(commentId);
    }
}
