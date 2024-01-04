package com.ms.email.dtos;

import java.util.ArrayList;

import com.ms.email.models.EmailModel;

public record EmailRecordDto(ArrayList<EmailModel> emails) {

}
