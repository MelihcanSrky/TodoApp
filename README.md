# To do App
 
## Features
* Todogo - Golang Backend server for TodoApp's
* TodoApp - Kotlin Frontend Mobile app for Android.(For now!)

#### Design from Figma
https://www.figma.com/community/file/1233771767232378502

### Future Ideas
* More features in backend
* Electron.js desktop app / Web App
* ----

### Completed Things!
This App Works on main branch.
Version 1.0.0


Standard to do app.

TR / EN localize language.

Change between dark and light themes in app. Default is system theme.

Create categories for tasks. (If you log in from another device, the lists stored on that device are not accessible.(Because lists are storing in SharedPreferences.))


In todogo folder: 
```powershell
.../todogo>go run main.go
```
Server runs on localhost:5000

In TodoApp Kotlin app /todoapp/di/AppModule.kt

Change IP with your IP.

```kotlin
private const val BASE_URL = "http://<your-ip>:5000/api/"
```

----------------------
### Register / Login Pages
<img src="https://github.com/MelihcanSrky/TodoApp/assets/62643822/9c0e1409-62b2-4087-9909-400f3798be2c" width=25% height=25%>
<img src="https://github.com/MelihcanSrky/TodoApp/assets/62643822/21cce221-c0cc-41ac-882c-e73f748d2147" width=25% height=25%>

### Home Page / Add Todo
<img src="https://github.com/MelihcanSrky/TodoApp/assets/62643822/e36b31d7-61d8-45ea-8457-a51ebb370688" width=25% height=25%>
<img src="https://github.com/MelihcanSrky/TodoApp/assets/62643822/a9cc760c-6c3b-428a-acf2-37e2f9d29e39" width=25% height=25%>

