package com.nafisulbari.eib.service;

import com.nafisulbari.eib.model.MedicalRecord;
import com.nafisulbari.eib.model.User;

public interface UserService {

     User findUserById(Long userId);

     void save(User user);


}
