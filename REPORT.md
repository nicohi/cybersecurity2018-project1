# Cybersecurity 2018 project 1
[link to project](https://github.com/nicohi/cybersecurity2018-project1)

[link to report](https://github.com/nicohi/cybersecurity2018-project1/blob/master/REPORT.md)

I suggest reading this report from github because of the markdown formatting.
The template project has been expanded to include five major security flaws from OWASPs top ten (documented below).
There are many things wrong with this program.

The purpose of the "Bank details" field is to serve as a placeholder for some kind of secret information that should be impossible for other users of the app to access.

# Most notable security flaws and their fixes

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

## Issue: Sensitive Data Exposure (OWASP A3)
Steps to reproduce:
1. Run webapplication and open it in a web browser (localhost:8080 is the default address)
2. Enter "Jane Doe" into the name field
3. Enter "Doestreet 15" into the address field and click "Submit"
4. Click "View signups"
5. "Jane Doe" is displayed in the list of signups
6. Access the web application with a different browser session (incognito window for instance)
7. Click "View signups"
8. "Jane Doe" and her address are displayed in the list of signups to all users

### Fix:
Having names and addresses be publically viewable is not good. Do not show full name and address in signup list. This can be fixed simply by modifying "signups.html" and rethinking how you want the program to work.

## Issue: Broken Access Control (OWASP A5)
Steps to reproduce:
1. Run webapplication and open it in a web browser (localhost:8080 is the default address)
2. Enter "Jane Doe" into the "Name" field
3. Enter "Doestreet 15" into the "Address" field
4. Enter "11513123" into the "Bank Details" field and click "Submit"
5. Copy the url of the page (should be "/done/" with a number)
6. With a different browser session (incognito window for instance) go to the link you copied
7. All of Janes private info is visible to anybody with the link. Guessing these links is easy because they are sequential numbers.

### Fix:
Check that the users session id is the same as the user who submitted the form. Display page to only the valid session id.

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


## Issue: Using Components with Known Vulnerabilities (OWASP A9)
Steps to reproduce:
1. Run "mvn dependency-check:check" in project root with terminal
2. View the report "target/dependency-check-report.html"
3. Multiple dependencies have flaws classified as "High severity" 
4. You should see CVE-2017-5929, CVE-2018-7489, CVE-2017-17485 and others.

### Fix:
Change the dependencies in "pom.xml" to use newer versions of the dependencies that do not have known severe flaws.
