# LaksaJ
LaksaJ -- Zilliqa Blockchain Java Library 

The project is still under development.

## Quick Start

```java
package com.firestack.example;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.contract.Contract;
import com.firestack.laksaj.contract.ContractFactory;
import com.firestack.laksaj.contract.DeployParams;
import com.firestack.laksaj.contract.Value;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionFactory;
import javafx.util.Pair;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static com.firestack.laksaj.account.Wallet.pack;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Wallet wallet = new Wallet();
        String ptivateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        // Populate the wallet with an account
        String address = wallet.addByPrivateKey(ptivateKey);
        System.out.println("address is: " + address);

        HttpProvider provider = new HttpProvider("https://api.zilliqa.com");
        //get balance
        HttpProvider.BalanceResult balanceResult = provider.getBalance(address);
        System.out.println("balance is: " + balanceResult.getBalance());

        //construct non-contract transaction
        Transaction transaction = Transaction.builder()
                .version(String.valueOf(pack(2, 8)))
                .toAddr("4baf5fada8e5db92c3d3242618c5b47133ae003c".toLowerCase())
                .senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a")
                .amount("10000000")
                .gasPrice("1000000000")
                .gasLimit("1")
                .code("")
                .data("")
                .provider(new HttpProvider("https://api.zilliqa.com/"))
                .build();

        //sign transaction
        transaction = wallet.sign(transaction);
        System.out.println("signature is: " + transaction.getSignature());

        //send transaction
        HttpProvider.CreateTxResult result = TransactionFactory.sendTransaction(transaction);
        System.out.println(result);

        //hello-world contract
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

        ContractFactory factory = ContractFactory.builder().provider(new HttpProvider("https://api.zilliqa.com/")).signer(wallet).build();
        Contract contract = factory.newContract(code, (Value[]) init.toArray(), "");
        DeployParams deployParams = DeployParams.builder().version(String.valueOf(pack(2, 8))).gasPrice("1000000000").gasLimit("10000").senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a").build();

        //deploy contract, this will take a while to track transaction util it been confirmed or failed
        Pair<Transaction, Contract> deployResult = contract.deploy(deployParams, 300, 3);
        System.out.println("result is: " + deployResult);
    }
}
```

## Supports

### Account API

- [x] fromFile
- [x] toFile

### Wallet API

- [x] createAccount
- [x] addByPrivateKey addByKeyStore
- [x] remove
- [x] setDefault
- [x] signTransaction (default account)
- [x] signTransactionWith (specific account)

### TransactionFactory Transaction

- [x] sendTransaction
- [x] trackTx
- [x] confirm
- [x] isPending isInitialised isConfirmed isRejected

### ContractFactory Contract

- [x] deploy
- [x] call
- [x] isInitialised isDeployed isRejected
- [x] getState
- [x] getAddressForContract


### Crypto API

- [x] getDerivedKey (PBKDF2 and Scrypt)
- [x] generatePrivateKey
- [x] Schnorr.sign
- [x] Schnorr.verify
- [x] getPublicKeyFromPrivateKey
- [x] getPublicKeyFromPrivateKey
- [x] getAddressFromPublicKey
- [x] getAddressFromPrivateKey
- [x] encryptPrivateKey
- [x] decryptPrivateKey

### JSON-RPC API
- [x] getBalance
- [x] getDsBlock
- [x] getTxBlock
- [x] getLatestDsBlock
- [x] getLatestTxBlock
- [x] getTransaction
- [x] getSmartContracts
- [x] getSmartContractState
- [x] getSmartContractCode
- [x] getSmartContractInit
- [x] getBlockchainInfo
- [x] getNetworkId
- [x] getRecentTransactions
- [x] getDSBlockListing 
- [x] getTxBlockListing 
- [x] getMinimumGasPrice

### Validation

- [x] isAddress
- [x] isPublicjKey
- [x] isPrivateKey
- [x] isSignature

### Util

- [x] byteArrayToHexString
- [x] hexStringToByteArray
- [x] generateMac
- [x] isByteString
- [x] encodeTransactionProto
- [x] toChecksumAddress
- [x] isValidChecksumAddress

## Build and Installation

you can build jar using following command :

```
gradle build -x test
```

and you can also try our snapshot version by 


gradle: (please see our example project)

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://oss.sonatype.org/content/groups/public"
    }
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
    compile group: 'org.firestack', name: 'laksaj', version: '0.0.1-SNAPSHOT'
}

```



maven: 

```xml
<dependency>
  <groupId>org.firestack</groupId>
  <artifactId>laksaj</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```





