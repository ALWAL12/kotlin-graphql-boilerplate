Feature: User authentication
  A user may log in on the platform providing its email

  Scenario: An existing user can log in
    Given a user provided email 'john.doe@acme.com'
    When retrieving a user by its email
    Then the user can log in

  Scenario: A non existing user cannot log in
    Given a user provided email 'non.existing@email.com'
    When retrieving a user by its email
    Then the user cannot log in