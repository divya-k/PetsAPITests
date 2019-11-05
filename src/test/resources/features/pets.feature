#This feature file contains few test scenarios to verify Create Pet and Get Pet
#Author: Divya
Feature: Verify pet creation and retreival
  @API_Test
Scenario Outline: To authorize ,create and get pet
    Given Authorize oAuth with scope of read pets and write pets to receive the access token
    When we create a pet with id <id> name "<name>" photoUrl "<photoUrl>" and status "<status>"
  	And the pet should be created successfully with id <id> name "<name>" photoUrl "<photoUrl>" and status "<status>"
  	And we get the pet with id <id>
   	Then the pet details should be correct with id <id> name "<name>" photoUrl "<photoUrl>" and status "<status>"
  	
    Examples:
      | id      | name             | photoUrl                             | status         |
      | 9181		| Pomeranian Doggie| https://picsum.photos/200/300        | pending        |
      
      
