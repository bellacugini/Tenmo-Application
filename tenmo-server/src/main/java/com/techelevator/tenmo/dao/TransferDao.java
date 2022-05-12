package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.util.List;

public interface TransferDao {

        List<Transfer> listTransfers(long account_id);

        List<Transfer> getPendingTransfers(int account_to);

        Transfer createTransfer(Transfer transfer);

        Transfer getTransferById(int transfer_id);

        List<Transfer> getSentRequests(int account_id);

        int transfer(long user_id, TransferDTO transferDTO);
    }


