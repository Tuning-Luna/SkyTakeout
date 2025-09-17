package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.dto.CategoryPageQueryDTO;
import com.Tuning.entity.Category;
import com.Tuning.vo.CategoryQueryVO;
import com.github.pagehelper.Page;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
  // 插入一条分类数据
  @AutoFill(value = OperationType.INSERT)
  Integer insertCategory(Category category);

  // 分页查询数据
  Page<CategoryQueryVO> selectPage(CategoryPageQueryDTO dto);

  // 根据id删除分类（先确保此分类下没有dish/setmeal）
  Integer deleteById(@Param("id") Long id);

  // 根据id查询分类
  Category selectById(@Param("id") Long id);

  // 更新
  @AutoFill(value = OperationType.UPDATE)
  void update(Category category);

  // 列出数据，筛选type可选字段
  List<Category> list(Integer type);
}
