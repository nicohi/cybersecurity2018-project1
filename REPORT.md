I expanded the template project to include {}

There are many things wrong with this program.

The purpose of the "Bank details" field is to serve as a placeholder for some kind of secret information that should be impossible for other users of the app to access.

# Most notable security flaws and their fixes

## Issue: XSS (OWASP A7)
Steps to reproduce:
1. Run web application and open it in a web browser (localhost:8080 is the default address)
2. Enter "</span><script>alert("some text")</script>" into the name field
3. Click "Submit"
4. Click link "View signups"
5. Your browser should display an alert window with "some text"
6. Thus the user can run arbitrary scripts in the web browsers of other users

### Fix:
Change "utext" to "text" for listing signups by name in the signups.html template. This causes the name of the signup to be placed with the proper escape characters in the template. This means that text written by a user can no longer be interpreted by someone else's browser as html or javascript.


## Issue: SQL Injection (OWASP A1)
Steps to reproduce:
1. Run webapplication and open it in a web browser (localhost:8080 is the default address)
2. Click "View signups"
3. Enter "test" into the "Name" field
4. Enter "'); DROP TABLE Message; '" into the "Message" field
5. The page should refresh and all messages disappear. The message feature is now unusable until the server program is restarted (table Message is made again)
6. The user can enter any arbitrary SQL that is interpreted by the server

### Fix:
Use prepared statements in place of executeQuery().
Another option is to remove all "'" symbols before executing the query (prepared statements are probably a better idea though).
