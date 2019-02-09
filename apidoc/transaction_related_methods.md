### CreateTransaction

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionFactory;

import java.io.IOException;

import static com.firestack.laksaj.account.Wallet.pack;

public class App {
    public static void main(String[] args) throws IOException {
        Wallet wallet = new Wallet();
        wallet.setProvider(new HttpProvider("https://dev-api.zilliqa.com"));
        wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930");
        Transaction transaction = Transaction.builder()
                .version(String.valueOf(pack(333, 8)))
                .toAddr("4baf5fada8e5db92c3d3242618c5b47133ae003c".toLowerCase())
                .senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a")
                .amount("10000")
                .gasPrice("1000000000")
                .gasLimit("1")
                .code("")
                .data("")
                .provider(new HttpProvider("https://dev-api.zilliqa.com"))
                .build();
        transaction = wallet.sign(transaction);
        HttpProvider.CreateTxResult result = TransactionFactory.createTransaction(transaction);
        System.out.println(result);
    }
}
```

#### Example response:

```
CreateTxResult{Info='Non-contract txn, sent to shard', TranID='8006379570367a4ff5f3cb596edfa9025ba2745dd0dbf5a0043382b7f2badcec'}
```

### GetTransaction

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://dev-api.zilliqa.com");
        Transaction transaction = client.getTransaction("8006379570367a4ff5f3cb596edfa9025ba2745dd0dbf5a0043382b7f2badcec");
        System.out.println(transaction);
    }
}
```
#### Example response:

```
Transaction(ID=8006379570367a4ff5f3cb596edfa9025ba2745dd0dbf5a0043382b7f2badcec, version=21823496, nonce=1, amount=10000, gasPrice=1000000000, gasLimit=1, signature=0x39FCFDAB8135601737971FBBDC27EDF2141B192A93CC87B0330F54A2DDCFDB6F67647BE794076215CE3E48EB8E8A3DFA2D2DB97AA968B9A8479111880A525780, receipt=TransactionReceipt(success=true, cumulative_gas=1, epoch_num=29331), senderPubKey=0x0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A, toAddr=4baf5fada8e5db92c3d3242618c5b47133ae003c, code=null, data=null, provider=null, status=null)
```


