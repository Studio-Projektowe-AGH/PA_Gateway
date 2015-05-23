### Travis CI [![Build Status](https://travis-ci.org/Studio-Projektowe-AGH/PA_Gateway.svg)](https://travis-ci.org/Studio-Projektowe-AGH/PA_Gateway)
======
Host:
=================================
Ścieżki:

POST          /auth/signup                  
POST          /auth/signin/credentials      
POST          /auth/signin/:provider        

GET           /test/token/random            

GET           /profiles/business            
POST          /profiles/business            
PUT           /profiles/business            
DELETE        /profiles/business            
GET           /profiles/business/all        

GET           /profiles/individual          


POST          /events/:action                 controllers.EventsDispatcher.dispatch(action)