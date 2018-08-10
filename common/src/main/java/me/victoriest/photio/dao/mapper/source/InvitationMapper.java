package me.victoriest.photio.dao.mapper.source;

import java.util.List;
import me.victoriest.photio.model.entity.Invitation;
import me.victoriest.photio.model.entity.InvitationExample;
import org.apache.ibatis.annotations.Param;

public interface InvitationMapper {
    long countByExample(InvitationExample example);

    int deleteByExample(InvitationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Invitation record);

    int insertSelective(Invitation record);

    List<Invitation> selectByExample(InvitationExample example);

    Invitation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Invitation record, @Param("example") InvitationExample example);

    int updateByExample(@Param("record") Invitation record, @Param("example") InvitationExample example);

    int updateByPrimaryKeySelective(Invitation record);

    int updateByPrimaryKey(Invitation record);
}