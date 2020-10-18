# Luas Dublin

Luas Dublin application has the purpose of helping Dublin citizens to see the Luas forecast.

In the current version you can only see these 2 forecast:

**Between 00:00 and 12:00 shows the forecast from Marlborough LUAS stop towards Outbound**  
**Between 12:01 and 23:59 shows the forecast from Stillorgan LUAS stop towards Inbound**

## Offline mode
When you have no internet connection, this application will present the latest result available. It also shows when the data was last updated.  
To accomplish that, **Luas Dublin** uses the concept of **Single Source of Truth**.

## Modules
To better organize the application, it was created three different modules, one for the UI, another for API and another for database.

## More info
**Luas Dublin** was built using **MVVM** architecture.

Even though in the current version there is no advantages of using the **Navigation Component** (it is a single fragment application),
**Luas Dublin** is using it to make easier to extend with new screens/fragments in the future .

**Hilt** is being used for **dependency injection**.
