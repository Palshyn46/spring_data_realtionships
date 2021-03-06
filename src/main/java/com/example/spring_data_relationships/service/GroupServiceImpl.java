package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.GroupDao;
import com.example.spring_data_relationships.dao.UserDao;
import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.dto.GroupDtoWithUsers;
import com.example.spring_data_relationships.entity.GroupEntity;
import com.example.spring_data_relationships.entity.UserEntity;
import com.example.spring_data_relationships.mappers.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public class GroupServiceImpl implements GroupService {
    GroupDao groupDao;
    GroupMapper groupMapper;
    UserDao userDao;

    public GroupServiceImpl(GroupDao groupDao, GroupMapper groupMapper) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
    }

    @Autowired
    public void setUserService(UserDao userDao) {
        this.userDao = userDao;
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

    @Transactional
    @Override
    public GroupDtoWithUsers findGroupDtoWithUsers(Long id) {
        Optional<GroupEntity> group = groupDao.findById(id);
        return group.map(groupMapper::toGroupDtoWithUsers).orElse(null);
    }

    @Transactional
    @Override
    public void addUserToGroup(Long userId, Long groupId) {
        userDao
                .findById(userId)
                .map(usr -> {
                    groupDao
                            .findById(groupId)
                            .map(grp -> {
                                Set<UserEntity> users = grp.getUsers();
                                users.add(usr);
                                grp.setUsers(users);
                                groupDao.save(grp);

                                // usr.setGroup?
                                return null;
                            });
                    return null;
                });
    }

    @Transactional
    @Override
    public void deleteUserFromGroup(Long userId, Long groupId) {
        userDao
                .findById(userId)
                .map(usr -> {
                    groupDao
                            .findById(groupId)
                            .map(grp -> {
                                Set<UserEntity> users = grp.getUsers();
                                users.remove(usr);
                                grp.setUsers(users);
                                return null;
                            });
                    return null;
                });
    }
}
