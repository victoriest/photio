package me.victoriest.photio.dao.mapper.source;

import me.victoriest.photio.model.entity.Evaluation;

public interface EvaluationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Evaluation record);

    int insertSelective(Evaluation record);

    Evaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Evaluation record);

    int updateByPrimaryKeyWithBLOBs(Evaluation record);

    int updateByPrimaryKey(Evaluation record);
}