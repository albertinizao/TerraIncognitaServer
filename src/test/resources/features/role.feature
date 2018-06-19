Feature: the roles can be managed with controller 

Background:
	Given database user is clean 
	And database role is clean 
	And user usuarioBasico exists in DB
	And user usuarioAdmin exists in DB
	Given role ROLEMANAGER exists in DB 
	And user usuarioAdmin has role ROLEMANAGER 

Scenario: client makes call to GET /role 
	Given client is authenticated with user usuarioBasico 
	When the client get role list 
	Then the client receives response status code of 200 
	
Scenario: client get list of roles 
	Given client is authenticated with user usuarioBasico 
	And role USER exists in DB 
	And role AMBIENTACION exists in DB 
	And the client get role list 
	Then the client receives response status code of 200 
	And the client receives a list with all the inserted roles 
	
Scenario: client get one role 
	Given client is authenticated with user usuarioBasico 
	And role JUNTA exists in DB 
	When the client calls role /role/JUNTA 
	Then the client receives response status code of 200 
	And the client receives JUNTA role 
	
Scenario: client makes call to GET /role/NOEXISTS 
	Given client is authenticated with user usuarioBasico 
	And the client calls role /role/NOEXISTS 
	Then the client receives response status code of 200 
	And the client don't receives role 
	
Scenario: client makes call to POST /role with no more info
	Given client is authenticated with user usuarioAdmin 
	When the client post role /role 
	Then the client receives response status code of 500 
	
Scenario: client makes call to POST /role with no more info bad user
	Given client is authenticated with user usuarioBasico
	When the client post role /role 
	Then the client receives response status code of 403 
	
Scenario: client makes call to post existent role 
	Given client is authenticated with user usuarioAdmin 
	And role TESORERIA exists in DB 
	When the client post role /role/TESORERIA 
	Then the client receives response status code of 500 
	
Scenario: client makes call to post existent role bad user
	Given client is authenticated with user usuarioBasico 
	And role TESORERIA exists in DB 
	When the client post role /role/TESORERIA 
	Then the client receives response status code of 403 
	
Scenario: client makes call to POST /role/CAPITAN
	Given client is authenticated with user usuarioAdmin 
	When the client build role CAPITAN 
	And the client post role /role/CAPITAN
	Then the client receives response status code of 202 
	And the client receives CAPITAN role 
	And the role CAPITAN is in the DB 
	
Scenario: client makes call to POST /role/CAPITAN bad user
	Given client is authenticated with user usuarioBasico 
	When the client build role CAPITAN 
	And the client post role /role/CAPITAN
	Then the client receives response status code of 403
	
Scenario: client makes call to PUT inexistent /role/CABO 
	Given client is authenticated with user usuarioAdmin 
	When the client put role /role/CABO 
	Then the client receives response status code of 400 
	
Scenario: client makes call to PUT inexistent /role/CABO bad user
	Given client is authenticated with user usuarioBasico 
	When the client put role /role/CABO 
	Then the client receives response status code of 403 
	
Scenario: client makes call to PUT /role/TENIENTE 
	Given client is authenticated with user usuarioAdmin 
	And role TENIENTE exists in DB 
	When the client modify role TENIENTE 
	And the client put role /role/TENIENTE 
	Then the client receives response status code of 202 
	And the client receives TENIENTE role modified 
	And the role TENIENTE is modified in the DB 
	
Scenario: client makes call to PUT /role/TENIENTE bad user
	Given client is authenticated with user usuarioBasico 
	And role TENIENTE exists in DB 
	When the client modify role TENIENTE 
	And the client put role /role/TENIENTE 
	Then the client receives response status code of 403
	
Scenario: client makes call to PUT wrong role /role/TENIENTE 
	Given client is authenticated with user usuarioAdmin 
	And role REY exists in DB 
	And role TENIENTE exists in DB 
	When the client modify role REY 
	And the client put role /role/TENIENTE 
	Then the client receives response status code of 500 
	And the role TENIENTE is in the DB 
	And the role REY is in the DB 
	
Scenario: client makes call to PUT wrong role /role/TENIENTE bad user
	Given client is authenticated with user usuarioBasico 
	And role REY exists in DB 
	When the client modify role REY 
	And the client put role /role/TENIENTE 
	Then the client receives response status code of 403
	
	
Scenario: client delete role 
	Given client is authenticated with user usuarioAdmin 
	And role CAMPECHANO exists in DB 
	When the client delete /role/CAMPECHANO 
	Then the client receives response status code of 200 
	And the role CAMPECHANO is not persisted 
	
Scenario: client delete role bad user
	Given client is authenticated with user usuarioBasico 
	And role CAMPECHANO exists in DB 
	When the client delete /role/CAMPECHANO 
	Then the client receives response status code of 403
	
Scenario: client delete inexistent role 
	Given client is authenticated with user usuarioAdmin 
	When the client delete /role/SOCIO 
	Then the client receives response status code of 200 
	And the role SOCIO is not persisted 
	
Scenario: client delete inexistent role bad user
	Given client is authenticated with user usuarioBasico 
	When the client delete /role/SOCIO 
	Then the client receives response status code of 403