Feature: the users validation works

  Scenario Outline: the user is correct
    When user <username> has username <username>
    And user <username> has password <password>
    And user <username> has name <name>
    And user <username> has surname <surname>
    And user <username> has dni <dni>
    And user <username> has email <email>
    And user <username> has phone <phone>
    And user <username> has birthDate <birthDate>
    And user <username> has medicalInformation <medicalInformation>
    When Validate the user <username>
	Then the validation is <valid>
	
  Examples:
    | username 	| password 	| name 	| surname	 | dni		 | email		 | phone 	 | birthDate 	 | medicalInformation 	| valid |
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770  | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  			| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| 			| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pas		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pas@		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| ◄◄◄◄		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| 		| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| 			 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 			 | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770-R| ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770A | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | X6321496S | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | Z9038019J | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | Y5833019A | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | Y5833019  | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | X9038019J | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | Q9432092F | ender@fi.com	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | Q9432092A | ender@fi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | 				 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | enderfi.com	 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@		 | 656565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@ficom	 | 656565656 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | a56565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 65a565656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 65656a656 | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 999999999 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 000000000 | 1356048000000 | Any					| true	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 6565656561| 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 6565656   | 1356048000000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 2966371200000 | Any					| false	|
    |  Ender	| Pass		| Andrew| Wiggin	 | 20582770R | ender@fi.com	 | 656565656 | 1356048000000 | 						| true	|