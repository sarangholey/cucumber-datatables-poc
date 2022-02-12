@endtoend
Feature: User can iteract with different products & links

  Background: Navigation to the URL
    Given User navigated to the home application url

	@addtocart
  Scenario: User is able to search and add the product to the cart
    Given User able to see the searchbox
    When User add the products with defined name listed below
      | PRODUCTNAME     | 
      | shirt   				|
      | dress 					|
      | summer    			|
    Then User cart is updated with the products and quantity as 3
    
  @footerLink
  Scenario: User is able to redirect to correct footer link
    Given User able to see footer links section of name "My account"
    When All 4 links under "My account" section is displayed
    Then User click on footerLinkText and verify the associated footerLinkUrlText
    	| FooterLinkText   | FooterLinkUrlText |
      | My orders     	 | history 					 |
      | My credit slips  | order-slip 			 |
      | My addresses 		 | addresses 				 |
      | My personal info | identity 				 |


	
  Scenario: User is able to redirect to correct footer link
    Given User able to see footer links section of name "My account"
    When All 4 links under "My account" section is displayed
    Then User click on footerLinkText and verify the associated footerLinkUrlText
    	| FooterLinkText   | FooterLinkUrlText |
      | My orders     	 | history 					 |
      | My credit slips  | order-slip 			 |
      | My addresses 		 | addresses 				 |
      | My personal info | identity 				 |
    
   