package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
    @Autowired
    TransferDao transferDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AccountDao accountDao;



    @GetMapping(path = "/transfers/{id}")
    public Transfer getTransferById(int transfer_id) {

        Transfer output = transferDao.getTransferById(transfer_id);

        System.out.println(output);
        return output;
    }

    @GetMapping(path = "/accounts/transfers")
    public List<Transfer> listTransfers(Principal principal) {
        String username = principal.getName();
        int user_id = userDao.findIdByUsername(username);
        int account_id = userDao.AccountIdFromUserId(user_id);

        List<Transfer> output = transferDao.listTransfers(account_id);

        System.out.println(output);
        return output;
    }

//    @GetMapping(path = "/accounts/transfers")
//    public List<Transfer> pendingTransfers(Principal principal) {
//        String username = principal.getName();
//        int user_id = userDao.findIdByUsername(username);
//        int account_id = userDao.AccountIdFromUserId(user_id);
//
//        List<Transfer> output = transferDao.getPendingTransfers(account_id);
//
//        System.out.println(output);
//        return output;
//    }

   @PostMapping(path="/transfer")
    public int transfer( @RequestBody TransferDTO transferDto){

    return transferDao.transfer(transferDto.getUser_id_from(),transferDto);
   }

}
