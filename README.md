# LaksaJ
LaksaJ -- Zilliqa Blockchain Java Library 

The project is still under development.

## Supports

### Account API

- [x] fromFile
- [x] toFile

### Wallet API

- [x] createAccount
- [x] addByPrivateKey addByKeyStore
- [x] remove
- [x] setDefault
- [ ] signTransaction (default ac********count)
- [ ] signTransactionWith (specific account)

### TransactionFactory

- [ ] sendTransaction 

### Crypto API

- [x] getDerivedKey (PBKDF2 and Scrypt)
- [x] generatePrivateKey
- [x] Schnorr.sign
- [x] Schnorr.verify
- [ ] verifyPrivateKey
- [x] getPublicKeyFromPrivateKey
- [ ] isPublicjKey
- [x] getPublicKeyFromPrivateKey
- [x] getAddressFromPublicKey
- [x] getAddressFromPrivateKey
- [ ] isAddress
- [ ] signTransaction
- [ ] verifySignature
- [x] createAccount
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

### Util

- [x] byteArrayToHexString
- [x] hexStringToByteArray
- [x] generateMac



