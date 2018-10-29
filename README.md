# Diversity Android App

This app was created for Denver's regional Go Code Colorado hackathon. The idea was to create a chat app by which employers seek suggestions of where to look for diverse candidates, especially among local universities. 

The app one first place for the Denver competition, and advanced to state-wide finals. 

Participants from the team went on to spin out a company from the project: https://hirediversity.us/

# Generate a Play Store Build

- Obtain the `diversity.jks` key store from the shared Google Drive folder and copy it to your machine;
- Obtain the password for the `diversity.jks` key store (shared via email);
- Invoke the following Gradle command from the root of this repository:
    ```
    ./gradlew clean app:assembleRelease -PpathToKeyStore='fill-me-in' -PsigningPassword='fill-me-in'
    ```

This generates a signed APK in the `app/build/outputs/apk` directory.

# Features

Chatbot which asks questions regarding your employment needs. 

Pulls in local college data to suggest where you should recruit for your next hire.

# ChangeLog & Roadmap

As other folks have carried the product forward, there are no changes planned for this code repo. 

As a product manager, there are several ways I would have built this differently from the start: 

1.) Built the product as a simple web app rather than in native Android/iOS (those were the skill-sets we had on the team). 

2.) Chat-bot functionality provided quick and easy setup through a 3rd-party library and was great for a quick MVP! Long-term, I would beta test with the chatbot UX vs. something closer to Typeform's smooth transitioning Q&A style. It may be that users enjoy the quirky chatbot style and it helps it feel more engaging (especially if we add some kind of personality to the bot) but I suspect employers would be more trusting of a more professional format. 

3.) The next feature to prioritize would have been some kind of 1-click outreach to the schools suggested by the app. It could automtically message the career services department with contact info of the inquirerer for follow-up. This kind of easy action would have induced more outcomes. 


# Credits

Thank you to the Slyce Android library for our messaging functionality: https://github.com/Slyce-Inc/SlyceMessaging

Thank you to Stuart Kent from Detroit Labs for significant technical guidance in creation of this app. 

