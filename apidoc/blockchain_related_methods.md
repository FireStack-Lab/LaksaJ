### GetNetworkId

#### Example request:

```java

package com.firestack.example;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String networkId = client.getNetworkId();
        System.out.println(networkId);
    }
}
 
```

#### Example response:

```
1
```

### GetBlockchainInfo

#### Example request:

```java
package com.firestack.example;

import com.firestack.laksaj.blockchain.BlockchainInfo;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockchainInfo blockchainInfo = client.getBlockchainInfo();
        System.out.println(blockchainInfo);
    }
}

```

#### Example response:

```
BlockchainInfo(NumPeers=2400, NumTxBlocks=1511, NumDSBlocks=17, NumTransactions=0, TransactionRate=0, TxBlockRate=0.009301685668083757, DSBlockRate=1.0811733936662047E-4, CurrentMiniEpoch=1511, CurrentDSEpoch=16, NumTxnsDSEpoch=0, NumTxnsTxEpoch=0, ShardingStructure=ShardingStructure(NumPeers=[600, 600, 600]))
```

### GetShardingStructure

### GetDsBlock

#### Example request:

```java
package com.firestack.example;

import com.firestack.laksaj.blockchain.DsBlock;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getDsBlock("1");
        System.out.println(dsBlock);
    }
}
```

#### Example response:

```
DsBlock(header=DsBlockHeader(blockNumber=1, difficulty=3, diffcultyDS=5, gasPrice=100, leaderPublicKey=0x020035B739426374C5327A1224B986005297102E01C29656B8B086BF4B352C6CA9, powWinners=[], previousHash=0800bfda2b715fe7e401f7cf73182f072f8459318cb4bb90d195f681c5bbebdd, timestamp=1548928944839738), signature=1515B838C310485ADE001822470E9CF9D0068E8528516AB71D8A964B642AF59A874A5345CC8239254B951EE53CB509A9A3D5F5FF63380D5F70D9A32A060DB0CD)
```

### GetLatestDsBlock

#### Example request:

```java
package com.firestack.example;

import com.firestack.laksaj.blockchain.DsBlock;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getLatestDsBlock();
        System.out.println(dsBlock);
    }
}
```

#### Example response:

```
DsBlock(header=DsBlockHeader(blockNumber=16, difficulty=24, diffcultyDS=35, gasPrice=1000000000, leaderPublicKey=0x02011852D914FAE2B5FD0B58AFA90F2490AF374C661958A6376F0C20CE0E08F05B, powWinners=[0x02068D20FF58034FEB1F59EB36211B639CB72D6068A4DF8B324C12E2DFAC6C3E01, 0x0254E9A07338ACD4897E9F25127C75C5E65EBB152B075CB12918833395F31702DB, 0x027821B80B76BF327C675ADE7249F72C26A83F0A06C7F7CDCD975835C1CE08BA96, 0x02B2BFD6DDAC2D904432EC10CBC0EDEA7AF598B83883C21A64BDF7A975A5A368FA, 0x031ECD4DC2E2BEAFF1E43D6F3303DCB8F404FE7FF06BB3DC97BB1E220C0F5C1318, 0x032E03A9F87478B2723335C4B42F0600E3442A31DF6A6527B49C32A4C3F1F4D433, 0x034E554C7324B12A6547385E3D997DE9F9FE61C892038E75BCF0166986CE9FECE6, 0x035E8D5140FE48DA14ACD9342FF0DD58C2C479F0B304F46A83DC36D1809F1005AE, 0x03CE492F1575A4108721D57C66E205B51244CF9BC61B40FE9A200126133BECFE01, 0x03FD61DA441E4E1CEE0292153A00DE57010F2014708855EA19CE044039A2B354A8], previousHash=105fa27f195415d55a593d3dc9ecd2aaf7ab38a4e985dd907dda1f6e604e85ce, timestamp=1549086181413442), signature=BECB7BC6B165039DB5EC53E62ED13DCA222515DAC12D8CB9A6D4AC66BD5BCB08A49F1B2DA0A2983C67758F3468953075EDA6E6149E1BA9CC97B65B2368D0E2DC)
```

### GetNumDSBlocks

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String result = client.getNumDSBlocks();
        System.out.println(result);
    }
}
```

#### Example response:

```
26
```



### GetDSBlockRate

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        double result = client.getDSBlockRate();
        System.out.println(result);
    }
}
```

#### Example response:

```
7.97597758621593E-5
```

### DSBlockListing

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.blockchain.BlockList;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getDSBlockListing();
        System.out.println(blockList);
    }
}
```

#### Example response:

```
BlockList(data=[BlockShort(BlockNum=25, Hash=0F47F226BB62F18F43D9F17DD7B0F0A1A6FD66EF3CF070C1C2DC25B7DB53B0B5), BlockShort(BlockNum=24, Hash=C4BC893D9A3793DBFE64CF8823AEC4B6E787F557FF02C05F00598005EB448F6D), BlockShort(BlockNum=23, Hash=98138EE513AA72C3FDFBCFED7CA414C8B0CCD0426584AF00F0F67285B48CB3D2), BlockShort(BlockNum=22, Hash=9E91FC5229895C9E5CED6CD44F37C7F6145015B99BBE4AD2A21F85BEAA0D7B79), BlockShort(BlockNum=21, Hash=217216FC69F4FF5ADBCD04C1AE09667A081B56F45179D4C254CFFD36A59EFD9B), BlockShort(BlockNum=20, Hash=9CB7EA4430883178A7137A26DAFEB52583F0FC3023624514065BBC5AFD1C6CA5), BlockShort(BlockNum=19, Hash=63AD9C5239CDF75630772A38135E9BBD08F4F080C0727E23B290ADF30780C7B5), BlockShort(BlockNum=18, Hash=EA9BF571BF5C366DC108EE36E1AC2866ADB275EAF955090A1B48189F598EEB54), BlockShort(BlockNum=17, Hash=a95e8bab22c2c8548e653b0d9ed21dc967f0aefb91350cc4c041d95db1c25f97), BlockShort(BlockNum=16, Hash=c9003cef7bb2a2ad3e88eadd1945629f213e65e917f41bc691de3d98b2454db7)], maxPages=3)
```

### GetNumTxBlocks

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String blocks = client.getNumTxBlocks();
        System.out.println(blocks);
    }
}
```

#### Example response:

```
3429
```

### GetTxBlockRate

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        double blockRate = client.getTxBlockRate();
        System.out.println(blockRate);
    }
}
```

#### Example response:

```
0.007834518595651478
```

### GetNumTransactions

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://dev-api.zilliqa.com");
        String transactions = client.getNumTransactions();
        System.out.println(transactions);
    }
}
```

#### Example response:

```
71
```

### GetTransactionRate

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://dev-api.zilliqa.com");
        int tps = client.getTransactionRate();
        System.out.println(tps);
    }
}
```

#### Example response:

```
0
```

### GetCurrentMiniEpoch

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://dev-api.zilliqa.com");
        String currentMiniEpoch = client.getCurrentMiniEpoch();
        System.out.println(currentMiniEpoch);
    }
}
```

#### Example response:

```
15952
```

### GetCurrentDSEpoch

#### Example request:

```java
package com.firestack.laksaj.app;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://dev-api.zilliqa.com");
        String currentDSEpoch = client.getCurrentDSEpoch();
        System.out.println(currentDSEpoch);
    }
}
```

#### Example response:

```
160
```





