Feature: the locations can be managed with controller 

Background:
	Given database user is clean 
	And database location is clean 
	And user usuario exists in DB

Scenario: client makes call to GET /location 
	Given client is authenticated with user usuario 
	When the client get location list 
	Then the client receives response status code of 200 
	
Scenario: client get list of locations 
	Given client is authenticated with user usuario 
	And location Torremedusa exists in DB 
	And location Terranegra exists in DB 
	And the client get location list 
	Then the client receives response status code of 200 
	And the client receives a list with all the inserted locations 
	
Scenario: client get one location 
	Given client is authenticated with user usuario 
	And location Fungarest exists in DB 
	When the client calls location /location/Fungarest 
	Then the client receives response status code of 200 
	And the client receives Fungarest location
	
Scenario: client makes call to GET /location/Coralis 
	Given client is authenticated with user usuario 
	And the client calls location /role/Coralis
	Then the client receives response status code of 200 
	And the client don't receives location 
	
Scenario: client makes call to POST /location with no more info
	Given client is authenticated with user usuario 
	When the client post location /location
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /location with no more info bad user
	When the client post location /location 
	Then the client receives response status code of 403 
	
Scenario: client makes call to post existent location 
	Given client is authenticated with user usuario 
	And location Sjool exists in DB 
	When the client post location /location/Sjool 
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /location/Boscolmillo
	Given client is authenticated with user usuario 
	When the client build location Boscolmillo 
	And the client post location /location/Boscolmillo
	Then the client receives response status code of 202 
	And the client receives Boscolmillo location 
	And the location Boscolmillo is in the DB 
	
Scenario: client makes call to PUT inexistent /location/Coralis
	Given client is authenticated with user usuario 
	When the client put location /location/Coralis 
	Then the client receives response status code of 400 
	
Scenario: client makes call to PUT /location/Domus 
	Given client is authenticated with user usuario 
	And location Domus exists in DB 
	When the client modify location Domus 
	And the client put location /location/Domus 
	Then the client receives response status code of 202 
	And the client receives Domus location modified 
	And the location Domus is modified in the DB 
	
Scenario: client makes call to PUT wrong location /location/One 
	Given client is authenticated with user usuario 
	And location One exists in DB 
	And location Two exists in DB 
	When the client modify location Two 
	And the client put location /role/One
	Then the client receives response status code of 403 
	And the location One is in the DB 
	And the location Two is in the DB 
	
	
Scenario: client delete location 
	Given client is authenticated with user usuario 
	And location Coralis exists in DB 
	When the client delete /location/Coralis
	Then the client receives response status code of 200 
	And the location Coralis is not persisted 
	
Scenario: client delete inexistent location 
	Given client is authenticated with user usuario 
	When the client delete /location/Villapene 
	Then the client receives response status code of 200 
	And the location Villapene is not persisted