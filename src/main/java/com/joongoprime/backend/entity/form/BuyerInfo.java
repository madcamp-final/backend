package com.joongoprime.backend.entity.form;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@NoArgsConstructor
@Data
public class BuyerInfo {
    private String code;
    private String message;
    private JSONObject response = new JSONObject();
}
