When there is a 400:Bad Request OAuthClientID issue, this means that the JavaFX app is using the google sheets and gmail API in tester mode. Google only saves the tokens associated with these apis for 7 days, so the app will work perfctly for 7 days and then crash.
• To get rid of this issue, before you publish, all you have to do is:
      1. Go to the "tokens" folder
      2. Open the folder
      3. Delete the "Stored Credentials" file
      4. Now when you run again it should redirect you to verify/confirm
      5. Press continue, and viola
• If the issue persists, delete the project on google cloud console and create your OAuthClientID and credentials.json again and don't forget to replace the old credentials.json with the new one.
