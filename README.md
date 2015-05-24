### Travis CI [![Build Status](https://travis-ci.org/Studio-Projektowe-AGH/PA_Gateway.svg)](https://travis-ci.org/Studio-Projektowe-AGH/PA_Gateway)
======
Host: https://goparty-gateway.herokuapp.com
=================================
### Ścieżki:

#### Logowanie i rejestracja
POST          /auth/signup                    
POST          /auth/signin/credentials        
POST          /auth/signin/:provider          

###### Testowe usugi
GET           /test/token/random              

#### Dostęp do danych
GET           /profiles                       
POST          /profiles                       
DELETE        /profiles                       
PUT           /profiles                       

###### Testowe usugi
GET           /profiles/business/all          

#### Przestarzałe
##### Klient biznesowy
GET           /profiles/business              
POST          /profiles/business              
PUT           /profiles/business              
DELETE        /profiles/business              

##### Klient indywidualny
GET           /profiles/indyvidual            


#### Nieistniejce?
POST          /events/:action                 Akcja pozwala na dostęp do podelementów gałęzi events
