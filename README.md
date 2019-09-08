README:

For this project, I made a unit converter. The unit converter can convert different units of length, temperature, mass, and volume, with a maximum of three decimal places outputted. There is one dropdown to select the type of unit to convert, a dropdown for the input unit, and a dropdown for the output unit. The image in the background changes depending on what unit type is selected: there is a scale for mass, a measuring cup for volume, a ruler for length, and a thermometer for temperature.

I started the app before seeing we could make a non-standard unit converter, and thus decided to make it with the most common types of units to convert. I looked at Digital Dutch's unit converter (https://www.digitaldutch.com/unitconverter/mass.htm) for inspiration on what units to add, and used StackOverflow for help learning how to use spinners and other simple Android features (https://stackoverflow.com/questions/20151414/how-can-i-use-onitemselected-in-android - https://stackoverflow.com/questions/10331854/how-to-get-spinner-selected-item-value-to-string - https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list - https://stackoverflow.com/questions/5324941/get-text-string-from-edittext). The bulk of the programming was adding simple if-else statements to convert each, with formulas for each possible conversion. Most of the rest was having everything show up well graphically, whether changing images or resetting input/output text when a new type was selected. To make use of vertical space on mobile phones, I decided to have the input at the top and output at the bottom, with a button in the middle to convert.

One of the hardest parts of working on this project was making sure everything showed up correctly- the rendering in Android Studio would not show up in the same way in the emulator even after changing to a Constraint Layout, so I had to constantly guess and modify where to put everything so it would show up correctly. The background image also at first would show up very blurred, and I had to research more about how images worked in Android Studio to finally fix it.

On August 28th, I started the project, added the basic layout (input/output text fields, spinner dropdowns, button) and got the basic app working with only length as measurement.
On August 29th, I added miles, centimeters, and kilometers to the length conversion.
On August 30th, I added temperature and mass conversions, with many unit options, and a new spinner to choose between the types.
On September 5th, I changed the button's image to an arrow to signify a conversion action. I also commented on the code to make it easier to read.
On September 6th, I added the background images, but had issues as they showed up very low quality and sometimes even didn't show up at all.
On September 7th, I fixed the images so they would show up correctly, and added a volume conversion type, that could convert US cups, US quarts, and liters.
On September 8th, I added this README.md, took the screenshots, and made sure everything was correct for submission.