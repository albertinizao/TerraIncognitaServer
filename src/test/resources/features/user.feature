Feature: the user can be managed with controller

  Scenario: client makes call to GET /user
    When the client calls /user
	Then the client receives response status code of 200

  Scenario: client get list of users without users
    Given database user is clean 
    And the client get user list
	Then the client receives response status code of 200
	And the client receives a empty list in response

  Scenario: client get list of users
    Given database user is clean 
    Given user pepito exists in DB
    Given user juancho exists in DB
    And the client get user list
	Then the client receives response status code of 200
	And the client receives a list with all the inserted users

  Scenario: client makes call to GET /user/inexistent
    When the client calls /user/inexistent
	Then the client receives response status code of 200

  Scenario: client get single inexistent user
    Given database user is clean 
    Given user pepito exists in DB
    Given user juancho exists in DB
    And the client get inexistent user
	Then the client receives response status code of 200
	And the client don't receives user

  Scenario Outline: client get single user and receive it
    Given database user is clean 
    Given user pepito exists in DB
    Given user juancho exists in DB
    And user <myUser> exists in DB
    And the client get <myUser> user
	Then the client receives response status code of 200
	And the client receives <myUser> user
	
  Examples:
    | myUser 	|
    |  Ender	|
    |  Julian   |


  Scenario: client create new user 
    When the client post /user
	Then the client receives response status code of 500

  Scenario: client create new user calls Rasputin without send user
    When the client post /user/Rasputin
	Then the client receives response status code of 415
	And the user Rasputin is not persisted

  Scenario: client create new user calls Putin but is created
  	When database user is clean 
    And user Putin exists in DB
  	And  the client build user Putin
    And the client post /user/Putin
	Then the client receives response status code of 500
	And the user Putin is in the DB

  Scenario: client try to create a new user with incorrect url
  	 When database user is clean 
  	And the client build user Andrew
    And the client post /user/Ender
	Then the client receives response status code of 500
	And the user Andrew is not persisted
	And the user Ender is not persisted

  Scenario: client create new user calls Snake
     When database user is clean 
  	And the client build user Snake
    And the client post /user/Snake
	Then the client receives response status code of 202
	And the client receives Snake user
	And the user Snake is in the DB


  Scenario: client update user 
    When database user is clean 
    And user Anastasia exists in DB
  	And the client modify user Anastasia
  	And the client put /user/Anastasia
	Then the client receives response status code of 202
	And the client receives Anastasia user modified
	And the user Anastasia is modified in the DB

  Scenario: client update user with incorrect url
    When database user is clean 
    And user Agumon exists in DB
  	And the client modify user Agumon
  	And the client put /user/Greymon
	Then the client receives response status code of 500
	And the user Agumon is in the DB
	And the user Greymon is not persisted

  Scenario: client update user inexistent user
    When database user is clean 
  	And the client modify user Patamon
  	And the client put /user/Patamon
	Then the client receives response status code of 500
	And the user Patamon is not persisted


  Scenario: client delete user 
     When database user is clean 
    And user Marcelino exists in DB
  	And the client delete /user/Marcelino
	Then the client receives response status code of 200
	And the user Marcelino is not persisted

  Scenario: client delete inexistent user 
     When database user is clean 
  	And the client delete /user/Amedio
	Then the client receives response status code of 200
	And the user Amedio is not persisted
