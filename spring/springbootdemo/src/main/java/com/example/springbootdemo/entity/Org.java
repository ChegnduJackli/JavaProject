package com.example.springbootdemo.entity;

import com.example.springbootdemo.service.OrgField;
import lombok.Data;

@Data
public class Org {
    @OrgField(id=1)
    String name;

    @OrgField(id=2)
    String taxNo;

    @OrgField(id=3)
    String buyerName;
}
