package com.example.hotelmanagementsystem.models;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@MappedSuperclass
@Getter
public class CommonInfo {

    private String createdBy;

    @CreationTimestamp
    private String createdDate;
    private String modifiedBy;
    public void setModifiedBy(String email)
    {
        if(email==null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            email=userDetails.getUsername();
        }
        this.modifiedBy=email;
    }
    @UpdateTimestamp
    private String modifiedDate;

    public void setCreatedBy(String email)
    {
        if(email==null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            email=userDetails.getUsername();
        }
        this.createdBy=email;
    }
}
