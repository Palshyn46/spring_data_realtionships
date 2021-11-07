package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.GroupDao;
import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.dto.GroupDtoWithUsers;
import com.example.spring_data_relationships.entity.GroupEntity;
import com.example.spring_data_relationships.mappers.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GroupServiceImpl implements GroupService {
    GroupDao groupDao;
    GroupMapper groupMapper;
    UserService userService;

    public GroupServiceImpl(GroupDao groupDao, GroupMapper groupMapper) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
    }

    @Transactional
    @Override
    public GroupDto create(GroupDto group) {
        GroupEntity groupEntity = groupMapper.toEntity(group);
        groupEntity = groupDao.save(groupEntity);
        return groupMapper.toDto(groupEntity);
    }

    @Transactional
    @Override
    public Optional<GroupDto> get(Long id) {
        Optional<GroupEntity> group = groupDao.findById(id);
        return group.map(groupMapper::toDto);
    }

    @Transactional
    @Override
    public Optional<GroupDto> update(GroupDto group, Long id) {
        GroupEntity groupEntity = null;
        if (groupDao.existsById(id)) {
            group.setId(id);
            groupEntity = groupDao.save(groupMapper.toEntity(group));
        }
        return Optional.ofNullable(groupMapper.toDto(groupEntity));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (groupDao.existsById(id)) {
            groupDao.deleteById(id);
        }
    }

    @Transactional
    @Override
    public boolean existsById(Long id) {
        return groupDao.existsById(id);
    }

    @Override
    public GroupDtoWithUsers findGroupDtoWithUsers(Long id) {
        Optional<GroupEntity> group = groupDao.findById(id);
        //return group.map(groupMapper::toGroupDtoWithUsers).orElse(null);
        return null;
    }

    @Override
    public void deleteUserFromGroup(Long groupId, Long userId) {
        groupDao
                .findById(groupId)
                .map(usr -> {
                    usr.setUsers(null);
                    groupDao.save(usr);
                    return null;
                });
    }
}
