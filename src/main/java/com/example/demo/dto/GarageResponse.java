package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class GarageResponse {

  private List<ResponseMessage> responseMessageList;
}
