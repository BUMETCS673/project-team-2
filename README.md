# GroupProjectTemplate
Please make sure to modify this readme file as well as the "about" property of the project!

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

The API Endpoints are as followings:
http://localhost:8888/api/register
http://localhost:8888/api/login
http://localhost:8888/api/transaction/add
http://localhost:8888/api/transaction/credit
http://localhost:8888/api/transaction/debit
http://localhost:8888/api/transaction/history
http://localhost:8888/api/transaction/monthly/income
http://localhost:8888/api/transaction/monthly/expense
http://localhost:8888/api/transaction/analytics/monthly/%7BTRANSACTION_TYPE%7D/%7BYEAR%7D
http://localhost:8888/api/transaction/delete/%7BTRANSACTION_ID%7D
http://localhost:8888/api/budgetgoal/add
http://localhost:8888/api/budgetgoal/delete/%7BBUDGETGOAL_ID%7D
http://localhost:8888/api/budgetgoal/all
