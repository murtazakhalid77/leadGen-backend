package com.leadgen.backend.service;

import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.UserRequest;

import java.io.IOException;
import java.util.List;

public interface UserRequestService extends GenericService<UserRequestDTO> {
    UserRequest saveUserRequest(RequestDto requestDto) throws IOException;
    List<RequestDto> getAllUserRequests(String phoneNumber);

    List<RequestDto> getSellerNotifications(String categoryName);
    List<UserRequestDTO> getAllRequests();
    UserRequest setAdminApprovalOfRequest(Long id);
    UserRequest cancelSellerRequest(Long id);
    UserRequest deleteSellerRequest(Long id);
    Boolean accept(Long id, String emailOFAcceptedUser, Long acceptedAmount);
    List<UserRequestDTO> getAllDeletedRequests();

}
