### Travis CI [![Build Status](https://travis-ci.org/Studio-Projektowe-AGH/PA_Gateway.svg)](https://travis-ci.org/Studio-Projektowe-AGH/PA_Gateway)
======
Host: https://goparty-gateway.herokuapp.com
=================================
### Ścieżki:

## Logowanie i rejestracja
POST          /auth/signup                  
POST          /auth/signin/credentials      
POST          /auth/signin/:provider        

# Testowe usugi
GET           /test/token/random            

## Dostp do danych
GET           /profiles/business            
POST          /profiles/business            
PUT           /profiles/business            
DELETE        /profiles/business            
GET           /profiles/business/all        

GET           /profiles/individual          

## Nieistniejce?
POST          /events/:action                 controllers.EventsDispatcher.dispatch(action)
