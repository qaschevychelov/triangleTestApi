# Triangle Service: API test

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
    21. get all triangles when 0
    22. get perimeter of nonexistent triangle
    23. get perimeter of deleted triangle
    24. get area of nonexistent triangle
    25. get area of deleted triangle
    26. create impossible triangle
    27. create two lines
    28. QUESTION TO ANALYST - create quadrangle
    29. create triangle without sides
    30. create triangle with negative sides
    31. create triangle with zero length sides
    32. BUG - create triangle without input
    33. BUG - create triangle with null input
    34. create triangle with empty input
    35. create triangle with wrong separator
    36. create triangle with non default separator