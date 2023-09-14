package com.ericdev.citrixincinterview.data.mapper

import com.ericdev.citrixincinterview.data.local.entity.LoggedInUserEntity
import com.ericdev.citrixincinterview.data.local.entity.UserEntity
import com.ericdev.citrixincinterview.data.local.entity.UserOrganizationEntity
import com.ericdev.citrixincinterview.data.local.entity.UserRoleEntity
import com.ericdev.citrixincinterview.data.remote.dto.organization.*
import com.ericdev.citrixincinterview.data.remote.dto.role.RoleDto
import com.ericdev.citrixincinterview.data.remote.dto.role.RoleResultDto
import com.ericdev.citrixincinterview.data.remote.dto.user.UserDto
import com.ericdev.citrixincinterview.data.remote.dto.user.UserOrganizationDto
import com.ericdev.citrixincinterview.data.remote.dto.user.UserResultDto
import com.ericdev.citrixincinterview.data.remote.dto.user.UserRoleDto
import com.ericdev.citrixincinterview.domain.model.organization.*
import com.ericdev.citrixincinterview.domain.model.role.Role
import com.ericdev.citrixincinterview.domain.model.role.RoleResult
import com.ericdev.citrixincinterview.domain.model.user.*

/** Organizations Mappers */
internal fun OrganizationResultDto.toOrganizationResult(): OrganizationResult {
    return OrganizationResult(
        organizations = organizations?.map { it.toOrganization() } ?: emptyList(),
        last = last ?: true,
        pageNo = pageNo ?: 0,
        pageSize = pageSize ?: 0,
        totalElements = totalElements ?: 0,
        totalPages = totalPages ?: 0
    )
}

internal fun OrganizationDto.toOrganization(): Organization {
    return Organization(
        active = active ?: true,
        contact = contact ?: "",
        createdOn = createdOn ?: "",
        email = email ?: "",
        id = id ?: "",
        organizationName = organizationName ?: "",
        organizationTypes = organizationTypesDto.toOrganizationTypes(),
        regions = regionsDto.toRegions(),
        territories = territoriesDto.toTerritories(),
        tin = tin ?: ""
    )
}

internal fun OrganizationTypesDto?.toOrganizationTypes(): OrganizationTypes {
    return OrganizationTypes(
        active = this?.active ?: true,
        createdOn = this?.createdOn ?: "",
        id = this?.id ?: "",
        organizationType = this?.organizationType ?: ""
    )
}

internal fun RegionsDto?.toRegions(): Regions {
    return Regions(
        active = this?.active ?: true,
        createdOn = this?.createdOn ?: "",
        id = this?.id ?: "",
        regionName = this?.regionName ?: ""
    )
}

internal fun TerritoriesDto?.toTerritories(): Territories {
    return Territories(
        active = this?.active ?: true,
        createdOn = this?.createdOn ?: "",
        id = this?.id ?: "",
        territoryName = this?.territoryName ?: ""
    )
}

/** Role mappers */
internal fun RoleResultDto.toRoleResult(): RoleResult {
    return RoleResult(
        roles = this.rolesDto?.map { it.toRole() } ?: emptyList(),
        last = last ?: true,
        pageNo = pageNo ?: 0,
        pageSize = pageSize ?: 0,
        totalElements = totalElements ?: 0,
        totalPages = totalPages ?: 0
    )
}

internal fun RoleDto.toRole(): Role {
    return Role(
        active = active ?: true,
        createdOn = createdOn ?: "",
        description = description ?: "",
        id = id ?: "",
        name = name ?: ""
    )
}

/**
 * User Mappers
 * */

internal fun UserResultDto.toUserResult(): UserResult {
    return UserResult(
        users = userDto?.map { it.toUser() } ?: emptyList(),
        last = last ?: true,
        pageSize = pageSize ?: 0,
        pageNo = pageNo ?: 0,
        totalPages = totalPages ?: 0,
        totalElements = totalElements ?: 0
    )
}

internal fun UserDto.toUser(): User {
    return User(
        active = active ?: true,
        createdOn = createdOn ?: "",
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        username = username ?: "",
        id = id ?: "",
        organization = organizationDto?.toUserOrganization() ?: UserOrganization("", ""),
        roles = rolesDto?.map { it.toUserRole() } ?: emptyList()
    )
}

internal fun UserOrganizationDto.toUserOrganization(): UserOrganization {
    return UserOrganization(
        id = id ?: "",
        organizationName = organizationName ?: ""
    )
}

internal fun UserRoleDto.toUserRole(): UserRole {
    return UserRole(
        active = active ?: true,
        createdOn = createdOn ?: "",
        description = description ?: "",
        id = id ?: "",
        name = name ?: ""
    )
}

/**
 * Entity Mappers
 * */

internal fun UserEntity.toUser(): User {
    return User(
        active = active,
        createdOn = createdOn,
        email = email,
        username = username,
        firstName = firstName,
        id = id,
        lastName = lastName,
        organization = organization.toUserOrganization(),
        roles = roles.map { it.toUserRole() }
    )
}

internal fun UserEntity.toUserResult(): UserResult {
    return UserResult(
        users = listOf(
            User(
                active = active,
                createdOn = createdOn,
                email = email,
                username = username,
                firstName = firstName,
                id = id,
                lastName = lastName,
                organization = organization.toUserOrganization(),
                roles = roles.map { it.toUserRole() }
            )
        ),
        last = true,
        pageNo = 1,
        pageSize = 0,
        totalElements = 0,
        totalPages = 1
    )
}

internal fun UserResult.toUserEntity(): List<UserEntity> {
    return users.map { it.toUserEntity() }
}

internal fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        active = active,
        createdOn = createdOn,
        email = email,
        username = username,
        firstName = firstName,
        lastName = lastName,
        organization = organization.toUserOrganizationEntity(),
        roles = roles.map { it.toUserRoleEntity() }
    )
}

internal fun UserOrganizationEntity.toUserOrganization(): UserOrganization {
    return UserOrganization(id = organization_id, organizationName = organizationName)
}

internal fun UserOrganization.toUserOrganizationEntity(): UserOrganizationEntity {
    return UserOrganizationEntity(organization_id = id, organizationName = organizationName)
}

internal fun UserRoleEntity.toUserRole(): UserRole {
    return UserRole(
        active = active,
        createdOn = createdOn,
        description = description,
        id = id,
        name = name
    )
}

internal fun UserRole.toUserRoleEntity(): UserRoleEntity {
    return UserRoleEntity(
        active = active,
        createdOn = createdOn,
        description = description,
        id = id,
        name = name
    )
}

internal fun LoggedInUserEntity.toLoggedInUser(): LoggedInUser? {
    return LoggedInUser(id = userId)
}

internal fun LoggedInUser.toLoggedUserEntity(): LoggedInUserEntity {
    return LoggedInUserEntity(userId = id)
}
