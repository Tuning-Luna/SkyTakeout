package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.entity.AddressBook;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {
  // 条件查询
  List<AddressBook> list(AddressBook addressBook);

  // 新增
  @AutoFill(OperationType.INSERT)
  void insert(AddressBook addressBook);

  // id查询
  AddressBook getById(Long id);

  // 修改
  @AutoFill(OperationType.UPDATE)
  void update(AddressBook addressBook);

  // 修改某个地址是否是默认地址
  void updateIsDefaultByUserId(AddressBook addressBook);

  void deleteById(Long id);
}
