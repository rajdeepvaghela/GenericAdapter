# GenericAdapter
[![Release](https://jitpack.io/v/com.github.rajdeepvaghela/GenericAdapter.svg)](https://jitpack.io/#com.github.rajdeepvaghela/GenericAdapter)
[![Release](https://img.shields.io/github/v/release/rajdeepvaghela/GenericAdapter)](https://github.com/rajdeepvaghela/GenericAdapter/releases)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


An adapter wrapper for Spinner and AutoCompleteTextView to remove the boilerplate code. 

## Installation
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
    	...
        maven { url 'https://jitpack.io' }
    }
} 
```
Add the dependency
```gradle
dependencies {
    implementation 'com.github.rajdeepvaghela:GenericAdapter:1.0.4'
}
```
## Usage
```java
    val adapter = object : GenericAdapter<Book>(resource = R.layout.spinner_item, list = list) {
        override fun getDisplayText(item: Book): String {
            return item.name
        }
    }
    // set the adapter like any other
    spinner.adapter = adapter

    // to get selected item which can be null if nothing is selected
    val selectedItem = adapter.getItem(spinner.selectedItemPosition)
```
For adding non selectable hint
```java
    val adapter = object : GenericAdapter<Book>(resource = R.layout.spinner_item, list = list, hint = "Select Book") {
        override fun getDisplayText(item: Book): String {
            return item.name
        }
    }
    // to change hint color
    adapter.hintColor = ContextCompat.getColor(context, R.color.spinner_item_hint_color)

    // to check if hint is selected
    adapter.isHintSelected(spinner.selectedItem)
```
You can also use this for AutoCompleteTextView
```java
    val adapter = object : GenericAdapter<Book>(resource = R.layout.auto_complete_item, list = list) {
        override fun getDisplayText(item: Book): String {
            return item.name
        }
    }
    autoCompleteTextView.adapter = adapter

    // to get selected item which can be null if nothing is selected
    val selectedItem = adapter.getItem(autoCompleteTextView.text.toString())
```
## License
```
Copyright 2023 Rajdeep Vaghela

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
