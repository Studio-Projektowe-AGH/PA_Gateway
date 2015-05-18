/**
 * Created by Dominika on 2015-04-17.
 */

angular.module('HomeModule', ['UserModule', 'AuthenticationModule'])
.controller('HomeCtrl', ['$scope', '$rootScope', '$location', 'UserService', 'AuthenticationService', function ($scope, $rootScope, $location, UserService, AuthenticationService) {
        (function () {
            UserService.GetBusinessProfile(function (response) {
                console.log("UserService.GetBussinessProfile entry!");
                $scope.user = response;
                console.log("UserService success");
                console.log($scope.user);
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

                //$http.get('../profileData.json').success(function (data){
                //    $scope.employees = data;
                //    console.log("success FUNCKJA!");
                //    //$scope.message = 'Hello World!';
                //});
            });
        })();
        $scope.email = $rootScope.globals.currentUser.email;
        $scope.logout = function () {
            AuthenticationService.ClearCredentials();
            UserService.SignOut($scope.user.email, function(response){
                AuthenticationService.ClearCredentials();
                $location.path('/login');
            });
        };
    }]);