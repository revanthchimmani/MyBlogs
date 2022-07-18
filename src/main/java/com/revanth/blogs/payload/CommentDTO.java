package com.revanth.blogs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "user name should have at lease 2 characters")
    private String name;
    @NotEmpty
    @Email(message = "user should provide valid email address")
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comment should have at lease 10 characters")
    private String body;
}
