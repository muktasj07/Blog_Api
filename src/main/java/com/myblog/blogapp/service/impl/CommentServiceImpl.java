package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entities.Comment;
import com.myblog.blogapp.entities.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.repository.CommentRepository;
import com.myblog.blogapp.repository.PostRepository;
import com.myblog.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Component
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    
    private ModelMapper mapper;



    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Autowired
    private PostRepository postRepository;
    
    



    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = mapToComment(commentDto);

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return  mapToDTO(newComment);

    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
       return comments.stream().map(comment->mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.deleteById(commentId);
    }

    Comment mapToComment(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);

//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        return comment;

    }

    CommentDto mapToDTO(Comment comment){

        CommentDto map = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        
        return map;
    }
}
