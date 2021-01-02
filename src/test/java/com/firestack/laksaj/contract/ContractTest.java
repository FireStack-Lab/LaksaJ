package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static com.firestack.laksaj.account.Wallet.pack;

public class ContractTest {

    @Test
    public void deploy() throws Exception {
        String code = "scilla_version 0\n" +
                "\n" +
                "(* This contract implements a fungible token interface a la ERC20.*)\n" +
                "\n" +
                "(***************************************************)\n" +
                "(*               Associated library                *)\n" +
                "(***************************************************)\n" +
                "library FungibleToken\n" +
                "\n" +
                "let one = Uint128 1\n" +
                "let zero = Uint128 0\n" +
                "\n" +
                "let min_int =\n" +
                "  fun (a : Uint128) => fun (b : Uint128) =>\n" +
                "  let alt = builtin lt a b in\n" +
                "  match alt with\n" +
                "  | True =>\n" +
                "    a\n" +
                "  | False =>\n" +
                "    b\n" +
                "  end\n" +
                "\n" +
                "let le_int =\n" +
                "  fun (a : Uint128) => fun (b : Uint128) =>\n" +
                "    let x = builtin lt a b in\n" +
                "    match x with\n" +
                "    | True => True\n" +
                "    | False =>\n" +
                "      let y = builtin eq a b in\n" +
                "      match y with\n" +
                "      | True => True\n" +
                "      | False => False\n" +
                "      end\n" +
                "    end\n" +
                "\n" +
                "(* returns singleton List Message *)\n" +
                "let one_msg =\n" +
                "    fun (msg : Message) =>\n" +
                "        let nil_msg = Nil {Message} in\n" +
                "        Cons {Message} msg nil_msg\n" +
                "\n" +
                "\n" +
                "(***************************************************)\n" +
                "(*             The contract definition             *)\n" +
                "(***************************************************)\n" +
                "\n" +
                "contract FungibleToken\n" +
                "(owner : ByStr20,\n" +
                " total_tokens : Uint128,\n" +
                " decimals : Uint32,\n" +
                " name : String,\n" +
                " symbol : String)\n" +
                "\n" +
                "(* Initial balance is not stated explicitly: it's initialized when creating the contract. *)\n" +
                "\n" +
                "field balances : Map ByStr20 Uint128 =\n" +
                "  let m = Emp ByStr20 Uint128 in\n" +
                "    builtin put m owner total_tokens\n" +
                "field allowed : Map ByStr20 (Map ByStr20 Uint128) = Emp ByStr20 (Map ByStr20 Uint128)\n" +
                "\n" +
                "transition BalanceOf (tokenOwner : ByStr20)\n" +
                "  bal <- balances[tokenOwner];\n" +
                "  match bal with\n" +
                "  | Some v =>\n" +
                "    msg = { _tag : \"BalanceOfResponse\"; _recipient : _sender; _amount : zero;\n" +
                "            address : tokenOwner; balance : v};\n" +
                "    msgs = one_msg msg;\n" +
                "    send msgs\n" +
                "  | None =>\n" +
                "    msg = { _tag : \"BalanceOfResponse\"; _recipient : _sender; _amount : zero;\n" +
                "            address : tokenOwner; balance : zero};\n" +
                "    msgs = one_msg msg;\n" +
                "    send msgs\n" +
                "  end\n" +
                "end\n" +
                "\n" +
                "transition TotalSupply ()\n" +
                "  msg = { _tag : \"TotalSupplyResponse\"; _recipient : _sender; _amount : zero;\n" +
                "          caller : _sender; totalSupply : total_tokens};\n" +
                "  msgs = one_msg msg;\n" +
                "  send msgs\n" +
                "end\n" +
                "\n" +
                "transition Transfer (to : ByStr20, tokens : Uint128)\n" +
                "  bal <- balances[_sender];\n" +
                "  match bal with\n" +
                "  | Some b =>\n" +
                "    can_do = le_int tokens b;\n" +
                "    match can_do with\n" +
                "    | True =>\n" +
                "      (* subtract tokens from _sender and add it to \"to\" *)\n" +
                "      new_sender_bal = builtin sub b tokens;\n" +
                "      balances[_sender] := new_sender_bal;\n" +
                "\n" +
                "      (* Adds tokens to \"to\" address *)\n" +
                "      to_bal <- balances[to];\n" +
                "      new_to_bal = match to_bal with\n" +
                "      | Some x => builtin add x tokens\n" +
                "      | None => tokens\n" +
                "      end;\n" +
                "\n" +
                "  \t  balances[to] := new_to_bal;\n" +
                "      msg = { _tag : \"TransferSuccess\"; _recipient : _sender; _amount : zero;\n" +
                "              sender : _sender; recipient : to; amount : tokens};\n" +
                "      msgs = one_msg msg;\n" +
                "      send msgs\n" +
                "    | False =>\n" +
                "      (* balance not sufficient. *)\n" +
                "      msg = { _tag : \"TransferFailure\"; _recipient : _sender; _amount : zero;\n" +
                "              sender : _sender; recipient : to; amount : zero};\n" +
                "      msgs = one_msg msg;\n" +
                "      send msgs\n" +
                "    end\n" +
                "  | None =>\n" +
                "    (* no balance record, can't transfer *)\n" +
                "    msg = { _tag : \"TransferFailure\"; _recipient : _sender; _amount : zero;\n" +
                "            sender : _sender; recipient : to; amount : zero};\n" +
                "    msgs = one_msg msg;\n" +
                "    send msgs\n" +
                "  end\n" +
                "end\n" +
                "\n" +
                "transition TransferFrom (from : ByStr20, to : ByStr20, tokens : Uint128)\n" +
                "  bal <- balances[from];\n" +
                "  (* Check if _sender has been authorized by \"from\" *)\n" +
                "  sender_allowed_from <- allowed[from][_sender];\n" +
                "  match bal with\n" +
                "  | Some a =>\n" +
                "    match sender_allowed_from with\n" +
                "    | Some b =>\n" +
                "        (* We can only transfer the minimum of available or authorized tokens *)\n" +
                "        t = min_int a b;\n" +
                "        can_do = le_int tokens t;\n" +
                "        match can_do with\n" +
                "        | True =>\n" +
                "            (* tokens is what we should subtract from \"from\" and add to \"to\" *)\n" +
                "            new_from_bal = builtin sub a tokens;\n" +
                "            balances[from] := new_from_bal;\n" +
                "            to_bal <- balances[to];\n" +
                "            match to_bal with\n" +
                "            | Some tb =>\n" +
                "                new_to_bal = builtin add tb tokens;\n" +
                "                balances[to] := new_to_bal\n" +
                "            | None =>\n" +
                "                (* \"to\" has no balance. So just set it to tokens *)\n" +
                "                balances[to] := tokens\n" +
                "            end;\n" +
                "            (* reduce \"allowed\" by \"tokens\" *)\n" +
                "            new_allowed = builtin sub b tokens;\n" +
                "            allowed[from][_sender] := new_allowed;\n" +
                "            msg = { _tag : \"TransferFromSuccess\"; _recipient : _sender; _amount : zero;\n" +
                "                    sender : from; recipient : to; amount : tokens };\n" +
                "            msgs = one_msg msg;\n" +
                "            send msgs\n" +
                "        | False =>\n" +
                "            msg = { _tag : \"TransferFromFailure\"; _recipient : _sender; _amount : zero;\n" +
                "                    sender : from; recipient : to; amount : zero };\n" +
                "            msgs = one_msg msg;\n" +
                "            send msgs\n" +
                "        end\n" +
                "    | None =>\n" +
                "        msg = { _tag : \"TransferFromFailure\"; _recipient : _sender; _amount : zero;\n" +
                "                sender : from; recipient : to; amount : zero };\n" +
                "        msgs = one_msg msg;\n" +
                "        send msgs\n" +
                "    end\n" +
                "  | None =>\n" +
                "\tmsg = { _tag : \"TransferFromFailure\"; _recipient : _sender; _amount : zero;\n" +
                "            sender : from; recipient : to; amount : zero };\n" +
                "    msgs = one_msg msg;\n" +
                "    send msgs\n" +
                "  end\n" +
                "end\n" +
                "\n" +
                "transition Approve (spender : ByStr20, tokens : Uint128)\n" +
                "  allowed[_sender][spender] := tokens;\n" +
                "  msg = { _tag : \"ApproveSuccess\"; _recipient : _sender; _amount : zero;\n" +
                "          approver : _sender; spender : spender; amount : tokens };\n" +
                "  msgs = one_msg msg;\n" +
                "  send msgs\n" +
                "end\n" +
                "\n" +
                "transition Allowance (tokenOwner : ByStr20, spender : ByStr20)\n" +
                "  spender_allowance <- allowed[tokenOwner][spender];\n" +
                "  match spender_allowance with\n" +
                "  | Some n =>\n" +
                "      msg = { _tag : \"AllowanceResponse\"; _recipient : _sender; _amount : zero;\n" +
                "              owner : tokenOwner; spender : spender; amount : n };\n" +
                "      msgs = one_msg msg;\n" +
                "      send msgs\n" +
                "  | None =>\n" +
                "      msg = { _tag : \"AllowanceResponse\"; _recipient : _sender; _amount : zero;\n" +
                "              owner : tokenOwner; spender : spender; amount : zero };\n" +
                "      msgs = one_msg msg;\n" +
                "      send msgs\n" +
                "  end\n" +
                "end";
        List<Value> init = Arrays.asList(
                Value.builder().vname("_scilla_version").type("Uint32").value("0").build(),
                Value.builder().vname("owner").type("ByStr20").value("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a").build(),
                Value.builder().vname("total_tokens").type("Uint128").value("1000000000").build(),
                Value.builder().vname("decimals").type("Uint32").value("0").build(),
                Value.builder().vname("name").type("String").value("BobCoin").build(),
                Value.builder().vname("symbol").type("String").value("BOB").build());
        Wallet wallet = new Wallet();
        wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930");
        ContractFactory factory = ContractFactory.builder().provider(new HttpProvider("https://dev-api.zilliqa.com/")).signer(wallet).build();
        Contract contract = factory.newContract(code, (Value[]) init.toArray(), "");
        Integer nonce = Integer.valueOf(factory.getProvider().getBalance("9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a").getResult().getNonce());
        DeployParams deployParams = DeployParams.builder().version(String.valueOf(pack(333, 1))).gasPrice("1000000000").gasLimit("10000").nonce(String.valueOf(nonce + 1)).senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a").build();
        System.out.println(contract.deployWithoutConfirm(deployParams));
//        Pair<Transaction, Contract> result = contract.deploy(deployParams, 3000, 3);

//        System.out.println("result is: " + result.toString());
//
//        String transactionFee = new BigInteger(result.getKey().getReceipt().getCumulative_gas()).multiply(new BigInteger(result.getKey().getGasPrice())).toString();
//        System.out.println("transaction fee is: " + transactionFee);
    }

    @Test
    public void call() throws Exception {

        List<Value> init = Arrays.asList(
                Value.builder().vname("_scilla_version").type("Uint32").value("0").build(),
                Value.builder().vname("owner").type("ByStr20").value("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a").build(),
                Value.builder().vname("total_tokens").type("Uint128").value("1000000000").build(),
                Value.builder().vname("decimals").type("Uint32").value("0").build(),
                Value.builder().vname("name").type("String").value("BobCoin").build(),
                Value.builder().vname("symbol").type("String").value("BOB").build());
        Wallet wallet = new Wallet();
        wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930");
        ContractFactory factory = ContractFactory.builder().provider(new HttpProvider("https://dev-api.zilliqa.com/")).signer(wallet).build();
        Contract contract = factory.atContract("zil1h4cesgy498wyyvsdkj7g2zygp0xj920jw2hyx0", "", (Value[]) init.toArray(), "");
        Integer nonce = Integer.valueOf(factory.getProvider().getBalance("9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a").getResult().getNonce());
        CallParams params = CallParams.builder().nonce(String.valueOf(nonce + 1)).version(String.valueOf(pack(333, 1))).gasPrice("1000000000").gasLimit("1000").senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a").amount("0").build();
        List<Value> values = Arrays.asList(Value.builder().vname("to").type("ByStr20").value("0x381f4008505e940ad7681ec3468a719060caf796").build(), Value.builder().vname("tokens").type("Uint128").value("10").build());
        contract.call("Transfer", (Value[]) values.toArray(), params, 3000, 3);
    }


    @Test
    public void getAddressForContract() throws NoSuchAlgorithmException {
        Transaction transaction = Transaction.builder().build();
        transaction.setSenderPubKey("0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A");
        transaction.setNonce("19");
        String address = ContractFactory.getAddressForContract(transaction);
        Assert.assertEquals(address.toLowerCase(), "8f14cb1735b2b5fba397bea1c223d65d12b9a887");
    }
}
