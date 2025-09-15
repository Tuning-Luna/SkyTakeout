package com.Tuning.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {

  private long total; // 总记录数

  // 形参化类 'List' 的原始使用
  private List<T> records; // 当前页数据集合
}
