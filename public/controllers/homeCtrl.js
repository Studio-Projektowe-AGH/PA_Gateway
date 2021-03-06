/**
 * Created by Dominika on 2015-04-17.
 */

angular.module('HomeModule', ['UserModule', 'AuthenticationModule'])
    .controller('HomeCtrl', ['$scope', '$rootScope', '$location', 'UserService', 'AuthenticationService', function ($scope, $rootScope, $location, UserService, AuthenticationService) {
        (function () {
            UserService.GetBusinessProfile(function (response) {
                console.log("UserService.GetBussinessProfile entry!");
                $scope.user = response.data;
                console.log("UserService success");
                console.log($scope.user);
                $scope.location = response.data.location;
                $scope.locationCoord = response.data.locationCoordinates;

                $scope.user_edit = angular.copy($scope.user);
                delete $scope.user_edit['_id'];

                for (member in $scope.user) {
                    if ($scope.user[member] == null) {
                        $scope.user[member] = 'Brak informacji';
                    }
                }

                for (member in $scope.location) {
                    if ($scope.location[member] == null) {
                        $scope.location[member] = 'Brak informacji';
                    }
                }

                for (member in $scope.locationCoord) {
                    if ($scope.locationCoord[member] == null) {
                        $scope.locationCoord[member] = 'Brak informacji';
                    }
                }

                //{
                //    "name":"Nazwa Klubu,
                //    "category_list":[ bar, klub, restauracja, dyskoteka]
                //    "about": "Klub Studio jest sztandarowym punktem na imprezowej mapie Krakowa każdego studenta. Położony w samym sercu miasteczka studenckiego AGH, zalicza się do największych klubów muzycznych w Polsce.",
                //    "location" : {
                //    "city": "Kraków",
                //        "country": "Poland",
                //        "street": "Budryka 4",
                //}
                //    "location_coordinates" : {
                //    Tutaj format tego pola ma zaproponować Marek
                //}
                //    "website": "www.klubstudio.pl",
                //    "music_genres": [pop, folk, rock, soul]
                //    "phone": "+48 12 617-45-45",
                //    "picture_url": "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xft1/v/t1.0-1/1233526_633294910023878_2040166084_n.jpg?oh=0c3d11862d47141d0144b68198cb43fa&oe=560AA6AC&__gda__=1439348545_1539583d82c3721f2126306e173bcaef"
                //}


            });
        })();
        $scope.email = $rootScope.globals.currentUser.email;
        $rootScope.logout = function () {
            AuthenticationService.ClearCredentials();
            UserService.SignOut($scope.user.email, function (response) {
                AuthenticationService.ClearCredentials();
                $rootScope.$apply(function(){
                    $rootScope.isLogged = false;
                });
                $location.path('/login');
            });
        };


        $scope.submitEditProfile = function () {
            UserService.UpdateBusinessProfile($scope.user_edit, function(response) {
                console.log("UpdateBusinessProfile - success");
            },
            function(response) {
                console.log("UpdateBusinessProfile - failed");
            });

        }
    }]);