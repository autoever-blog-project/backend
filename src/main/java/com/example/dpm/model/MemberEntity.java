package com.example.dpm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member")
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    private String socialId;

    private String nickname;
    
    private String profile_image;
    
    private int point;
    
    private boolean attendance;
    
    private boolean is_deleted;

    @Column(name = "refresh_token")
    private String refreshToken;

}
