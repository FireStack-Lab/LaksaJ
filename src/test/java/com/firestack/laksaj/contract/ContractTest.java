package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.google.common.io.ByteSink;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static com.firestack.laksaj.account.Wallet.pack;

public class ContractTest {

    @Test
    public void deploy() throws NoSuchAlgorithmException {
        String code = "scilla_version 0\n" +
                "\n" +
                "    (* HelloWorld contract *)\n" +
                "\n" +
                "    import ListUtils\n" +
                "\n" +
                "    (***************************************************)\n" +
                "    (*               Associated library                *)\n" +
                "    (***************************************************)\n" +
                "    library HelloWorld\n" +
                "\n" +
                "    let one_msg =\n" +
                "      fun (msg : Message) =>\n" +
                "      let nil_msg = Nil {Message} in\n" +
                "      Cons {Message} msg nil_msg\n" +
                "\n" +
                "    let not_owner_code = Int32 1\n" +
                "    let set_hello_code = Int32 2\n" +
                "\n" +
                "    (***************************************************)\n" +
                "    (*             The contract definition             *)\n" +
                "    (***************************************************)\n" +
                "\n" +
                "    contract HelloWorld\n" +
                "    (owner: ByStr20)\n" +
                "\n" +
                "    field welcome_msg : String = \"\"\n" +
                "\n" +
                "    transition setHello (msg : String)\n" +
                "      is_owner = builtin eq owner _sender;\n" +
                "      match is_owner with\n" +
                "      | False =>\n" +
                "        msg = {_tag : \"Main\"; _recipient : _sender; _amount : Uint128 0; code : not_owner_code};\n" +
                "        msgs = one_msg msg;\n" +
                "        send msgs\n" +
                "      | True =>\n" +
                "        welcome_msg := msg;\n" +
                "        msg = {_tag : \"Main\"; _recipient : _sender; _amount : Uint128 0; code : set_hello_code};\n" +
                "        msgs = one_msg msg;\n" +
                "        send msgs\n" +
                "      end\n" +
                "    end\n" +
                "\n" +
                "\n" +
                "    transition getHello ()\n" +
                "        r <- welcome_msg;\n" +
                "        e = {_eventname: \"getHello()\"; msg: r};\n" +
                "        event e\n" +
                "    end";
        List<Value> init = Arrays.asList(Value.builder().vname("_scilla_version").type("Uint32").value("0").build(), Value.builder().vname("owner").type("ByStr20").value("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a").build());
        Wallet wallet = new Wallet();
        wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930");
        ContractFactory factory = ContractFactory.builder().provider(new HttpProvider("https://api.zilliqa.com/")).signer(wallet).build();
        Contract contract = factory.newContract(code, (Value[]) init.toArray(), "");
        DeployParams deployParams = DeployParams.builder().version(String.valueOf(pack(2, 8))).gasPrice("1000000000").gasLimit("10000").senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a").build();
        Pair<Transaction, Contract> result = contract.deploy(deployParams, 300, 3);
        System.out.println("result is: " + result);
    }

    @Test
    public void call() {
        String code = "scilla_version 0\n" +
                "\n" +
                "    (* HelloWorld contract *)\n" +
                "\n" +
                "    import ListUtils\n" +
                "\n" +
                "    (***************************************************)\n" +
                "    (*               Associated library                *)\n" +
                "    (***************************************************)\n" +
                "    library HelloWorld\n" +
                "\n" +
                "    let one_msg =\n" +
                "      fun (msg : Message) =>\n" +
                "      let nil_msg = Nil {Message} in\n" +
                "      Cons {Message} msg nil_msg\n" +
                "\n" +
                "    let not_owner_code = Int32 1\n" +
                "    let set_hello_code = Int32 2\n" +
                "\n" +
                "    (***************************************************)\n" +
                "    (*             The contract definition             *)\n" +
                "    (***************************************************)\n" +
                "\n" +
                "    contract HelloWorld\n" +
                "    (owner: ByStr20)\n" +
                "\n" +
                "    field welcome_msg : String = \"\"\n" +
                "\n" +
                "    transition setHello (msg : String)\n" +
                "      is_owner = builtin eq owner _sender;\n" +
                "      match is_owner with\n" +
                "      | False =>\n" +
                "        msg = {_tag : \"Main\"; _recipient : _sender; _amount : Uint128 0; code : not_owner_code};\n" +
                "        msgs = one_msg msg;\n" +
                "        send msgs\n" +
                "      | True =>\n" +
                "        welcome_msg := msg;\n" +
                "        msg = {_tag : \"Main\"; _recipient : _sender; _amount : Uint128 0; code : set_hello_code};\n" +
                "        msgs = one_msg msg;\n" +
                "        send msgs\n" +
                "      end\n" +
                "    end\n" +
                "\n" +
                "\n" +
                "    transition getHello ()\n" +
                "        r <- welcome_msg;\n" +
                "        e = {_eventname: \"getHello()\"; msg: r};\n" +
                "        event e\n" +
                "    end";

        List<Value> init = Arrays.asList(Value.builder().vname("_scilla_version").type("Uint32").value("0").build(), Value.builder().vname("owner").type("ByStr20").value("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a").build());

        Wallet wallet = new Wallet();
        wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930");
        ContractFactory factory = ContractFactory.builder().provider(new HttpProvider("https://api.zilliqa.com/")).signer(wallet).build();
        Contract contract = factory.atContract("a7c88a90eb79740fc730397557f77f36fd52a04c", code, (Value[]) init.toArray(), "");
        CallParams params = CallParams.builder().version(String.valueOf(pack(2, 8))).gasPrice("1000000000").gasLimit("10000").senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a").amount("0").build();
        contract.call(Transition.builder().name("getHello").params(new Field[]{}).build(), (Value[]) init.toArray(), params, 300, 3);


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
