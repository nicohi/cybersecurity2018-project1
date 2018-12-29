

-XSS flaw with name field for signups (runs javascript from name field of signups)

## Fix:
Change "utext" to "text" for listing signups by name in the signups.html template. This causes the name of the signup to be placed with the proper escape characters in the template. This means that text written by a user can no longer be interpreted by someone else's browser as html or javascript.

