## Technologies:
- Compose for UI
- Clean architecture for files organization
- Repository with simulated call (delay), could be used to implement some offline storage as well
- MVVM pattern
- State and LiveData for passing data from the ViewModel to the View, both are used to display the differences in handling them.
  > **Note:** StateFlow could be used instead of LiveData without much changes on the View.

## Improvements
- Unit and UI tests
- Dependency Injection
- Separate the validation to another class or maybe implement UseCases
- Have different models for http response, local database (if present) and to be used in the domain to better handle and standardize data
- Strings in Strings files for translations
- Padding/Spacing values in Values files for reusability
- Encrypted way of implementing "remember me", preferably saving some auth token returned from the backed instead of actual login data
- Convey the error/success messages to the user in a better way, Snackbar was just a basic and quick way
-
## Final considerations
I tried to do this mimicking how it would be done during the interview, so I kept it pretty simple and with lots of room for improvement