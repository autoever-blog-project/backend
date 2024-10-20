package com.example.dpm.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dpm.auth.exception.CustomException;
import com.example.dpm.auth.exception.ErrorCode;
import com.example.dpm.dto.MemberDto;
import com.example.dpm.model.MemberEntity;
import com.example.dpm.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	// MemberEntity를 DTO로 변환
    private MemberDto toDto(MemberEntity memberEntity) {
        return new MemberDto(
            memberEntity.getMember_id(),
            memberEntity.getSocialId(),
            memberEntity.getNickname(),
            memberEntity.getRefreshToken()
        );
    }

    // memberId로 찾은 후 DTO로 변환
    public MemberDto findById(int memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        
        return toDto(memberEntity);
    }

    public Optional<MemberEntity> findByRefreshToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken);
    }

    public MemberEntity save(MemberEntity memberEntity) {
        return memberRepository.save(memberEntity);
    }

    public MemberEntity update(MemberEntity memberEntity) {
        return memberRepository.save(memberEntity); // save 메서드는 존재하면 업데이트, 없으면 삽입
    }

    public MemberEntity updateRefreshToken(int member_id, String refreshToken) {
        Optional<MemberEntity> userOpt = memberRepository.findById(member_id);
        if (userOpt.isPresent()) {
        	MemberEntity memberEntity = userOpt.get();
        	memberEntity.setRefreshToken(refreshToken);
            return memberRepository.save(memberEntity);
        }
        return null;
    }
    
    // Optional<MemberEntity>를 MemberDto로 변환하는 메서드 추가
    public MemberDto getMemberDtoFromRefreshToken(String refreshToken) {
        Optional<MemberEntity> memberEntityOpt = findByRefreshToken(refreshToken);
        return memberEntityOpt.map(this::toDto).orElse(null);
    }
    
 // MemberDto를 MemberEntity로 변환하는 메서드 추가
    public MemberEntity toEntity(MemberDto memberDto) {
        return MemberEntity.builder()
                .member_id(memberDto.getMember_id())
                .socialId(memberDto.getSocialId())
                // 필요한 필드 추가
                .build();
    }
}