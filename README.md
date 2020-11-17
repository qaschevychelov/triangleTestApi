# Triangle Service: API test

# Questions to Analysts
    1. delete nonexistent triangle - response is 200 OK with no body. 
       Is it OK?
    2. delete deleted triangle - response is 200 OK with no body. 
       Is it OK?
    3. create quadrangle - API takes first three side and creates a triangle. Fourth side is skipped. 
       Is it OK? Is an error expected?
    4. get area of max double - API returns 'infinite'. 
       Is it OK? Is an error expected?
    5. Is it OK if i get 405 'Method Not Allowed' when changing type of request? 
       For instance, when i send GET request '/triangle' with request body instead of POST.
       405 code isn't described in the specification. 
       
## Bugs
    1. 11 triangles can be saved. Expected 10
    2. got 500 internal error when trying to send '/triangle' POST request without 'input' field or with 'input' equals null
    3. got 500 internal error when trying to send '/triangle' POST request with separator as math operator (*)
    4. got 500 internal error when trying to send '/triangle' POST request with separator as |
    5. got 500 internal error when trying to send '/triangle' POST request with separator as special character (?)

## Negative cases
    1. BUG - save more than 10 triangles - allows to save 11 triangles
    2. create without token
    3. get without token
    4. delete without token
    5. get all without token
    6. get perimeter without token
    7. get area without token
    8. create with invalid token
    9. get with invalid token
    10. delete with invalid token
    11. get all with invalid token
    12. get perimeter with invalid token
    13. get area with invalid token
    14. POST with invalid endpoint
    15. get with invalid endpoint
    16. delete with invalid endpoint
    17. get nonexistent triangle
    18. get deleted triangle
    19. QUESTION TO ANALYST - delete nonexistent triangle
    20. QUESTION TO ANALYST - delete deleted triangle
    21. get perimeter of nonexistent triangle
    22. get perimeter of deleted triangle
    23. get area of nonexistent triangle
    24. get area of deleted triangle
    25. create impossible triangle
    26. create two lines
    27. QUESTION TO ANALYST - create quadrangle
    28. create triangle without sides
    29. create triangle with negative sides
    30. BUG - create triangle without input
    31. BUG - create triangle with null input
    32. create triangle with empty input
    33. create triangle with wrong separator
    34. create triangle with non default separator

## Positive cases
    1. get existing triangle
    2. delete existing triangle
    3. get all triangles when 0
    4. create triangle with zero length sides
    5. get all triangles when 10
    6. get perimeter of existing triangle
    7. get area of existing triangle
    8. create simple triangle
    9. create simple triangle with float
    10. create simple triangle with max double
    11. create simple triangle with max double + 1
    12. BUG - create triangle with math operator separator
    13. BUG - create triangle with another separator
    14. BUG - create triangle with special character separator
    15. create triangle with logic operator separator
    16. create triangle with default separator
    17. create isosceles triangle
    18. create equilateral triangle
    19. get perimeter of max double
    20. QUESTION TO ANALYST - get area of max double - API returns 'infinite'
    21. create the same triangle