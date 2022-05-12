package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;


    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Override
//    public List<Transfer> listTransfers(int account_id) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT * FROM transfer JOIN account ON account.account_id = transfer.transfer_id WHERE transfer_id = ?;";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, account_id);
//        while (result.next()) {
//            Transfer thisTransfer = transferObjectMapper(result);
//            transfers.add(thisTransfer);
//        }
//        return transfers;
//    }

    @Override
    public List<Transfer> listTransfers(long user_id) {
        List<Transfer> listOfTransfers = new ArrayList<>();
        String sql =  "select * from transfer " +
                "Where account_to = ? OR account_from = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,user_id,user_id);
        while (results.next()) {
            Transfer transfer = transferObjectMapper(results);
            listOfTransfers.add(transfer);
        }
        return listOfTransfers;
    }

    @Override
    public List<Transfer> getPendingTransfers(int user_id) {

//        List<Transfer> pendingTransfers = new ArrayList<>();
//        String sql = "select transfer_id from transfer " +
//                "Where transfer_status_id = 1 and account_id = ?";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, account_id);
//        while (result.next()) {
//            Transfer transfer = transferObjectMapper(result);
//            pendingTransfers.add(transfer);
//        }
//        return pendingTransfers;

        List<Transfer> pendingTransfers = new ArrayList<>();
        String sql = "SELECT t.*, u.username AS userFrom, v.username AS userTo FROM transfers t " +
                "JOIN accounts a ON t.account_from = a.account_id " +
                "JOIN accounts b ON t.account_to = b.account_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "JOIN users v ON b.user_id = v.user_id " +
                "WHERE transfer_status_id = 1 AND (account_from = ? OR account_to = ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id, user_id);
        while (results.next()) {
            Transfer transfer = transferObjectMapper(results);
            pendingTransfers.add(transfer);
        }
        return pendingTransfers;

    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql,
                Integer.class,
                transfer.getTransfer_type_id(),
                transfer.getTransfer_status_id(),
                transfer.getAccount_from(),
                transfer.getAccount_to(),
                transfer.getAmount());

        Transfer newTransfer = null;
        if (newId != null) {
            newTransfer = getTransferById(newId);
        }

        return newTransfer;
    }

    @Override
    public Transfer getTransferById(int transfer_id) {
//        Transfer transfer = null;
//        String sql = "Select * from transfer where transfer_id=?";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transfer_id);
//        transfer = transferObjectMapper(result);
//        return transfer;
        return null;
    }

    @Override
    public List<Transfer> getSentRequests(int account_id) {
        return null;
    }

    @Override
    public int transfer(long user_id, TransferDTO transferDTO) {

        String sql = "START TRANSACTION;" +
                "UPDATE account SET balance = balance - ? WHERE user_id = ?;" +
                "UPDATE account SET balance = balance + ? WHERE user_id = ?;" +
                "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(?, ?, " +
                "(SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?);" +
//                " RETURNING transfer_id;"+
                "COMMIT;";
      jdbcTemplate.update(sql,  transferDTO.getAmount(),user_id,transferDTO.getAmount(),transferDTO.getUser_id_to(),
                2, 2, user_id, transferDTO.getUser_id_to(), transferDTO.getAmount());
        // transfer_type_id - 2 for SEND
        // transfer_status_id - 2 for Approved

        return 1;
    }

    private Transfer transferObjectMapper(SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(result.getLong("transfer_id"));
        transfer.setTransfer_type_id(result.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(result.getInt("transfer_status_id"));
        transfer.setAccount_from(result.getInt("account_from"));
        transfer.setAccount_to(result.getInt("account_to"));
        transfer.setAmount(result.getDouble("amount"));
        return transfer;
    }

}
