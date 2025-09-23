package com.tuning.controller.user;

import com.Tuning.context.BaseContext;
import com.Tuning.entity.AddressBook;
import com.Tuning.result.ApiResult;
import com.tuning.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {
  final private AddressBookService addressBookService;

  @Autowired
  public AddressBookController(AddressBookService addressBookService) {
    this.addressBookService = addressBookService;
  }

  @GetMapping("/list")
  public ApiResult<List<AddressBook>> list() {
    AddressBook addressBook = new AddressBook();
    addressBook.setUserId(BaseContext.getCurrentId());
    List<AddressBook> list = addressBookService.list(addressBook);
    return ApiResult.ok(list);
  }

  @PostMapping
  public ApiResult<String> save(@RequestBody AddressBook addressBook) {
    addressBookService.save(addressBook);
    return ApiResult.ok();
  }

  @GetMapping("/{id}")
  public ApiResult<AddressBook> getById(@PathVariable Long id) {
    AddressBook addressBook = addressBookService.getById(id);
    return ApiResult.ok(addressBook);
  }

  @PutMapping
  public ApiResult<String> update(@RequestBody AddressBook addressBook) {
    addressBookService.update(addressBook);
    return ApiResult.ok();
  }

  @PutMapping("/default")
  public ApiResult<String> setDefault(@RequestBody AddressBook addressBook) {
    addressBookService.setDefault(addressBook);
    return ApiResult.ok();
  }

  @DeleteMapping
  public ApiResult<String> deleteById(Long id) {
    addressBookService.deleteById(id);
    return ApiResult.ok();
  }

  @GetMapping("default")
  public ApiResult<AddressBook> getDefault() {
    // SQL:select * from address_book where user_id = ? and is_default = 1
    AddressBook addressBook = new AddressBook();
    addressBook.setIsDefault(1);
    addressBook.setUserId(BaseContext.getCurrentId());
    List<AddressBook> list = addressBookService.list(addressBook);

    if (list != null && list.size() == 1) {
      return ApiResult.ok(list.get(0));
    }

    return ApiResult.fail(HttpStatus.BAD_REQUEST, "没有查询到默认地址");
  }
}
