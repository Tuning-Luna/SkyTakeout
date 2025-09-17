package com.tuning.mapper;

import com.Tuning.dto.CategoryPageQueryDTO;
import com.Tuning.entity.Category;
import com.Tuning.vo.CategoryQueryVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
  // 插入一条分类数据
  Integer insertCategory(Category category);

  // 分页查询数据
  Page<CategoryQueryVO> selectPage(CategoryPageQueryDTO dto);

}
