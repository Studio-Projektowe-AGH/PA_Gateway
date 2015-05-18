/**
 * Created by Dominika on 2015-04-17.
 */
angular.module('RegisterModule', ['UserModule', 'AuthenticationModule'])
.controller('RegisterCtrl', ['$scope','$location', '$rootScope', 'UserService','AuthenticationService', function($scope, $location, $rootScope, UserService, AuthenticationService){
    $scope.user= {};
        $scope.info = "";
        $scope.register =  function(){
            $scope.user.role = "business";
            $scope.dataLoading = true;
            UserService.SignUp($scope.user, function(response){
                AuthenticationService.SetCredentials(response.data.access_token, $scope.user.email);
                $scope.dataLoading = false;
                $location.path('/home');
                },
            function(response){
                if(response.status == 400){
                    $scope.info = "Użytkownik o podanym adresu e-mail już isnieje";
                }else{
                    $scope.info="Wystąpił niespodziewany błąd spróbuj jeszcze raz.";
                }
            })
        };

        $scope.save = function(ngForm) {
            if(ngForm.$invalid) {
                $scope.invalidSubmitAttempt = true;
            }
        }

    }]);

