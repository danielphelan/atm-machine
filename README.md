
# Project Title

A brief description of what this project does and who it's for


## Run Locally

Clone the project

```bash
  git clone https://github.com/danielphelan/atm-machine.git
```

Go to the project directory

```bash
  cd atm-machine
```

Build

```bash
  maven clean install
```

Start the application

```bash
  maven spring-boot:run
```


## API Reference

### ATM API: /api/atm
#### Get ATM notes and balance
```http
  GET /api/atm/check-available-notes
```
##### Response
```json
{
    "atmNotes": [
        {
            "note": 50,
            "count": 10
        },
        {
            "note": 20,
            "count": 30
        },
        {
            "note": 10,
            "count": 30
        },
        {
            "note": 5,
            "count": 20
        }
    ],
    "atmBalance": 1500.0
}
```

### Account API: /api/account
##### Get Account Balance

```http
 GET /api/account/check-balance/{userAccountId}?pin={userPin}
  
```
##### Response
```json
{
    "accountId": 123456789,
    "accountBalance": 800.00,
    "accountOverdraft": 200.00,
    "maximumWithdrawal": 1000.00
}
```

##### Withdraw

```http
 PUT /api/account/withdraw/{userAccountId}?pin={userPin}&withdrawalAmount={amount}
  
```
##### Response
```json
{
    "accountId": 987654321,
    "withdrawalAmountRequested": 635,
    "withdrawalNotes": {
        "50": 10,
        "20": 6,
        "5": 1,
        "10": 1
    },
    "remainingWithdrawalBalance": 745.00,
    "withdrawalStatus": "SUCCESSFUL"
}
```



## Functionality

-	On startup/restart project will initialize with the following accounts:

| Account Number | PIN          | Opening Balance  | Overdraft  |
| ------------- |:-------------:| -----:|-----:|
| 123456789      | 1234 		| 800 	| 200 |
| 987654321      | 4321 		| 1230 	| 150 |
												
-	ATM is filled up with €1500 made up of 10 x €50s, 30 x €20s, 30 x €10s and 20 x €5s.
	- Can be confirmed by calling: 
	
```http
 PUT http://localhost:8080/api/atm/check-available-notes  
```
	
-	If the pin is incorrect for both check balance and withdrawals, users will see the below response:

```json
{
    "transactionStatus": "FAILED",
    "reason": "Invalid pin for account with Id 123456789 entered"
}
```



-	If user attempts to withdraw more money than is in the ATM, they will see the below response:

```json
{
    "transactionStatus": "FAILED",
    "reason": "Requested amount is too high. Maximum of 1500 can be withdrawn"
}
```

-	If the user requests access to more funds than the customer has access to, they will see the below response:

```json
{
    "transactionStatus": "FAILED",
    "reason": "Requested amount is too high. Maximum of 1000 can be withdrawn"
}
```

-	Upon successful request users will see the notes being used which will total to the requested value. 
-	Logic in place will identify the lowest possible number of notes to dispense, Example response below:

```json
{
    "accountId": 123456789,
    "withdrawalAmountRequested": 435,
    "withdrawalNotes": {
        "50": 8,
        "20": 1,
        "5": 1,
        "10": 1
    },
    "remainingWithdrawalBalance": 565.00,
    "withdrawalStatus": "SUCCESSFUL"
}
```


### Additional functionality

- Database loaded on startup - see src/main/resources for data.sql used to populate tables
- Database storage in place for user account and atm balance. With each withdrawal the users account will update along with the number of notes.
	- Restart the application to refresh the users account and atm balances.
- 81.3% coverage total with 99% coverage on service layer
		
		
### Postman Collection
- Find collection of calls that can be used in Postman in src/main/resources


