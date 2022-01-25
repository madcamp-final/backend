package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Prefer;
import com.joongoprime.backend.entity.TradeRepository;
import com.joongoprime.backend.entity.PreferRepository;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final PreferRepository preferRepository;
    private final TradeRepository tradeRepository;

    public DefaultResponse preferProduct(String uid, Integer product_id){
        if (preferRepository.getPreferFromUidAndPid(uid, product_id).size() != 0) {
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_REQUESTED);
        }
        try {
            Prefer prefer = Prefer.builder()
                    .user_id(uid)
                    .product_id(product_id)
                    .build();
            preferRepository.save(prefer);
        } catch (Exception e){
            e.printStackTrace();
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.DENIED);
        }
        List<Prefer> prefers = preferRepository.getPreferFromUidAndPid(uid, product_id);
        if (prefers == null) {
            return DefaultResponse.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.REQUEST_FAILED);
        }

        return DefaultResponse.res(StatusCode.OK, ResponseMessage.REQUESTED, prefers.get(0));
    }
}
