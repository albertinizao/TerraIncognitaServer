Feature: the payments can be managed with controller 

Background:
	Given database user is clean 
	And database payment is clean 
	And user usuario exists in DB

Scenario: client makes call to GET /payment 
	Given client is authenticated with user usuario 
	When the client get payment list 
	Then the client receives response status code of 200 
	
Scenario: client get list of payments 
	Given client is authenticated with user usuario 
	And payment 42 exists in DB 
	And payment 56 exists in DB 
	And the client get payment list 
	Then the client receives response status code of 200 
	And the client receives a list with all the inserted payments 
	
Scenario: client get one payment 
	Given client is authenticated with user usuario 
	And payment 26 exists in DB 
	When the client calls payment /payment/26
	Then the client receives response status code of 200 
	And the client receives 26 payment
	
Scenario: client makes call to GET /payment/58 
	Given client is authenticated with user usuario 
	And the client calls payment /payment/58
	Then the client receives response status code of 200 
	And the client don't receives payment 
	
Scenario: client makes call to POST /payment with no more info
	Given client is authenticated with user usuario 
	When the client post payment /payment
	Then the client receives response status code of 202 
	
Scenario: client makes call to POST /payment with no more info bad user
	When the client post payment /payment 
	Then the client receives response status code of 403 
	
Scenario: client makes call to post existent payment 
	Given client is authenticated with user usuario 
	And payment 33 exists in DB 
	When the client post payment /payment/33 
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /payment/16
	Given client is authenticated with user usuario 
	When the client build payment 16 
	And the client post payment /payment/16
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /payment
	Given client is authenticated with user usuario 
	And the client post payment /payment
	Then the client receives response status code of 202
	And the received payment is in the DB 
	
Scenario: client makes call to PUT inexistent /payment/58
	Given client is authenticated with user usuario 
	When the client put payment /payment/58 
	Then the client receives response status code of 400 
	
Scenario: client makes call to PUT /payment/67 
	Given client is authenticated with user usuario 
	And payment 67 exists in DB 
	When the client modify payment 67 
	And the client put payment /payment/67 
	Then the client receives response status code of 202 
	And the client receives 67 payment modified 
	And the payment 67 is modified in the DB 
	
Scenario: client makes call to PUT wrong payment /payment/10 
	Given client is authenticated with user usuario 
	And payment 10 exists in DB 
	And payment 20 exists in DB 
	When the client modify payment 20 
	And the client put payment /role/10
	Then the client receives response status code of 403 
	And the payment 10 is in the DB 
	And the payment 20 is in the DB 
	
	
Scenario: client delete payment 
	Given client is authenticated with user usuario 
	And payment 58 exists in DB 
	When the client delete /payment/58
	Then the client receives response status code of 200 
	And the payment 58 is not persisted 
	
Scenario: client delete inexistent payment 
	Given client is authenticated with user usuario 
	When the client delete /payment/96 
	Then the client receives response status code of 200 
	And the payment 96 is not persisted