## Google project Repo
- [Project]https://github.com/udacity/ud851-Exercises/tree/student/Lesson09b-ToDo-List-AAC

### Converter
- Use to covert data type in Room example "DateConveter"

### error and solution
- android-room-persistent-appdatabase-impl-does-not-exist
   apply plugin: 'kotlin-kapt'

   implementation "android.arch.lifecycle:extensions:1.1.1"
    kapt "android.arch.lifecycle:compiler:1.1.1"
// Room
    implementation "android.arch.persistence.room:runtime:1.1.1"
    kapt "android.arch.persistence.room:compiler:1.1.1"

- error: DateConverter() has private access in DateConverter kotlin class
   add @JvmStatic in object class
