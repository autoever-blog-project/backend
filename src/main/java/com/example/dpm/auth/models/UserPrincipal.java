package com.example.dpm.auth.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.dpm.auth.enums.MemberRole;
import com.example.dpm.member.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	@Setter
	private Map<String, Object> attributes;

	public static UserPrincipal create(MemberDto memberDto) {
		List<GrantedAuthority> authorities = Collections
				.singletonList(new SimpleGrantedAuthority(MemberRole.USER.getRole()));
		return new UserPrincipal(memberDto.getMember_id(), memberDto.getSocialId(), "", authorities, null);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}

}