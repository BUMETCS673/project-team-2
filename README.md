# SoloSavings 
<img src="misc/LOGO.png" width="100" height="100" alt="LOGO">

"SoloSavings" is a Personal Budgeting Financial Application designed to help individuals manage their finances more effectively. It provides tools and features to assist users in creating and maintaining a budget, tracking their expenses, and achieving their financial goals. With SoloSavings, users can gain better control over their personal finances and make informed decisions to save, invest, and plan for the future.

### https://solosavings.wanl.blue 
![Dashboard](misc/dashboard.jpg)

## Deployment

WIP

## Development

To run application in Docker first open docker on pc.

Then navigate to 
D:\Code\Software Engineering\project-team-2\code\database
and run the following
Setup-Docker.ps1 -build
Setup-Docker.ps1 -start

Next navigate to
D:\Code\Software Engineering\project-team-2\code\SoloSavingsApp
and run the following
Setup-Docker.ps1 -build
Setup-Docker.ps1 -start

Then enter the following in your browser
http://localhost:8888/solosavings

#### The API Endpoints available are as followings:
```java
/api/register
/api/login
/api/forget-password
/api/reset-password
/api/transaction/add
/api/transaction/credit
/api/transaction/debit
/api/transaction/history
/api/transaction/monthly/income
/api/transaction/monthly/expense
/api/transaction/analytics/monthly/%7BTRANSACTION_TYPE%7D/%7BYEAR%7D
/api/transaction/delete/%7BTRANSACTION_ID%7D
/api/budgetgoal/add
/api/budgetgoal/delete/%7BBUDGETGOAL_ID%7D
/api/budgetgoal/all
```
