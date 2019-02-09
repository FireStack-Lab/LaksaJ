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

### GetRecentTransactions

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.blockchain.TransactionList;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://dev-api.zilliqa.com");
        TransactionList transactionList = client.getRecentTransactions();
        System.out.println(transactionList);
    }
}
```

#### Example response:

```
TransactionList(number=100, TxnHashes=[bbab3fced5ffd5a58257c6c98114f333cb840ed1b60833e6c117c26a4c671908, 53432802c2bf0a12aa1a6b016f4fdd71febaf9daeb8f87a43b6b3baf8727d18a, 8006379570367a4ff5f3cb596edfa9025ba2745dd0dbf5a0043382b7f2badcec, cc89eba2d5b6fa3c8b7b7423f9b5a8a46d3faf290ee8a9d14e656677c3c012e0, bb56ef84d9f9d2515c66e25affbed4df6454ae5e7b6eb47ca6a5dfb74f944693, 9e928068f1315d4c5c7ba87210ce1e4716054863167db1eb7305ca1a72cd2c7b, ef05f44bcce151e7b3279c90323b04db02b443a29aa72f8de436957d5f9c6e90, 2bccb6dcdc8d20c43c2b8cac0175abe799f48f75a3a4034a68b8555348c942b6, 3b7ffbb0e550fe1e01e0b6c1bf41238f2210bfda8e1ad25c8bb001f08dcdd79a, bda1bda318cb58a23f9d74988e5808ff720d826721a3d8b70431f9eb416f1f9f, 4d224166e7fd01d6b2287ae140966508f4bf8a3254f1255b46b834cddd8084a0, d980d3019cd8afac0e98b4b74a7b4a8f48ccb399adcd37295e2085032af055f3, 42cc0593e5d2c95e0cfa525c52c9f5cbd4661416af68e3486f4d289d5144f8b0, 6492dd442ad74b40aec35eafa38e89aadbdf86f84061f1b690c4563c33ae9f66, 82e2610530e54deb3d7ddce254757d3e8f46e64e6feae52d2aa0fd4ea5483054, 158cdc8096c07e5d26565002baaf8242e05f6f1f4bb95ca52e6055c301c0a789, 9ca9889348f6d4247f01c48aba073222686a52d1115a2a84f465932f9b6e45a1, b1b73d989b9c375c1b12a48b2d68e9dc0197c7af95b3838fb7e9de715b88802c, 56b64a39e01a96ccd3b13916ab53b3c9f7ccfe2917af8ade47c5f073c5c3ed27, c883d104658e74dd67503ebd78beb1bc45118306ec5acbafa1925f696ad866a7, ae4696ac7fddd76363cd2f70bac521532434f5eb35ad7006bcdbea448e8781a8, 99e83b0736d46fadc18c113935fa38df4ea13a7cd0a5bbfd70da2287ca8ddc5c, 397e93fad6760186eef9a0255e4a641c2f024555172e2b412207b22d6eb59354, baf4f323e6a719f89f3b408132790c54aeb43ac29c138a8849b4569bdbda6cf3, 978e1cbc6167017867f15ae7926b93b6fdba40e96691420fc4f6490188d4d025, 5c73e6538b909cf208aab1e9bfbde32c0ea054fb167208fea35cfcd18f86ddbf, 1408245e1394941a55f0ace407be9436f8c8ef8f444b45ad26a5234b4a2e9352, d8005f96a8b6b5bed349fd16fb5606e7fa4810a5aa8a4fffcfce386f6d28adfc, b893d7c6b49ab3610a7cda277f01d4f76d872858b7f34ecdbe75e7df23a3789b, 68c65f1f8dc29ca28755fab51f8962db859551090c12dc2e30893c6d604aaeeb, 3b478d52a684c83d94f52aa3a145df659e9f39005c99bc87d4d9fdce21b180d5, d9b14919146ee5d841772d903af53de615e27ca1463a2a6bc38dbcdb6cfa697e, 7c3c93b07abcf4dc917ab5259835ccad0aae7e0943e36f3dc3871024a24ae18a, 73bd7f161c7b814b3fcfb0b03716d602949735d3ab9c33d9140fe515cc486596, 54039d9abe39dcc43edd9c58a55ae50b783e778afb46b103466e363ba21536ff, c10975600b0b00a58338446ef372688867a902d249147c4cfc3833ac5abac4d6, 19634b10e39c0a67954cf9c18254f22f4f257080048326a12c12dfac03330e54, 87343cbd67caa05d3c02dfa9c6976a8cfae8f6b53734bb5b48090fc31f239543, 18c62e6361af60b2db3668b7c0aba2483c3d1410cc5b7d209b15baaf46e787ef, 895950960c6c428aa4c140c015fff9ec2b1e65a877c6f42d9b5ef8e6f6f32537, 3b9f0993a010ceced271366d3bc62445552b450394041c8b170baccfe75ac50c, 58a5f4f31c8e3233f0dad7a254e9af1e30c28499208656aebd3a2c6008a8bd94, 7901c01b47893ef8a8f4bed8eae22dbf4688e15c70ca7dfb2cf1e6b158a8f5a5, 40d26b6bf327f16d1e122f536066252d8bb8778e140518e608bf6ee81f36c386, 282b3ae67b4413ba269957eab97dab616c1d62e0cc5231eb22277bc45a045dfc, fa3a87f3e3839918d66a5b39a4c332d6c98bb3a0d233ed0d0138df5cb2f4c15d, 0225df6374af2efa17f53cc442fb378f1c29f4a7e119959a238f20d5aca52196, 982808dddf8414cc94e3b4d50758237f7aa3abff0d20db7f82c52f2ca548b65f, 2834a02d973340a0e6ef2c803024566782f490b4d527f99fc5d6d5e46acbc19b, f0c9094b94a28543cb19817764498c6ee97e21ecbd1ae06e2b10fcf09a67edc9, 2b139e205b74958da56db9aff2371c7e9bfb4d5cfed0ee271b383c7df73b5d50, 59989a02f0fa45d3000b2ba79730653f3249a26d223284524cfa0c643ebe977c, ef99a5f60b6e79c38a6d5e9f48acf0de74567c2f218e18c6f497e3437f74427a, e77fbc82614780962e71e85ed45e41afd4e9bed06359e8e488a2a8b036b13558, 79b76f2fc5bab8767496f4aa6635b4af50e97700da0a31517fa84f00bd7a550d, d328eb50dea2470056534b76c60f5bda0b7b98dd4328f7296ff50e4cff8b96a3, 457168ed5cbd6c21e3ab8d5a4c54f3e2dd690e261e262a39a2bb55acff2e3b53, c0c460b5f99715f8e2b9b3260d4bd4f6aa7bd5cc0f656f48e8c34705f999996d, e5089226280af903478ded9e7210e3b0e3194f903fcfcc3bb3ac260826b800cd, 1c5b6052ed1c48a58592eddfef8233839f6e6dcc03221876d241bc32d52d2ea7, 9bbeecfe1047dfabb3b4ff6eb6a6f458908774181dcf5f44af982a2114cbebea, 920ac1f91c6db9505db400bc87073e8fd97376e5f4b2889f3f52ef3940dabae9, 8d65896b340a41193c8238c82752603b2e046a331cfdd678394319ca0f4b2750, 8bec082fa1a35ae18409021e55da56fe4c0f58f71116d3f884d09e7697c906db, 263eb0ca20fc223007b4386415ae9e6afc1180c4b06f94e0ddfc2cb19cf05159, 8597ae4666b4183263c8f7e736899bfc0d09a5bd42d3eb86a6cbaf0fc325d461, 72e8e5500822cc710a7fb7a102b27b459b04b251ff38c849987f200d71a7c614, 04e1b970fd86548a2014bd4359f091fcd91b362d4fe4a785fc39a39904424d8c, 210bb1539ffa9d472701d6b2a5ce7517743e441bbfced477fc9a0f014bbf0230, 37f762bc0e94ec2949cc1362d22527b8ca2bffc7ccdce62db1bb1b3ae590259a, c7577bc52f0a0754ba936c27f8e0c0e2dc5d316853f87d3a289a0063a7d505d5, 5cd7b3fb835014f27eee4a20ddfba15e533fab65e436e75e4cab85866fed95ea, e8811aae31bf905f966b4101e3686e83932d687209d0889ec2d5c022be08e4d5, 3c8e1ab326932e72fb3e173ed4edf94eaf94cf1eece1a049bbfd5aa1f0d4094f, 7e2d10e79eb4a1e96b38f4a6ab2a2bca3bf51102fc8ace0579a06cde7d9b4fda, 958e52bbadf25212ece1ac83e4455449116b1a835f341aeb7884e2c5470eec60, f3ca4b240183b8c31c68be2fb92255e3cadf565772909bc060ca9e4f19f840d1, 20d6a9e5eab3d10c03444711c3ad161216aff9ad6d9191ebd99da92f9f2cc990, 94d5570fd314dfcbd038a922240f3eb50e65f760f3b47707ba59596b83da9009, 3cf801b37d6cc6f1feb2295997e78089fefca785ebd89e8de162cf55863931a5, e565a1ed3f7042a1293cee5797832c193806254266990ad88d6b8df5af3a6f49, 5d101d017a2ce3be98feb9a22e7d6d45a34c3198e6a8d8b068c146b01e507c91, c6bc0f632aa11e55b9a52a80390e60eff2d649d584d4465308faae774fcd87a3, 64da0f83d801e1c158f63da9aed7cf843fd81f972540e01d67cf868528d70184, b6110eb96ced0ffa9fb564538ea429fc4ec4407bb5f627c2c9f15c7d192bd3ba, 9302f7afdf94c7a5ff3d500172197b96c81588b74fa8ab7c025e1f54d938ae63, 5b309def59dcc96148dd05c60c9768320be6c9ff6dda4076d63222cb86633874, c9564ba65aa42aa67ba1730f0450003b1ccadc2ddd3bd8861e67173ab01099dd, fd5e4316431ebb3f1d3a000a48caa8363942b3c7bb74bdf8f330fc305feb947e, ca8399f8065f9d24f4e5c92509129b7d6797dff70cbc1a059a98d108c50fe848, 44ac0b131a48f0c99c990b80789d5d04eb5d620dc79a888d7e19a61de3086a64, 8a1342b084cdf5c8c0b9e4b075ca68e8ee05d5fd8899e5c2f54b6e89a4a59ec7, 8053561a6995a4aa0b056371acfa8dfe4dc2bf3be93372b7c3308000c3f96d2d, 195f8f9242b376ed62ea82b6790e88598ad040c3ba6fe2941d42f3c9fa53c074, d485ac9f53f29dd3a620ec0de16576d44e7ec3e2b05b6c10d0e1f61c87b1e3c9, 6fa94619d0dbd6602487cb17c3442c3addcd8e2de51e17a0891da3c34e0b00b7, 62863c8946f450cd0cd9edfa8f6d2e6d4d303125b673fd6ac45ba3b17712896d, bd7508a25d5931923d4643e9fad0d0735169b5c31f0868044c3faa28f05b9cac, 7f370ebd68925652c2c64ac989f43d40235e2006affe26d2c3e27831ae123a72, 7f8661a3619e3bb30acea23c3fb1157da3ae2e6c7b8f4bb6c490d76598dbba0c])
```





