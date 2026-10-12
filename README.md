# Student Organizer App — Level 2 Assignment

Kotlin + Jetpack Compose student organizer with five screens: Dashboard, Assignments, Profile, Settings, and Add Assignment. Implements a sealed navigation route model, central NavHost, reusable AppBar/BottomBar/CourseInfo/AssignmentCard/StudentProfile/AssignmentForm composables, assignment filters and completion toggles, form validation, reminder setting, and theme preference.

## Run
Open this folder in Android Studio, allow Gradle sync, install Android SDK Platform 35 if prompted, start an API 24+ emulator, then run the `app` configuration. Java 17 is required.

## Demo recording
Capture a real 10–20 second emulator video showing Dashboard → Tasks → Profile → Settings → Add Assignment, then save it as `demo/student-organizer-demo.mp4` and commit it here. A real emulator recording cannot be fabricated outside the emulator. See `demo/README.md` for steps.

No database or network backend is used; sample UI state is in-memory as specified in the assignment.