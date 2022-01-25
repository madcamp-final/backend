package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.*;
import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final PreferRepository preferRepository;
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;
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

    public DefaultResponse getPreferList(Integer product_id){
        List<Forms.PreferListComponentForm> prefers = preferRepository.getPrefersFromPid(product_id);
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.PREFERS_FOUND, prefers);
    }

    public DefaultResponse startTrade(Forms.PreferForm preferForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.TOKEN_FAILED);
        }
        if (authentication.getName().equals("anonymousUser")){
            return DefaultResponse.res(StatusCode.NEED_REFRESH, ResponseMessage.REQUIRES_TOKEN_UPDATE);
        }
        Optional<Users> users = usersRepository.findById(authentication.getName());
        if (!users.isPresent()) {
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
        Users seller = users.get();
        Optional<Users> users2 = usersRepository.findById(preferForm.getUid());
        if (!users2.isPresent()){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
        Users buyer = users2.get();
        Optional<Products> products = productsRepository.findById(preferForm.getProduct_id());
        if (!products.isPresent()){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PRODUCT);
        }
        Products product = products.get();
        List<Trade> trades = tradeRepository.checkTradeExists(seller.getUid(), buyer.getUid(), product.getPid());
        if (trades.size() != 0){
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.TRADE_ALREADY_EXISTS);
        }
        Trade trade = Trade.builder()
                .buyer(buyer.getUid())
                .seller(seller.getUid())
                .product_id(product.getPid())
                .bill(product.getPrice())
                .completion(0)
                .buyer_confirm(0)
                .seller_confirm(0)
                .build();
        tradeRepository.save(trade);
        buyer.setPoints(-product.getPrice());
        usersRepository.save(buyer);
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.TRADE_REGISTERED, trade);
    }

    public DefaultResponse acceptTrade(Integer tid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.TOKEN_FAILED);
        }
        if (authentication.getName().equals("anonymousUser")){
            return DefaultResponse.res(StatusCode.NEED_REFRESH, ResponseMessage.REQUIRES_TOKEN_UPDATE);
        }
        Optional<Users> users = usersRepository.findById(authentication.getName());
        if (!users.isPresent()) {
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
        Users mySelf = users.get();
        Optional<Trade> tenantTrade = tradeRepository.findById(tid);
        if (!tenantTrade.isPresent()) {
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.TRADE_NOT_FOUND);
        }
        Trade trade = tenantTrade.get();
        if (trade.getSeller().equals(mySelf.getUid())){
            //seller
            trade.setSeller_confirm(1);
            if (trade.getBuyer_confirm() == 1) {
                trade.setCompletion(1);
            }
        } else if (trade.getBuyer().equals(mySelf.getUid())){
            //buyer
            trade.setBuyer_confirm(1);
            if (trade.getSeller_confirm() == 1) {
                trade.setCompletion(1);
            }
        }
        if (trade.getCompletion() == 1){
            Optional<Users> users1 = usersRepository.findById(trade.getSeller());
            if (users1.isPresent()){
                Users user = users1.get();
                user.setPoints(trade.getBill());
                usersRepository.save(user);
            }
        }
        tradeRepository.save(trade);
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.ACCEPT_DONE, trade);
    }
}
