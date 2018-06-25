Feature: the events can be managed with controller 

Background:
	Given database user is clean 
	And database event is clean 
	And user usuario exists in DB

Scenario: client makes call to GET /event 
	Given client is authenticated with user usuario 
	When the client get event list 
	Then the client receives response status code of 200 
	
Scenario: client get list of events 
	Given client is authenticated with user usuario 
	And event Torremedusa exists in DB 
	And event Terranegra exists in DB 
	And the client get event list 
	Then the client receives response status code of 200 
	And the client receives a list with all the inserted events
	
Scenario: client get one event 
	Given client is authenticated with user usuario 
	And event Fungarest exists in DB 
	When the client calls event /event/Fungarest 
	Then the client receives response status code of 200 
	And the client receives Fungarest event
	
Scenario: client makes call to GET /event/Coralis 
	Given client is authenticated with user usuario 
	And the client calls event /role/Coralis
	Then the client receives response status code of 200 
	And the client don't receives event 
	
Scenario: client makes call to POST /event with no more info
	Given client is authenticated with user usuario 
	When the client post event /event
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /event with no more info bad user
	When the client post event /event 
	Then the client receives response status code of 403 
	
Scenario: client makes call to post existent event 
	Given client is authenticated with user usuario 
	And event Sjool exists in DB 
	When the client post event /event/Sjool 
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /event/Boscolmillo
	Given client is authenticated with user usuario 
	When the client build event Boscolmillo 
	And the client post event /event/Boscolmillo
	Then the client receives response status code of 202 
	And the client receives Boscolmillo event 
	And the event Boscolmillo is in the DB 
	
Scenario: client makes call to PUT inexistent /event/Coralis
	Given client is authenticated with user usuario 
	When the client put event /event/Coralis 
	Then the client receives response status code of 400 
	
Scenario: client makes call to PUT /event/Domus 
	Given client is authenticated with user usuario 
	And event Domus exists in DB 
	When the client modify event Domus 
	And the client put event /event/Domus 
	Then the client receives response status code of 202 
	And the client receives Domus event modified 
	And the event Domus is modified in the DB 
	
Scenario: client makes call to PUT wrong event /event/One 
	Given client is authenticated with user usuario 
	And event One exists in DB 
	And event Two exists in DB 
	When the client modify event Two 
	And the client put event /role/One
	Then the client receives response status code of 403 
	And the event One is in the DB 
	And the event Two is in the DB 
	
	
Scenario: client delete event 
	Given client is authenticated with user usuario 
	And event Coralis exists in DB 
	When the client delete /event/Coralis
	Then the client receives response status code of 200 
	And the event Coralis is not persisted 
	
Scenario: client delete inexistent event 
	Given client is authenticated with user usuario 
	When the client delete /event/Villapene 
	Then the client receives response status code of 200 
	And the event Villapene is not persisted
	
	
Scenario: client list all the character groups of a event
	Given client is authenticated with user usuario 
	And event Ceres exists with character-group Olivo in DB 
	When the client get event character group list with event id Ceres 
	Then the client receives response status code of 200 
	And the client receives a list with all the inserted character groups
	
Scenario: client get one character group of a event
	Given client is authenticated with user usuario 
	And event Ceres exists with character-group Olivo in DB 
	When the client calls event-characterGroup /event/Ceres/characterGroup/Olivo
	Then the client receives response status code of 200 
	And the client receives Olivo event character group
	
	
Scenario: client list all the character groups of a event
	Given client is authenticated with user usuario 
	And event Ceres exists with character Bielian in group Olivo in DB 
	When the client calls list of characters /event/Ceres/characterGroup/Olivo/character
	Then the client receives response status code of 200 
	And the client receives a list with all the inserted characters
	
Scenario: client get one character of a character group in a event
	Given client is authenticated with user usuario 
	And event Ceres exists with character Bielian in group Olivo in DB 
	When the client calls character /event/Ceres/characterGroup/Olivo/character/Bielian
	Then the client receives response status code of 200 
	And the client receives Olivo character