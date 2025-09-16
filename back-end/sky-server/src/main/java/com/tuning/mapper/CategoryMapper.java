package com.tuning.mapper;

import com.Tuning.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
  Integer insertCategory(Category category);
}
