package me.victoriest.photio.dao.mapper.source;

import java.util.List;
import me.victoriest.photio.model.entity.Schedule;
import me.victoriest.photio.model.entity.ScheduleExample;
import org.apache.ibatis.annotations.Param;

public interface ScheduleMapper {
    long countByExample(ScheduleExample example);

    int deleteByExample(ScheduleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    List<Schedule> selectByExampleWithBLOBs(ScheduleExample example);

    List<Schedule> selectByExample(ScheduleExample example);

    Schedule selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Schedule record, @Param("example") ScheduleExample example);

    int updateByExampleWithBLOBs(@Param("record") Schedule record, @Param("example") ScheduleExample example);

    int updateByExample(@Param("record") Schedule record, @Param("example") ScheduleExample example);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKeyWithBLOBs(Schedule record);

    int updateByPrimaryKey(Schedule record);
}