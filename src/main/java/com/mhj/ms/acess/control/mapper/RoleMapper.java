package com.mhj.ms.acess.control.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.mhj.ms.acess.control.auth.entity.Role;
import com.mhj.ms.acess.control.dto.response.RoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
	Role toEntity(RoleDto dto);
	
	RoleDto toDto(Role role);
	
	List<RoleDto> toDto(Collection<Role> roles);

}
