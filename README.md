# AdvancedRadioGroup
![version](https://img.shields.io/badge/advanced--radio--group-v1.0.0-blue)

AdvancedRadioGroup is a custom version of the android RadioGroup that allows detecting RadioButton 
inside a custom container. Unlike the standard RadioGroup, the AdvancedRadioGroup allows greater 
customization by allowing the placement of the RadioButtons in containers such as LinearLayout, 
ConstraintLayout RelativeLayout, etc.
## Installation
To install the library use Gradle:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.ihermandev:advanced-radio-group:1.0.0'
}
```
## Usage
The AdvancedRadioGroup can be used just like a regular android RadioGroup, but the main difference 
is that the AdvancedRadioGroup is able to handle a RadioButton inside container.
##### In XML layout
```XML
<com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup
  ...
  <androidx.constraintlayout.widget.ConstraintLayout...
    <RadioButton...
    <TextView...
  </androidx.constraintlayout.widget.ConstraintLayout>

  <androidx.constraintlayout.widget.ConstraintLayout...
    <TextView...
    <TextView...
    <RadioButton...
  </androidx.constraintlayout.widget.ConstraintLayout>

  <LinearLayout...
    <ImageView...
    <TextView...
    <TextView...
    <RadioButton...
  </LinearLayout>
</com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup>
```
##### Then listening for checked changes
```kotlin
advancedRadioGroup.setOnCheckedChangeListener { group, checkedId ->
    when (checkedId) {
        R.id.radioButton1 -> {
            // Handle the event for radio button 1 being checked
        }
        R.id.radioButton2 -> {
            // Handle the event for radio button 2 being checked
        }
    }
}
```
## Contribute
If you find any bugs or have any suggestions for improvements, please open an issue or create a pull
request on the [GitHub repository](https://github.com/ihermandev/advanced-radio-group)
## License
This library is released under the Apache 2.0 license. See the [LICENSE](https://github.com/ihermandev/advanced-radio-group/blob/master/LICENSE.md) file for more information.