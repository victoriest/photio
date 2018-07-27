package me.victoriest.photio.dao.mapper.source;

import me.victoriest.photio.model.entity.Invitation;

public interface InvitationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Invitation record);

    int insertSelective(Invitation record);

    Invitation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Invitation record);

    int updateByPrimaryKey(Invitation record);
}